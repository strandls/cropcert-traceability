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

import org.hibernate.exception.ConstraintViolationException;
import org.json.JSONException;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.google.inject.Inject;

import cropcert.traceability.model.Lot;
import cropcert.traceability.service.LotService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

@Path("lot")
@Api("Lot")
@ApiImplicitParams({
    @ApiImplicitParam(name = "Authorization", value = "Authorization token", 
                      required = true, dataType = "string", paramType = "header") })
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
	@ApiOperation(response=Lot.class, value = "Find the lot by its id")
	public Response find(@PathParam("id") Long id) {
		Lot lot = lotService.findById(id);
		return Response.status(Status.CREATED).entity(lot).build();
	}
	
	@Path("all")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Get all the Lots",
			response=Lot.class, 
			responseContainer = "List")
	public Response findAll(
			@DefaultValue("-1") @QueryParam("limit") Integer limit,
			@DefaultValue("-1") @QueryParam("offset") Integer offset) {
		List<Lot> lots;
		if(limit==-1 || offset ==-1)
			lots = lotService.findAll();
		else
			lots = lotService.findAll(limit, offset);
		return Response.ok().entity(lots).build();
	}
	
	@Path("all/{lotStatus}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Get all the Lots by the status",
			response=Lot.class, 
			responseContainer = "List")
	public Response getAllByStatus(
			@DefaultValue("-1") @PathParam("lotStatus") String lotStatusString,
			@DefaultValue("-1") @QueryParam("coCodes") String coCodes,
			@DefaultValue("-1") @QueryParam("limit") Integer limit,
			@DefaultValue("-1") @QueryParam("offset") Integer offset) {		
		List<Lot> lots = lotService.getByStatusAndUnion(lotStatusString, coCodes, limit, offset);
		return Response.ok().entity(lots).build();
	}
		
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@ApiOperation(response=Lot.class, value = "save the lot")
	public Response save(String  jsonString) {
		try {
			Lot lot = lotService.save(jsonString);
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
	
	@PUT
	@Path("dispatch/factory")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@ApiOperation(response=Lot.class, value = "update time to factory")
	public Response updateTimeToFactory(String jsonString, @Context HttpServletRequest request) {
		try {
			String response = lotService.updateTimeToFactory(jsonString, request);
			return Response.status(Status.CREATED).entity(response).build();
		} catch (JSONException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	@PUT
	@Path("millingTime")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@ApiOperation(response=Lot.class, value = "update time for milling")
	public Response updateMillingTime(String jsonString, @Context HttpServletRequest request) {
		try {
			String response = lotService.updateMillingTime(jsonString, request);
			return Response.status(Status.CREATED).entity(response).build();
		} catch (JSONException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	@PUT
	@Path("outTurn")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@ApiOperation(response=Lot.class, value = "update out turn time")
	public Response updateOutTurn(String jsonString, @Context HttpServletRequest request) {
		try {
			String response = lotService.updateOutTurn(jsonString, request);
			return Response.status(Status.CREATED).entity(response).build();
		} catch (JSONException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	@PUT
	@Path("dispatch/union")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@ApiOperation(response=Lot.class, value = "update time to dispatch")
	public Response dispatchToUnion(String jsonString, @Context HttpServletRequest request) {
		try {
			String response = lotService.dispatchToUnion(jsonString, request);
			return Response.status(Status.CREATED).entity(response).build();
		} catch (JSONException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	@PUT
	@Path("grnNumber")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@ApiOperation(response=Lot.class, value = "update grnNumber to factory")
	public Response updateGRNNumer(String jsonString, @Context HttpServletRequest request) {
		try {
			String response = lotService.updateGRNNumer(jsonString, request);
			return Response.status(Status.CREATED).entity(response).build();
		} catch (JSONException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}
