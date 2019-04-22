package cropcert.traceability.collection;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.Consumes;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.hibernate.exception.ConstraintViolationException;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.google.inject.Inject;

@Path("collection")
public class CollectionEndPoint {

	private static final Map<String, String> pathProperties = new HashMap<String, String>();
	
	static {
		pathProperties.put("farmer", "membershipId");
		pathProperties.put("cc", "ccCode");
	}
	
	private CollectionService collectionService;
	
	@Inject
	public CollectionEndPoint(CollectionService collectionService) {
		this.collectionService = collectionService;
	}
	
	@Path("{id}")
	@GET
	@Consumes(MediaType.TEXT_PLAIN)
	@Produces(MediaType.APPLICATION_JSON)
	public Response find(@PathParam("id") Long id) {
		Collection user = collectionService.findById(id);
		return Response.status(Status.CREATED).entity(user).build();
	}
	
	@Path("all")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Collection> findAll(			
			@DefaultValue("-1") @QueryParam("limit") Integer limit,
			@DefaultValue("-1") @QueryParam("offset") Integer offset) {
		if(limit==-1 || offset ==-1)
			return collectionService.findAll();
		else
			return collectionService.findAll(limit, offset);
	}
	
	@Path("farmer/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Collection> getByFarmer(
			@DefaultValue("") @PathParam("id") String value,
			@DefaultValue("-1") @QueryParam("limit") Integer limit,
			@DefaultValue("-1") @QueryParam("offset") Integer offset) {

		String property = "farmer";
		if(value != null && 
				(!"".equals(value)) && 
				pathProperties.containsKey(property)) {
			String propertyName = pathProperties.get(property);
			return collectionService.getByPropertyWithCondtion(propertyName, value, "=", limit, offset);
		}
		return null;
	}
	
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response save(String  jsonString) {
		try {
			Collection collection = collectionService.save(jsonString);
			return Response.status(Status.CREATED).entity(collection).build();
		} catch(ConstraintViolationException e) {
			return Response.status(Status.CONFLICT).tag("Dublicate key").build();
		}
		catch (JsonParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}
