package cropcert.traceability.api;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import org.json.JSONObject;

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
	public Response find(@Context HttpServletRequest request, @PathParam("id") Long id) {
		Lot lot = lotService.findById(id);
		return Response.status(Status.CREATED).entity(lot).build();
	}

	@Path("all")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Get all the Lots", response = Lot.class, responseContainer = "List")
	public Response findAll(@Context HttpServletRequest request, @DefaultValue("-1") @QueryParam("limit") Integer limit,
			@DefaultValue("-1") @QueryParam("offset") Integer offset) {
		List<Lot> lots;
		if (limit == -1 || offset == -1) {
			lots = lotService.findAll();
		} else {
			lots = lotService.findAll(limit, offset);
		}
		return Response.ok().entity(lots).build();
	}
	
	@Path("all/cupping")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Get all the Lots", response = Lot.class, responseContainer = "List")
	public Response findAllWithCupping(@Context HttpServletRequest request, @DefaultValue("-1") @QueryParam("limit") Integer limit,
			@DefaultValue("-1") @QueryParam("offset") Integer offset) {
		List<Map<String, Object>> lots = lotService.getAllWithCupping(limit, offset);
		return Response.ok().entity(lots).build();
	}

	@Path("all/{lotStatus}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Get all the Lots by the status", response = Lot.class, responseContainer = "List")
	public Response getAllByStatus(@Context HttpServletRequest request,
			@DefaultValue("-1") @PathParam("lotStatus") String lotStatusString,
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
	public Response getLotOrigins(@Context HttpServletRequest request,
			@DefaultValue("-1") @QueryParam("lotId") String lotId) {
		List<Long> origins = lotService.getLotOrigins(lotId);
		return Response.ok().entity(origins).build();
	}

	@Path("batches")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Get list of all the lot creation entries, whoes lot id is given", response = Batch.class, responseContainer = "List")
	public Response getBylotId(@Context HttpServletRequest request,
			@DefaultValue("-1") @QueryParam("lotId") String lotId,
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
		return Response.status(Status.NO_CONTENT)
				.entity(new HashMap<String, String>().put("error", "Lot creation failed")).build();
	}

	@PUT
	@Path("dispatch/factory")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@ApiOperation(response = Lot.class, value = "update time to factory")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "Authorization", value = "Authorization token", required = true, dataType = "string", paramType = "header") })
	@TokenAndUserAuthenticated(permissions = { Permissions.CO_PERSON })
	public Response updateTimeToFactory(@Context HttpServletRequest request, String jsonString) {
		try {
			String response = lotService.updateTimeToFactory(jsonString, request);
			return Response.ok().entity(response).build();
		} catch (JSONException | IOException e) {
			e.printStackTrace();
		}
		return Response.status(Status.NO_CONTENT)
				.entity(new HashMap<String, String>().put("error", "Time to factory updation failed")).build();
	}

	@PUT
	@Path("weightLeavingCooperative")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@ApiOperation(response = Lot.class, value = "update the weight of lot while leaving the cooperative")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "Authorization", value = "Authorization token", required = true, dataType = "string", paramType = "header") })
	@TokenAndUserAuthenticated(permissions = { Permissions.CO_PERSON })
	public Response updateWeightLeavingCooperative(@Context HttpServletRequest request, String jsonString) {
		try {
			Lot response = lotService.updateWeightLeavingCooperative(jsonString, request);
			return Response.ok().entity(response).build();
		} catch (JSONException | IOException e) {
			e.printStackTrace();
		}
		return Response.status(Status.NO_CONTENT)
				.entity(new HashMap<String, String>().put("error", "Weight updation failed at the cooperative")).build();
	}
	
	@PUT
	@Path("mcLeavingCooperative")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@ApiOperation(response = Lot.class, value = "update the moisture content of lot while leaving the cooperative")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "Authorization", value = "Authorization token", required = true, dataType = "string", paramType = "header") })
	@TokenAndUserAuthenticated(permissions = { Permissions.CO_PERSON })
	public Response updateMCLeavingCooperative(@Context HttpServletRequest request, String jsonString) {
		try {
			Lot response = lotService.updateMCLeavingCooperative(jsonString, request);
			return Response.ok().entity(response).build();
		} catch (JSONException | IOException e) {
			e.printStackTrace();
		}
		return Response.status(Status.NO_CONTENT)
				.entity(new HashMap<String, String>().put("error", "Moisture content updation failed at the cooperative")).build();
	}
	
	@PUT
	@Path("weightArrivingFactory")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@ApiOperation(response = Lot.class, value = "update the weight of lot while arriving at the factory")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "Authorization", value = "Authorization token", required = true, dataType = "string", paramType = "header") })
	@TokenAndUserAuthenticated(permissions = { Permissions.FACTORY })
	public Response updateWeightArrivingFactory(@Context HttpServletRequest request, String jsonString) {
		try {
			Lot response = lotService.updateWeightArrivingFactory(jsonString, request);
			return Response.ok().entity(response).build();
		} catch (JSONException | IOException e) {
			e.printStackTrace();
		}
		return Response.status(Status.NO_CONTENT)
				.entity(new HashMap<String, String>().put("error", "Weight updation failed at the factory")).build();
	}
	
	@PUT
	@Path("mcArrivingFactory")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@ApiOperation(response = Lot.class, value = "update the moisture content of lot while arriving at the factory")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "Authorization", value = "Authorization token", required = true, dataType = "string", paramType = "header") })
	@TokenAndUserAuthenticated(permissions = { Permissions.FACTORY })
	public Response updateMCArrivingFactory(@Context HttpServletRequest request, String jsonString) {
		try {
			Lot response = lotService.updateMCArrivingFactory(jsonString, request);
			return Response.ok().entity(response).build();
		} catch (JSONException | IOException e) {
			e.printStackTrace();
		}
		return Response.status(Status.NO_CONTENT)
				.entity(new HashMap<String, String>().put("error", "Moisture content updation failed at the factory")).build();
	}
	
	@PUT
	@Path("millingTime")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@ApiOperation(response = Lot.class, value = "update time for milling")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "Authorization", value = "Authorization token", required = true, dataType = "string", paramType = "header") })
	@TokenAndUserAuthenticated(permissions = { Permissions.FACTORY })
	public Response updateMillingTime(@Context HttpServletRequest request, String jsonString) {
		try {
			Lot response = lotService.updateMillingTime(jsonString, request);
			return Response.ok().entity(response).build();
		} catch (JSONException | IOException e) {
			e.printStackTrace();
		}
		return Response.status(Status.NO_CONTENT)
				.entity(new HashMap<String, String>().put("error", "Milling time updation failed")).build();
	}
	
	@PUT
	@Path("weightLeavingFactory")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@ApiOperation(response = Lot.class, value = "update the weight of lot while leaving the factory")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "Authorization", value = "Authorization token", required = true, dataType = "string", paramType = "header") })
	@TokenAndUserAuthenticated(permissions = { Permissions.FACTORY })
	public Response updateWeightLeavingFactory(@Context HttpServletRequest request, String jsonString) {
		try {
			Lot response = lotService.updateWeightLeavingFactory(jsonString, request);
			return Response.ok().entity(response).build();
		} catch (JSONException | IOException e) {
			e.printStackTrace();
		}
		return Response.status(Status.NO_CONTENT)
				.entity(new HashMap<String, String>().put("error", "Weight updation failed at the factory")).build();
	}
	
	@PUT
	@Path("mcLeavingFactory")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@ApiOperation(response = Lot.class, value = "update the moisture content of lot while leaving the factory")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "Authorization", value = "Authorization token", required = true, dataType = "string", paramType = "header") })
	@TokenAndUserAuthenticated(permissions = { Permissions.FACTORY })
	public Response updateMCLeavingFactory(@Context HttpServletRequest request, String jsonString) {
		try {
			Lot response = lotService.updateMCLeavingFactory(jsonString, request);
			return Response.ok().entity(response).build();
		} catch (JSONException | IOException e) {
			e.printStackTrace();
		}
		return Response.status(Status.NO_CONTENT)
				.entity(new HashMap<String, String>().put("error", "Moisture content updation failed at the factory")).build();
	}

	@PUT
	@Path("outTurn")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@ApiOperation(response = Lot.class, value = "update out turn time")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "Authorization", value = "Authorization token", required = true, dataType = "string", paramType = "header") })
	@TokenAndUserAuthenticated(permissions = { Permissions.FACTORY })
	public Response updateOutTurn(@Context HttpServletRequest request, String jsonString) {
		try {
			Lot response = lotService.updateOutTurn(jsonString, request);
			return Response.ok().entity(response).build();
		} catch (JSONException | IOException e) {
			e.printStackTrace();
		}
		return Response.status(Status.NO_CONTENT)
				.entity(new HashMap<String, String>().put("error", "Updation of outturn failed")).build();
	}

	@PUT
	@Path("dispatch/union")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@ApiOperation(response = Lot.class, value = "update time to dispatch")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "Authorization", value = "Authorization token", required = true, dataType = "string", paramType = "header") })
	@TokenAndUserAuthenticated(permissions = { Permissions.FACTORY })
	public Response dispatchToUnion(@Context HttpServletRequest request, String jsonString) {
		try {
			String response = lotService.dispatchToUnion(jsonString, request);
			return Response.ok().entity(response).build();
		} catch (JSONException | IOException e) {
			e.printStackTrace();
		}
		return Response.status(Status.NO_CONTENT)
				.entity(new HashMap<String, String>().put("error", "Time to dispatch from factory updation failed"))
				.build();
	}

	@PUT
	@Path("grnNumber")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@ApiOperation(response = Lot.class, value = "update grnNumber to factory")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "Authorization", value = "Authorization token", required = true, dataType = "string", paramType = "header") })
	@TokenAndUserAuthenticated(permissions = { Permissions.UNION })
	public Response updateGRNNumer(@Context HttpServletRequest request, String jsonString) {
		try {
			if (lotService.checkForDuplicate(jsonString)) {
				JSONObject jo = new JSONObject();
				jo.put("error", "Duplicate GRN Number");
				return Response.status(Status.PRECONDITION_FAILED).entity(jo.toString()).build();
			}
			Lot response = lotService.updateGRNNumer(jsonString, request);
			return Response.ok().entity(response).build();
		} catch (JSONException | IOException e) {
			e.printStackTrace();
		}
		return Response.status(Status.NO_CONTENT)
				.entity(new HashMap<String, String>().put("error", "GNR updation for lot has failed")).build();
	}
}
