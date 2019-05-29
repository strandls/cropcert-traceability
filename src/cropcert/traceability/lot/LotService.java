package cropcert.traceability.lot;

import java.io.IOException;

import org.json.JSONException;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.Inject;

import cropcert.traceability.common.AbstractService;

public class LotService extends AbstractService<Lot> {

	@Inject
	private ObjectMapper objectMappper;

	@Inject
	public LotService(LotDao dao) {
		super(dao);
	}

	public Lot save(String jsonString)
			throws JsonParseException, JsonMappingException, IOException, JSONException {
		Lot lot = objectMappper.readValue(jsonString, Lot.class);
		return save(lot);
	}
}
