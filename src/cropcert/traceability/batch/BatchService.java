package cropcert.traceability.batch;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONException;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.Inject;

import cropcert.traceability.Constants;
import cropcert.traceability.activity.Activity;
import cropcert.traceability.activity.ActivityService;
import cropcert.traceability.common.AbstractService;
import cropcert.traceability.util.UserUtil;

public class BatchService extends AbstractService<Batch> {

    @Inject
    private ObjectMapper objectMappper;

    @Inject
    private ActivityService activityService;

    @Inject
    public BatchService(BatchDao dao) {
        super(dao);
    }

    public Batch save(String jsonString, HttpServletRequest request)
            throws JsonParseException, JsonMappingException, IOException, JSONException {
        Batch batch = objectMappper.readValue(jsonString, Batch.class);

        // update the transfer time stamp
        Timestamp createdOn = batch.getCreatedOn();
        if (createdOn == null) {
            createdOn = new Timestamp(new Date().getTime());
            batch.setCreatedOn(createdOn);
        }
        batch.setReadyForLot(true);
        batch.setLotDone(false);
        batch = save(batch);

        /**
         * save the activity here.
         */
        String userId = UserUtil.getUserDetails(request);
        Timestamp timestamp = createdOn = new Timestamp(new Date().getTime());
        Activity activity = new Activity(batch.getClass().getSimpleName(), batch.getBatchId(), userId,
                timestamp, Constants.BATCH, batch.getBatchName());
        activity = activityService.save(activity);

        return batch;
    }

    /*
	 * This object list is the comma separated value.
     */
    public List<Batch> getByPropertyfromArray(String property, String objectList, Boolean isLotDone, Boolean isReadyForLot, int limit, int offset) {
        Object[] values = objectList.split(",");
        Long[] longValues = new Long[values.length];
        for (int i = 0; i < values.length; i++) {
            longValues[i] = Long.parseLong(values[i].toString());
        }
        return ((BatchDao)dao).getByPropertyfromArray(property, longValues, isLotDone, isReadyForLot, limit, offset);
    }
}
