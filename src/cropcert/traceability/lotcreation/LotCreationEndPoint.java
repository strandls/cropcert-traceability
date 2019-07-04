package cropcert.traceability.lotcreation;

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

import cropcert.traceability.batch.Batch;
import cropcert.traceability.lot.Lot;

@Path("lotCreation")
public class LotCreationEndPoint {

	
	private LotCreationService lotCreationService;
	
	@Inject
	public LotCreationEndPoint(LotCreationService batchProductionService) {
		this.lotCreationService = batchProductionService;
	}
	
	@Path("{id}")
	@GET
	@Consumes(MediaType.TEXT_PLAIN)
	@Produces(MediaType.APPLICATION_JSON)
	public Response find(@PathParam("id") Long id) {
		LotCreation lotCreation = lotCreationService.findById(id);
		return Response.status(Status.CREATED).entity(lotCreation).build();
	}
	
	@Path("all")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<LotCreation> findAll(
			@DefaultValue("-1") @QueryParam("limit") Integer limit,
			@DefaultValue("-1") @QueryParam("offset") Integer offset) {
		if(limit==-1 || offset ==-1)
			return lotCreationService.findAll();
		else
			return lotCreationService.findAll(limit, offset);
	}
	
	@Path("lotId")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Batch> getBylotId(
			@DefaultValue("-1") @QueryParam("lotId") String lotId,
			@DefaultValue("-1") @QueryParam("limit") Integer limit,
			@DefaultValue("-1") @QueryParam("offset") Integer offset) {
		return lotCreationService.getByLotId(lotId, limit, offset);
	}
	
	@Path("lot/origin")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Long> getLotOrigins(@DefaultValue("-1") @QueryParam("lotId") String lotId) {
		return lotCreationService.getLotOrigins(lotId);
	}
	
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response save(String  jsonString, @Context HttpServletRequest request) {
		try {
			Lot lot = lotCreationService.saveInBulk(jsonString, request);
			return Response.status(Status.CREATED).entity(lot).build();
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
