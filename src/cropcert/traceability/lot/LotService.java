package cropcert.traceability.lot;

import java.io.IOException;
import java.sql.Timestamp;

import org.json.JSONException;
import org.json.JSONObject;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.google.inject.Inject;

import cropcert.traceability.Constants;
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

	public Lot updateTimeToFactory(String jsonString) throws JsonProcessingException, JSONException, IOException {
		JSONObject jsonObject = new JSONObject(jsonString);
		Long id = jsonObject.getLong("id");
		Lot lot = findById(id);
		
		String timeToFactoryString = jsonObject.get(Constants.TIME_TO_FACTORY).toString();		
		Timestamp timeToFactory    = Timestamp.valueOf(timeToFactoryString);
		
		lot.setTimeToFactory(timeToFactory);
		jsonObject.get(activity);
		
		Lot lot = update(jsonString);
		return lot;
	}
}
