package cropcert.traceability.lotcreation;

import java.io.IOException;
import java.sql.Timestamp;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.Inject;

import cropcert.traceability.batch.Batch;
import cropcert.traceability.batch.BatchService;
import cropcert.traceability.common.AbstractService;
import cropcert.traceability.lot.Lot;
import cropcert.traceability.lot.LotService;

public class LotCreationService extends AbstractService<LotCreation> {

	@Inject
	private ObjectMapper objectMappper;
	
	@Inject 
	private LotService lotService;
	
	@Inject
	private BatchService batchService;

	@Inject
	public LotCreationService(LotCreationDao dao) {
		super(dao);
	}

	public Lot saveInBulk(String jsonString)
			throws JsonParseException, JsonMappingException, IOException, JSONException {
		JSONObject jsonObject = new JSONObject(jsonString);
		
		
		JSONArray jsonArray = (JSONArray) jsonObject.remove("batchIds");
		
		Lot lot = objectMappper.readValue(jsonObject.toString(), Lot.class);
		
		lot = lotService.save(lot);
		
		Timestamp timestamp = lot.getCreatedOn();
		
		Long lotId = lot.getId();
		
		// Add traceability for the lot creation.
		for(int i=0; i<jsonArray.length(); i++) {
			Long batchId = jsonArray.getLong(i);
			
			LotCreation lotCreation = new LotCreation();
			lotCreation.setBatchId(batchId);
			lotCreation.setLotId(lotId);
			lotCreation.setTimestamp(timestamp);
			lotCreation.setNote("");
			
			
			// update the batch activity..
			Batch batch = batchService.findById(batchId);
			save(lotCreation);
		}
		
		return lot;
	}
}
