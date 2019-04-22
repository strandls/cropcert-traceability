package cropcert.traceability.collection;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.Inject;

import cropcert.traceability.common.AbstractService;


public class CollectionService extends AbstractService<Collection>{

	@Inject
	private ObjectMapper objectMappper;
	
	@Inject
	public CollectionService(CollectionDao dao) {
		super(dao);
	}

	public Collection save(String jsonString) throws JsonParseException, JsonMappingException, IOException {
		Collection collection = objectMappper.readValue(jsonString, Collection.class);
		collection.setAvailableQuantity(collection.getQuantity());
		return save(collection);
	}
}
