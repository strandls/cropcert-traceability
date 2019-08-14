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
import cropcert.traceability.service.BatchService;
import cropcert.traceability.util.UserUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

@Path("batch")
@Api("Batch")
@ApiImplicitParams({
    @ApiImplicitParam(name = "Authorization", value = "Authorization token", 
                      required = true, dataType = "string", paramType = "header") })
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
	@ApiOperation(
			value = "Get the batch by id",
			response = Batch.class)
	public Response find(@PathParam("id") Long id, @Context HttpServletRequest request) {
		Batch batchProduction = batchService.findById(id);
		String user = UserUtil.getUserDetails(request);
		System.out.println(user);
		return Response.status(Status.CREATED).entity(batchProduction).build();
	}
	
	@Path("all")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(
			value = "Get all the batches",
			response = List.class)
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
	@ApiOperation(
			value = "Get list of batches by cc codes",
			response = List.class)
	public List<Batch> getByCcCodes(
			@DefaultValue("-1") @QueryParam("ccCodes") String ccCodes,
			@DefaultValue("false") @QueryParam("isLotDone") Boolean isLotDone,
			@DefaultValue("true") @QueryParam("isReadyForLot") Boolean isReadyForLot,
			@DefaultValue("-1") @QueryParam("limit") Integer limit,
			@DefaultValue("-1") @QueryParam("offset") Integer offset) {
		
		return batchService.getByPropertyfromArray("ccCode", ccCodes, isLotDone, isReadyForLot, limit, offset);
	}
	
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@ApiOperation(
			value = "Save the batch",
			response = Batch.class)
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
