package cropcert.traceability.processedlot;

import java.io.IOException;

import org.json.JSONException;
import org.json.JSONObject;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.google.inject.Inject;

import cropcert.traceability.common.AbstractService;

public class ProcessedLotService extends AbstractService<ProcessedLot> {

	@Inject
	private ObjectMapper objectMappper;

	@Inject
	public ProcessedLotService(ProcessedLotDao dao) {
		super(dao);
	}

	public ProcessedLot save(String jsonString)
			throws JsonParseException, JsonMappingException, IOException, JSONException {
		ProcessedLot lot = objectMappper.readValue(jsonString, ProcessedLot.class);
		return save(lot);
	}

	public ProcessedLot update(String jsonString) throws JSONException, JsonProcessingException, IOException {
		Long id = new JSONObject(jsonString).getLong("id");
		ProcessedLot lot = findById(id);
		ObjectReader objectReader = objectMappper.readerForUpdating(lot);
		lot = objectReader.readValue(jsonString);
		lot = update(lot);
		return lot;
	}
}
