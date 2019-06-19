package cropcert.traceability.lot;

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

	public Lot update(String jsonString) throws JSONException, JsonProcessingException, IOException {
		Long id = new JSONObject(jsonString).getLong("id");
		Lot lot = findById(id);
		ObjectReader objectReader = objectMappper.readerForUpdating(lot);
		lot = objectReader.readValue(jsonString);
		lot = update(lot);
		return lot;
	}
}
