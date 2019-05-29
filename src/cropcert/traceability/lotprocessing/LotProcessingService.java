package cropcert.traceability.lotprocessing;

import java.io.IOException;

import org.json.JSONException;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.Inject;

import cropcert.traceability.common.AbstractService;

public class LotProcessingService extends AbstractService<LotProcessing> {

	@Inject
	private ObjectMapper objectMappper;

	@Inject
	public LotProcessingService(LotProcessingDao dao) {
		super(dao);
	}

	public LotProcessing save(String jsonString)
			throws JsonParseException, JsonMappingException, IOException, JSONException {
		LotProcessing lot = objectMappper.readValue(jsonString, LotProcessing.class);
		return save(lot);
	}
}
