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

import cropcert.traceability.model.Activity;
import cropcert.traceability.model.Batch;
import cropcert.traceability.model.Lot;
import cropcert.traceability.service.ActivityService;
import cropcert.traceability.util.UserUtil;
import io.swagger.annotations.Api;

@Path("activity")
@Api("Activity")
public class ActivityApi {
	
	private ActivityService activityService;
	
	@Inject
	public ActivityApi(ActivityService batchProductionService) {
		this.activityService = batchProductionService;
	}
	
	@Path("{id}")
	@GET
	@Consumes(MediaType.TEXT_PLAIN)
	@Produces(MediaType.APPLICATION_JSON)
	public Response find(@PathParam("id") Long id) {
		Activity activity = activityService.findById(id);
		return Response.status(Status.CREATED).entity(activity).build();
	}
	
	@Path("all")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Activity> findAll(
			@DefaultValue("-1") @QueryParam("limit") Integer limit,
			@DefaultValue("-1") @QueryParam("offset") Integer offset) {
		if(limit==-1 || offset ==-1)
			return activityService.findAll();
		else
			return activityService.findAll(limit, offset);
	}
	
	@Path("batch")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Activity> getByBatchId(
			@DefaultValue("-1") @QueryParam("batchId") Long batchId,
			@DefaultValue("-1") @QueryParam("limit") Integer limit,
			@DefaultValue("-1") @QueryParam("offset") Integer offset) {
		if(batchId == -1) {
			String [] properties = {"objectType"};
			Object [] values     = {Batch.class.getSimpleName()};
			return activityService.getByMultiplePropertyWithCondtion(properties, values, limit, offset);
		}
		String [] properties = {"objectType", "objectId"};
		Object [] values     = {Batch.class.getSimpleName(), batchId};
		return activityService.getByMultiplePropertyWithCondtion(properties, values, limit, offset);
	}
	
	@Path("lot")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Activity> getByLotId(
			@DefaultValue("-1") @QueryParam("lotId") Long lotId,
			@DefaultValue("-1") @QueryParam("limit") Integer limit,
			@DefaultValue("-1") @QueryParam("offset") Integer offset) {
		if(lotId == -1) {
			String [] properties = {"objectType"};
			Object [] values     = {Lot.class.getSimpleName()};
			return activityService.getByMultiplePropertyWithCondtion(properties, values, limit, offset);
		}
		String [] properties = {"objectType", "objectId"};
		Object [] values     = {Lot.class.getSimpleName(), lotId};
		return activityService.getByMultiplePropertyWithCondtion(properties, values, limit, offset);
	}
	
	@Path("user")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Activity> getByUserId(
			@DefaultValue("") @QueryParam("userId") String userId,
			@DefaultValue("-1") @QueryParam("limit") Integer limit,
			@DefaultValue("-1") @QueryParam("offset") Integer offset,
			@Context HttpServletRequest request) {
		if("".equals(userId) || userId == null)
			userId = UserUtil.getUserDetails(request);
		String [] properties = {"userId"};
		Object [] values     = {userId};
		return activityService.getByMultiplePropertyWithCondtion(properties, values, limit, offset);
	}
	
	@Path("lot/user")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Activity> getByLotAndUserId(
			@DefaultValue("-1") @QueryParam("lotId") Long lotId,
			@DefaultValue("") @QueryParam("userId") String userId,
			@DefaultValue("-1") @QueryParam("limit") Integer limit,
			@DefaultValue("-1") @QueryParam("offset") Integer offset,
			@Context HttpServletRequest request) {
		if("".equals(userId) || userId == null)
			userId = UserUtil.getUserDetails(request);
		if(lotId == -1) {
			String [] properties = {"objectType", "userId"};
			Object [] values     = {Lot.class.getSimpleName(), userId};
			return activityService.getByMultiplePropertyWithCondtion(properties, values, limit, offset);
		}
		String [] properties = {"objectType", "objectId", "userId"};
		Object [] values     = {Lot.class.getSimpleName(), lotId, userId};
		return activityService.getByMultiplePropertyWithCondtion(properties, values, limit, offset);
	}
	
	@Path("batch/user")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Activity> getByBatchAndUserId(
			@DefaultValue("-1") @QueryParam("batchId") Long batchId,
			@DefaultValue("") @QueryParam("userId") String userId,
			@DefaultValue("-1") @QueryParam("limit") Integer limit,
			@DefaultValue("-1") @QueryParam("offset") Integer offset,
			@Context HttpServletRequest request) {
		if("".equals(userId) || userId == null)
			userId = UserUtil.getUserDetails(request);
		if(batchId == -1) {
			String [] properties = {"objectType", "userId"};
			Object [] values     = {Batch.class.getSimpleName(), userId};
			return activityService.getByMultiplePropertyWithCondtion(properties, values, limit, offset);
		}
		String [] properties = {"objectType", "objectId", "userId"};
		Object [] values     = {Batch.class.getSimpleName(), batchId, userId};
		return activityService.getByMultiplePropertyWithCondtion(properties, values, limit, offset);
	}
	
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response save(String  jsonString) {
		try {
			Activity activity = activityService.save(jsonString);
			return Response.status(Status.CREATED).entity(activity).build();
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
