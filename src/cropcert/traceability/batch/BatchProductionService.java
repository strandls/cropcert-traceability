package cropcert.traceability.batch;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.Inject;

import cropcert.traceability.common.AbstractService;


public class BatchProductionService extends AbstractService<BatchProduction>{

	@Inject
	private ObjectMapper objectMappper;
	
	@Inject
	public BatchProductionService(BatchProductionDao dao) {
		super(dao);
	}

	public BatchProduction save(String jsonString) throws JsonParseException, JsonMappingException, IOException {
		BatchProduction batchProduction = objectMappper.readValue(jsonString, BatchProduction.class);
		return save(batchProduction);
	}
}
