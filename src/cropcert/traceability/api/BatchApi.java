package cropcert.traceability.api;

import java.io.IOException;
import java.util.HashMap;
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

import org.json.JSONException;

import com.google.inject.Inject;

import cropcert.traceability.filter.Permissions;
import cropcert.traceability.filter.TokenAndUserAuthenticated;
import cropcert.traceability.model.Batch;
import cropcert.traceability.service.BatchService;
import cropcert.traceability.util.ValidationException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

@Path("batch")
@Api("Batch")
public class BatchApi {

	private BatchService batchService;

	@Inject
	public BatchApi(BatchService batchProductionService) {
		this.batchService = batchProductionService;
	}

	@Path("{id}")
	@GET
	@Consumes(MediaType.TEXT_PLAIN)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Get the batch by id", response = Batch.class)
	public Response find(@Context HttpServletRequest request, @PathParam("id") Long id) {
		Batch batchProduction = batchService.findById(id);
		return Response.status(Status.CREATED).entity(batchProduction).build();
	}

	@Path("all")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Get all the batches", response = Batch.class, responseContainer = "List")
	public Response findAll(@DefaultValue("-1") @QueryParam("limit") Integer limit,
			@DefaultValue("-1") @QueryParam("offset") Integer offset) {

		List<Batch> batches;
		if (limit == -1 || offset == -1)
			batches = batchService.findAll();
		else
			batches = batchService.findAll(limit, offset);
		return Response.ok().entity(batches).build();
	}

	@Path("cc")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Get list of batches by cc codes", response = Batch.class, responseContainer = "List")
	public Response getByCcCodes(@Context HttpServletRequest request,
			@DefaultValue("-1") @QueryParam("ccCodes") String ccCodes,
			@DefaultValue("false") @QueryParam("isLotDone") Boolean isLotDone,
			@DefaultValue("true") @QueryParam("isReadyForLot") Boolean isReadyForLot,
			@DefaultValue("-1") @QueryParam("limit") Integer limit,
			@DefaultValue("-1") @QueryParam("offset") Integer offset) {

		List<Batch> batches = batchService.getByPropertyfromArray("ccCode", ccCodes, isLotDone, isReadyForLot, limit,
				offset);
		return Response.ok().entity(batches).build();
	}

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Save the batch", response = Batch.class)
	@ApiImplicitParams({
			@ApiImplicitParam(name = "Authorization", value = "Authorization token", required = true, dataType = "string", paramType = "header") })
	@TokenAndUserAuthenticated(permissions = { Permissions.CC_PERSON })
	public Response save(@Context HttpServletRequest request, String jsonString) {
		Batch batchProduction;
		try {
			batchProduction = batchService.save(jsonString, request);
			return Response.status(Status.CREATED).entity(batchProduction).build();
		} catch (IOException | JSONException e) {
			return Response.status(Status.NO_CONTENT)
					.entity(new HashMap<String, String>().put("error", e.getMessage())).build();
		} catch (ValidationException e) {
			return Response.status(Status.NO_CONTENT)
					.entity(new HashMap<String, String>().put("error", e.getMessage())).build();
		}
	}
}
