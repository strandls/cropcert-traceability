package cropcert.traceability.processedlot;

import java.io.IOException;

import org.json.JSONException;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
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
}
