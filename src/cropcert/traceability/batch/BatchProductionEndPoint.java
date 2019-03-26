package cropcert.traceability.batch;

import java.io.IOException;
import java.util.List;

import javax.ws.rs.Consumes;
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

@Path("batch")
public class BatchProductionEndPoint {

	
	private BatchProductionService batchProductionService;
	
	@Inject
	public BatchProductionEndPoint(BatchProductionService batchProductionService) {
		this.batchProductionService = batchProductionService;
	}
	
	@Path("{id}")
	@GET
	@Consumes(MediaType.TEXT_PLAIN)
	@Produces(MediaType.APPLICATION_JSON)
	public Response find(@PathParam("id") Long id) {
		BatchProduction batchProduction = batchProductionService.findById(id);
		return Response.status(Status.CREATED).entity(batchProduction).build();
	}
	
	@Path("all")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<BatchProduction> findAll() {
		return batchProductionService.findAll();
	}
	
	@Path("few")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<BatchProduction> findAll(@QueryParam("limit") int limit, @QueryParam("offset") int offset) {
		return batchProductionService.findAll(limit, offset);
	}
	
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response save(String  jsonString) {
		try {
			BatchProduction batchProduction = batchProductionService.save(jsonString);
			return Response.status(Status.CREATED).entity(batchProduction).build();
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
