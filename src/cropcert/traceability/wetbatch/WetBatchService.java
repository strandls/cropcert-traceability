package cropcert.traceability.wetbatch;

import java.io.IOException;

import org.json.JSONException;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.Inject;

import cropcert.traceability.common.AbstractService;

public class WetBatchService extends AbstractService<WetBatch> {

	@Inject
	private ObjectMapper objectMappper;

	@Inject
	public WetBatchService(WetBatchDao dao) {
		super(dao);
	}

	public WetBatch save(String jsonString)
			throws JsonParseException, JsonMappingException, IOException, JSONException {
		WetBatch batch = objectMappper.readValue(jsonString, WetBatch.class);
		return save(batch);
	}
}
