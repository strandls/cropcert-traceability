package cropcert.traceability.api;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
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
import cropcert.traceability.model.Lot;
import cropcert.traceability.service.LotService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

@Path("lot")
@Api("Lot")
public class LotApi {

	private LotService lotService;

	@Inject
	public LotApi(LotService batchProductionService) {
		this.lotService = batchProductionService;
	}

	@Path("{id}")
	@GET
	@Consumes(MediaType.TEXT_PLAIN)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(response = Lot.class, value = "Find the lot by its id")
	public Response find(@PathParam("id") Long id) {
		Lot lot = lotService.findById(id);
		return Response.status(Status.CREATED).entity(lot).build();
	}

	@Path("all")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Get all the Lots", response = Lot.class, responseContainer = "List")
	public Response findAll(@DefaultValue("-1") @QueryParam("limit") Integer limit,
			@DefaultValue("-1") @QueryParam("offset") Integer offset) {
		List<Lot> lots;
		if (limit == -1 || offset == -1)
			lots = lotService.findAll();
		else
			lots = lotService.findAll(limit, offset);
		return Response.ok().entity(lots).build();
	}

	@Path("all/{lotStatus}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Get all the Lots by the status", response = Lot.class, responseContainer = "List")
	public Response getAllByStatus(@DefaultValue("-1") @PathParam("lotStatus") String lotStatusString,
			@DefaultValue("-1") @QueryParam("coCodes") String coCodes,
			@DefaultValue("-1") @QueryParam("limit") Integer limit,
			@DefaultValue("-1") @QueryParam("offset") Integer offset) {
		List<Lot> lots = lotService.getByStatusAndUnion(lotStatusString, coCodes, limit, offset);
		return Response.ok().entity(lots).build();
	}

	@Path("origin")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Collection center ids of the lot creation", response = Long.class, responseContainer = "List")
	public Response getLotOrigins(@DefaultValue("-1") @QueryParam("lotId") String lotId) {
		List<Long> origins = lotService.getLotOrigins(lotId);
		return Response.ok().entity(origins).build();
	}

	@Path("batches")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Get list of all the lot creation entries, whoes lot id is given", response = Batch.class, responseContainer = "List")
	public Response getBylotId(@DefaultValue("-1") @QueryParam("lotId") String lotId,
			@DefaultValue("-1") @QueryParam("limit") Integer limit,
			@DefaultValue("-1") @QueryParam("offset") Integer offset) {
		List<Batch> batches = lotService.getByLotId(lotId, limit, offset);
		return Response.ok().entity(batches).build();
	}

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@ApiOperation(response = Lot.class, value = "save the lot")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "Authorization", value = "Authorization token", required = true, dataType = "string", paramType = "header") })
	@TokenAndUserAuthenticated(permissions = { Permissions.CO_PERSON })
	public Response save(@Context HttpServletRequest request, String jsonString) {
		Lot lot;
		try {
			lot = lotService.saveInBulk(jsonString, request);
			return Response.status(Status.CREATED).entity(lot).build();
		} catch (IOException | JSONException e) {
			e.printStackTrace();
		}
		return Response.status(Status.NO_CONTENT).entity("Lot creation failed").build();
	}

	@PUT
	@Path("dispatch/factory")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@ApiOperation(response = Lot.class, value = "update time to factory")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "Authorization", value = "Authorization token", required = true, dataType = "string", paramType = "header") })
	@TokenAndUserAuthenticated(permissions = { Permissions.CO_PERSON })
	public Response updateTimeToFactory(String jsonString, @Context HttpServletRequest request) {
		try {
			String response = lotService.updateTimeToFactory(jsonString, request);
			return Response.ok().entity(response).build();
		} catch (JSONException | IOException e) {
			e.printStackTrace();
		}
		return Response.status(Status.NO_CONTENT).entity("Time to factory updation failed").build();
	}

	@PUT
	@Path("millingTime")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@ApiOperation(response = Lot.class, value = "update time for milling")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "Authorization", value = "Authorization token", required = true, dataType = "string", paramType = "header") })
	@TokenAndUserAuthenticated(permissions = { Permissions.FACTORY })
	public Response updateMillingTime(String jsonString, @Context HttpServletRequest request) {
		try {
			String response = lotService.updateMillingTime(jsonString, request);
			return Response.ok().entity(response).build();
		} catch (JSONException | IOException e) {
			e.printStackTrace();
		}
		return Response.status(Status.NO_CONTENT).entity("Milling time updation failed").build();
	}

	@PUT
	@Path("outTurn")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@ApiOperation(response = Lot.class, value = "update out turn time")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "Authorization", value = "Authorization token", required = true, dataType = "string", paramType = "header") })
	@TokenAndUserAuthenticated(permissions = { Permissions.FACTORY })
	public Response updateOutTurn(String jsonString, @Context HttpServletRequest request) {
		try {
			String response = lotService.updateOutTurn(jsonString, request);
			return Response.ok().entity(response).build();
		} catch (JSONException | IOException e) {
			e.printStackTrace();
		}
		return Response.status(Status.NO_CONTENT).entity("Updation of outturn failed").build();
	}

	@PUT
	@Path("dispatch/union")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@ApiOperation(response = Lot.class, value = "update time to dispatch")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "Authorization", value = "Authorization token", required = true, dataType = "string", paramType = "header") })
	@TokenAndUserAuthenticated(permissions = { Permissions.FACTORY })
	public Response dispatchToUnion(String jsonString, @Context HttpServletRequest request) {
		try {
			String response = lotService.dispatchToUnion(jsonString, request);
			return Response.ok().entity(response).build();
		} catch (JSONException | IOException e) {
			e.printStackTrace();
		}
		return Response.status(Status.NO_CONTENT).entity("Time to dispatch from factory updation failed").build();
	}

	@PUT
	@Path("grnNumber")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@ApiOperation(response = Lot.class, value = "update grnNumber to factory")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "Authorization", value = "Authorization token", required = true, dataType = "string", paramType = "header") })
	@TokenAndUserAuthenticated(permissions = { Permissions.UNION })
	public Response updateGRNNumer(String jsonString, @Context HttpServletRequest request) {
		try {
			String response = lotService.updateGRNNumer(jsonString, request);
			return Response.ok().entity(response).build();
		} catch (JSONException | IOException e) {
			e.printStackTrace();
		}
		return Response.status(Status.NO_CONTENT).entity("GNR updation for lot has failed").build();
	}
}
