package cropcert.traceability.batch;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.hibernate.exception.ConstraintViolationException;
import org.json.JSONException;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.google.inject.Inject;

import cropcert.traceability.util.UserUtil;

@Path("batch")
public class BatchEndPoint {

	
	private BatchService batchService;
	
	@Inject
	public BatchEndPoint(BatchService batchProductionService) {
		this.batchService = batchProductionService;
	}
	
	@Path("{id}")
	@GET
	@Consumes(MediaType.TEXT_PLAIN)
	@Produces(MediaType.APPLICATION_JSON)
	public Response find(@PathParam("id") Long id, @Context HttpServletRequest request) {
		Batch batchProduction = batchService.findById(id);
		String user = UserUtil.getUserDetails(request);
		System.out.println(user);
		return Response.status(Status.CREATED).entity(batchProduction).build();
	}
	
	@Path("all")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Batch> findAll(
			@DefaultValue("-1") @QueryParam("limit") Integer limit,
			@DefaultValue("-1") @QueryParam("offset") Integer offset) {
		if(limit==-1 || offset ==-1)
			return batchService.findAll();
		else
			return batchService.findAll(limit, offset);
	}
	
	@Path("cc")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Batch> getByCcCodes(
			@DefaultValue("-1") @QueryParam("ccCodes") String ccCodes,
			@DefaultValue("-1") @QueryParam("limit") Integer limit,
			@DefaultValue("-1") @QueryParam("offset") Integer offset) {
		return batchService.getByPropertyfromArray("ccCode", ccCodes, limit, offset);
	}
	
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response save(String  jsonString, @Context HttpServletRequest request) {
		try {
			Batch batchProduction = batchService.save(jsonString, request);
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
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}
