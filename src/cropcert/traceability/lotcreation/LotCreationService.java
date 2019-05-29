package cropcert.traceability.lotcreation;

import java.io.IOException;

import org.json.JSONException;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.Inject;

import cropcert.traceability.common.AbstractService;

public class LotCreationService extends AbstractService<LotCreation> {

	@Inject
	private ObjectMapper objectMappper;

	@Inject
	public LotCreationService(LotCreationDao dao) {
		super(dao);
	}

	public LotCreation save(String jsonString)
			throws JsonParseException, JsonMappingException, IOException, JSONException {
		LotCreation lot = objectMappper.readValue(jsonString, LotCreation.class);
		return save(lot);
	}
}
