package cropcert.traceability.lotprocessing;

import java.io.IOException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.Inject;

import cropcert.traceability.common.AbstractService;
import cropcert.traceability.processedlot.ProcessedLot;
import cropcert.traceability.processedlot.ProcessedLotService;

public class LotProcessingService extends AbstractService<LotProcessing> {

	@Inject
	private ObjectMapper objectMappper;
	
	@Inject
	private ProcessedLotService processedLotService;

	@Inject
	public LotProcessingService(LotProcessingDao dao) {
		super(dao);
	}

	public LotProcessing save(String jsonString)
			throws JsonParseException, JsonMappingException, IOException, JSONException {
		LotProcessing lot = objectMappper.readValue(jsonString, LotProcessing.class);
		return save(lot);
	}

	public ProcessedLot saveInBulk(String jsonString) throws JSONException, JsonParseException, JsonMappingException, IOException {
		JSONObject jsonObject = new JSONObject(jsonString);

		JSONArray jsonArray = (JSONArray) jsonObject.remove("ids");

		ProcessedLot processedLot = objectMappper.readValue(jsonObject.toString(), ProcessedLot.class);

		processedLot = processedLotService.save(processedLot);

		Long processedLotId = processedLot.getId();

		// Add traceability for the lot creation.
		for (int i = 0; i < jsonArray.length(); i++) {
			Long lotId = jsonArray.getLong(i);
			LotProcessing lotProcessing = new LotProcessing();
			lotProcessing.setLotId(lotId);
			lotProcessing.setProcessedLotId(processedLotId);
			save(lotProcessing);
		}

		return processedLot;
	}
}
