package cropcert.traceability.service;

import java.io.IOException;

import org.json.JSONException;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.Inject;

import cropcert.traceability.dao.CuppingDao;
import cropcert.traceability.model.Cupping;

public class CuppingService extends AbstractService<Cupping> {

	@Inject
	private ObjectMapper objectMappper;

	@Inject
	public CuppingService(CuppingDao dao) {
		super(dao);
	}

	public Cupping save(String jsonString)
			throws JsonParseException, JsonMappingException, IOException, JSONException {
		Cupping cupping = objectMappper.readValue(jsonString, Cupping.class);
		return save(cupping);
	}
}
