package cropcert.traceability.lot;

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

@Path("lot")
public class LotEndPoint {

	
	private LotService lotService;
	
	@Inject
	public LotEndPoint(LotService batchProductionService) {
		this.lotService = batchProductionService;
	}
	
	@Path("{id}")
	@GET
	@Consumes(MediaType.TEXT_PLAIN)
	@Produces(MediaType.APPLICATION_JSON)
	public Response find(@PathParam("id") Long id) {
		Lot lot = lotService.findById(id);
		return Response.status(Status.CREATED).entity(lot).build();
	}
	
	@Path("all")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Lot> findAll(
			@DefaultValue("-1") @QueryParam("limit") Integer limit,
			@DefaultValue("-1") @QueryParam("offset") Integer offset) {
		if(limit==-1 || offset ==-1)
			return lotService.findAll();
		else
			return lotService.findAll(limit, offset);
	}
		
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
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
	
	/**
	 * Generic update of the lot from json. not recommended to use.
	 * @param jsonString
	 * @return
	 */
	@PUT
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response update(String jsonString) {
		Lot lot;
		try {
			lot = lotService.update(jsonString);
			return Response.status(Status.CREATED).entity(lot).build();
		} catch (JSONException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	@PUT
	@Path("dispatch")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
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
}
