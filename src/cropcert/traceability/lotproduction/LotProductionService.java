package cropcert.traceability.lotproduction;

import java.io.IOException;

import org.json.JSONException;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.Inject;

import cropcert.traceability.common.AbstractService;

public class LotProductionService extends AbstractService<LotProduction> {

	@Inject
	private ObjectMapper objectMappper;

	@Inject
	public LotProductionService(LotProductionDao dao) {
		super(dao);
	}

	public LotProduction save(String jsonString)
			throws JsonParseException, JsonMappingException, IOException, JSONException {
		LotProduction lot = objectMappper.readValue(jsonString, LotProduction.class);
		return save(lot);
	}
}
