package cropcert.traceability.api;

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

import cropcert.traceability.model.Batch;
import cropcert.traceability.model.Lot;
import cropcert.traceability.model.LotCreation;
import cropcert.traceability.service.LotCreationService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

@Path("lotCreation")
@Api("Lot creation")
@ApiImplicitParams({
    @ApiImplicitParam(name = "Authorization", value = "Authorization token", 
                      required = true, dataType = "string", paramType = "header") })
public class LotCreationApi {

	private LotCreationService lotCreationService;

	@Inject
	public LotCreationApi(LotCreationService batchProductionService) {
		this.lotCreationService = batchProductionService;
	}

	@Path("{id}")
	@GET
	@Consumes(MediaType.TEXT_PLAIN)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Get lot creation entry by id", 
			notes = "This method is not required to call, bit tricky",
			response = LotCreation.class)
	public Response find(@PathParam("id") Long id) {
		LotCreation lotCreation = lotCreationService.findById(id);
		return Response.status(Status.CREATED).entity(lotCreation).build();
	}

	@Path("all")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Get list of lot creation",
			response = LotCreation.class, 
			responseContainer = "List")
	public Response findAll(@DefaultValue("-1") @QueryParam("limit") Integer limit,
			@DefaultValue("-1") @QueryParam("offset") Integer offset) {
		List<LotCreation> lotCreations;
		if (limit == -1 || offset == -1)
			lotCreations = lotCreationService.findAll();
		else
			lotCreations = lotCreationService.findAll(limit, offset);
		return Response.ok().entity(lotCreations).build();
	}

	@Path("lotId")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Get list of all the lot creation entries, whoes lot id is given",
			response = Batch.class, 
			responseContainer = "List")
	public Response getBylotId(@DefaultValue("-1") @QueryParam("lotId") String lotId,
			@DefaultValue("-1") @QueryParam("limit") Integer limit,
			@DefaultValue("-1") @QueryParam("offset") Integer offset) {
		List<Batch> batches = lotCreationService.getByLotId(lotId, limit, offset);
		return Response.ok().entity(batches).build();
	}

	@Path("lot/origin")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Collection center ids of the lot creation",
			response = Long.class, 
			responseContainer = "List")
	public Response getLotOrigins(@DefaultValue("-1") @QueryParam("lotId") String lotId) {
		List<Long> origins = lotCreationService.getLotOrigins(lotId);
		return Response.ok().entity(origins).build();
	}

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@ApiOperation(response = Lot.class, value = "Lot creation", notes = "Here you save the lot with the help of multiple lot creation ids."
			+ "This is the where we keep trace of traceability")
	public Response save(String jsonString, @Context HttpServletRequest request) {
		try {
			Lot lot = lotCreationService.saveInBulk(jsonString, request);
			return Response.status(Status.CREATED).entity(lot).build();
		} catch (ConstraintViolationException e) {
			return Response.status(Status.CONFLICT).tag("Dublicate key").build();
		} catch (JsonParseException e) {
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
