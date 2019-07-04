package cropcert.traceability;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Enumeration;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.json.JSONException;
import org.json.JSONObject;

@Path("client")
public class JavaClient {
	
	public String getMethod(String urlString, HttpServletRequest request) throws IOException {
		Cookie [] cookies = request.getCookies();
		URL url = new URL(urlString);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod("GET");
		conn.setRequestProperty("content-type", "application/json");
		
		System.out.println(cookies.toString());

		Enumeration<String> headers = request.getHeaderNames();
		while (headers.hasMoreElements()) {
			String header = (String) headers.nextElement();
			System.out.println(header);
			System.out.println(request.getHeader(header));
			conn.setRequestProperty(header, request.getHeader(header));			
		}
		conn.setDoOutput(true);
		
		System.out.println(cookies);
		
		if (conn.getResponseCode() != 200) {
			throw new RuntimeException("Failed : HTTP error code : "
					+ conn.getResponseCode());
		}

		BufferedReader br = new BufferedReader(new InputStreamReader(
			(conn.getInputStream())));

		String output;
		System.out.println("Output from Server .... \n");
		while ((output = br.readLine()) != null) {
			System.out.println(output);
		}

		conn.disconnect();
		return output;
	}
	
	@Path("dummy")
	@GET
	@Consumes(MediaType.TEXT_PLAIN)
	@Produces(MediaType.APPLICATION_JSON)
	public Response dummyData(@Context HttpServletRequest request ) {
		JSONObject jsonObject = new JSONObject();
		try {
			jsonObject.put("dummy", "dummy");
			JavaClient javaClient = new JavaClient();
			String output = javaClient.getMethod("https://localhost:8443/traceabilty/lot/502", request);
			return Response.status(Status.CREATED).entity(output).build();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}
