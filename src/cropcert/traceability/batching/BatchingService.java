package cropcert.traceability.batching;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.Inject;

import cropcert.traceability.common.AbstractService;


public class BatchingService extends AbstractService<Batching>{

	@Inject
	private ObjectMapper objectMappper;
	
	@Inject
	public BatchingService(BatchingDao dao) {
		super(dao);
	}

	public Batching save(String jsonString) throws JsonParseException, JsonMappingException, IOException {
		Batching batching = objectMappper.readValue(jsonString, Batching.class);
		return save(batching);
	}
	
	/**
	 * Utilty method to convert the json array to Batching objects
	 * @param jsonString
	 * @return
	 * @throws JsonParseException
	 * @throws JsonMappingException
	 * @throws IOException
	 * @throws JSONException
	 */
	public List<Batching> convertFromJsonArray(String jsonString) throws JsonParseException, JsonMappingException, IOException, JSONException {
		JSONArray array = new JSONArray(jsonString);
		
		List<Batching> batchings = new ArrayList<Batching>();
		for (int i = 0; i < array.length(); i++) {
			JSONObject object = array.getJSONObject(i);
			Batching batching= objectMappper.readValue(object.toString(), Batching.class);
			batchings.add(batching);
		}
		
		return batchings;
	}
}
