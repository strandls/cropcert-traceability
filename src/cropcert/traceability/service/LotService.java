package cropcert.traceability.service;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import javax.persistence.NoResultException;
import javax.servlet.http.HttpServletRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.google.inject.Inject;

import cropcert.traceability.Constants;
import cropcert.traceability.LotStatus;
import cropcert.traceability.dao.LotDao;
import cropcert.traceability.model.Activity;
import cropcert.traceability.model.Batch;
import cropcert.traceability.model.Lot;
import cropcert.traceability.model.LotCreation;
import cropcert.traceability.util.UserUtil;

public class LotService extends AbstractService<Lot> {

    @Inject
    private ObjectMapper objectMappper;

    @Inject
    private ActivityService activityService;

    @Inject
    private BatchService batchService;

    @Inject
    private LotCreationService lotCreationService;

    @Inject
    public LotService(LotDao dao) {
        super(dao);
    }

    public Lot saveInBulk(String jsonString, HttpServletRequest request)
            throws JsonParseException, JsonMappingException, IOException, JSONException {
        JSONObject jsonObject = new JSONObject(jsonString);

        JSONArray jsonArray = (JSONArray) jsonObject.remove("batchIds");

        Lot lot = objectMappper.readValue(jsonObject.toString(), Lot.class);
        lot.setLotStatus(LotStatus.AT_CO_OPERATIVE);

        lot = save(lot);

        Timestamp timestamp = lot.getCreatedOn();

        Long lotId = lot.getId();

        // update the name, by appending the lot id to name
        String lotName = lot.getLotName() + "_" + lotId;
        lot.setLotName(lotName);
        update(lot);

        String userId = UserUtil.getUserDetails(request).getId();

        // Add traceability for the lot creation.
        for (int i = 0; i < jsonArray.length(); i++) {
            Long batchId = jsonArray.getLong(i);

            LotCreation lotCreation = new LotCreation();
            lotCreation.setBatchId(batchId);
            lotCreation.setLotId(lotId);
            lotCreation.setUserId(userId);
            lotCreation.setTimestamp(timestamp);
            lotCreation.setNote("");

            // update the batch activity..
            Batch batch = batchService.findById(batchId);
            if (batch == null) {
                throw new JSONException("Invalid batch id found");
            }
            batch.setLotDone(true);
            batchService.update(batch);
            lotCreationService.save(lotCreation);
        }

        // Add activity of lot creation.
        if (timestamp == null) {
            timestamp = new Timestamp(new Date().getTime());
        }
        Activity activity = new Activity(lot.getClass().getSimpleName(), lotId, userId,
                timestamp, Constants.LOT_CREATION, lot.getLotName());
        activity = activityService.save(activity);

        return lot;
    }

    public Lot update(String jsonString) throws JSONException, JsonProcessingException, IOException {
        Long id = new JSONObject(jsonString).getLong("id");
        Lot lot = findById(id);
        ObjectReader objectReader = objectMappper.readerForUpdating(lot);
        lot = objectReader.readValue(jsonString);
        lot = update(lot);
        return lot;
    }

    public String updateTimeToFactory(String jsonString, HttpServletRequest request) throws JsonProcessingException, JSONException, IOException {
        JSONObject jsonObject = new JSONObject(jsonString);
        JSONArray jsonArray = jsonObject.getJSONArray("ids");

        Timestamp timeToFactory = new Timestamp((Long) jsonObject.get(Constants.TIME_TO_FACTORY));

        for (int i = 0; i < jsonArray.length(); i++) {
            Long id = jsonArray.getLong(i);
            Lot lot = findById(id);
            lot.setTimeToFactory(timeToFactory);
            lot.setLotStatus(LotStatus.AT_FACTORY);
            lot = update(lot);

            String userId = UserUtil.getUserDetails(request).getId();
            Timestamp timestamp = new Timestamp(new Date().getTime());
            Activity activity = new Activity(lot.getClass().getSimpleName(), lot.getId(), userId,
                    timestamp, Constants.TIME_TO_FACTORY, timeToFactory.toString());
            activity = activityService.save(activity);
        }
        return "Updated succesfully";
    }

    public Lot updateMillingTime(String jsonString, HttpServletRequest request) throws JsonProcessingException, JSONException, IOException {
        JSONObject jsonObject = new JSONObject(jsonString);

        Long id = jsonObject.getLong("id");
        Lot lot = findById(id);

        Timestamp millingTime = new Timestamp((Long) jsonObject.get(Constants.MILLING_TIME));

        lot.setMillingTime(millingTime);
        lot.setLotStatus(LotStatus.AT_FACTORY);
        lot = update(lot);

        String userId = UserUtil.getUserDetails(request).getId();
        Timestamp timestamp = new Timestamp(new Date().getTime());
        Activity activity = new Activity(lot.getClass().getSimpleName(), lot.getId(), userId,
                timestamp, Constants.MILLING_TIME, millingTime.toString());
        activity = activityService.save(activity);

        return lot;
    }

    public Lot updateOutTurn(String jsonString, HttpServletRequest request) throws JsonProcessingException, JSONException, IOException {
        JSONObject jsonObject = new JSONObject(jsonString);

        Long id = jsonObject.getLong("id");
        Lot lot = findById(id);

        String outTurnString = jsonObject.get(Constants.OUT_TURN).toString();
        Float outTurn = Float.valueOf(outTurnString);

        lot.setOutTurn(outTurn);
        lot.setLotStatus(LotStatus.AT_FACTORY);
        lot = update(lot);

        String userId = UserUtil.getUserDetails(request).getId();
        Timestamp timestamp = new Timestamp(new Date().getTime());
        Activity activity = new Activity(lot.getClass().getSimpleName(), lot.getId(), userId,
                timestamp, Constants.OUT_TURN, outTurn.toString());
        activity = activityService.save(activity);

        return lot;
    }

    public String dispatchToUnion(String jsonString, HttpServletRequest request)
            throws JsonProcessingException, JSONException, IOException {
        JSONObject jsonObject = new JSONObject(jsonString);
        JSONArray jsonArray = jsonObject.getJSONArray("ids");

        Timestamp dispatchTime = new Timestamp((Long) jsonObject.get(Constants.DISPATCH_TIME));

        for (int i = 0; i < jsonArray.length(); i++) {
            Long lotId = jsonArray.getLong(i);

            Lot lot = findById(lotId);
            lot.setLotStatus(LotStatus.AT_UNION);
            lot = update(lot);

            String userId = UserUtil.getUserDetails(request).getId();
            Timestamp timestamp = new Timestamp(new Date().getTime());
            Activity activity = new Activity(lot.getClass().getSimpleName(), lotId, userId,
                    timestamp, Constants.DISPATCH_TIME, dispatchTime.toString());
            activity = activityService.save(activity);
        }
        return "Dispatched to union succesful";
    }

    public Lot updateGRNNumer(String jsonString, HttpServletRequest request)
            throws JsonProcessingException, JSONException, IOException {
        JSONObject jsonObject = new JSONObject(jsonString);

        Long id = jsonObject.getLong("id");
        Lot lot = findById(id);

        String grnNumber = jsonObject.get(Constants.GRN_NUMBER).toString();
        Timestamp grnTimestamp = new Timestamp((Long) jsonObject.get(Constants.GRN_TIME));

        lot.setGrnNumber(grnNumber);
        lot.setLotStatus(LotStatus.AT_UNION);
        lot.setGrnTimestamp(grnTimestamp);
        lot = update(lot);

        String userId = UserUtil.getUserDetails(request).getId();
        Timestamp timestamp = new Timestamp(new Date().getTime());
        Activity activity = new Activity(lot.getClass().getSimpleName(), lot.getId(), userId,
                timestamp, Constants.GRN_NUMBER, grnNumber);
        activity = activityService.save(activity);

        return lot;
    }

    public boolean checkForDuplicate(String jsonString) throws JSONException {
        JSONObject jsonObject = new JSONObject(jsonString);
        String grnNumber = jsonObject.get(Constants.GRN_NUMBER).toString();
        try {
            findByPropertyWithCondtion("grnNumber", grnNumber, "=");
        } catch (NoResultException e) {
            return false;
        }
        return true;
    }

    public List<Lot> getByStatusAndUnion(String lotStatusString, String coCodes, Integer limit, Integer offset) {
        LotStatus lotStatus = LotStatus.fromValue(lotStatusString);
        Object[] values = coCodes.split(",");
        Long[] longValues = new Long[values.length];
        for (int i = 0; i < values.length; i++) {
            longValues[i] = Long.parseLong(values[i].toString());
        }
        return ((LotDao) dao).getByPropertyfromArray("coCode", longValues, lotStatus, limit, offset);
    }

    public List<Long> getLotOrigins(String lotId) {
        return lotCreationService.getLotOrigins(lotId);
    }

    public List<Batch> getByLotId(String lotId, Integer limit, Integer offset) {
        return lotCreationService.getByLotId(lotId, limit, offset);
    }
}
