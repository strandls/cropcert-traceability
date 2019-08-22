package cropcert.traceability.util;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.HttpHeaders;

import org.pac4j.core.profile.CommonProfile;

import cropcert.traceability.filter.JWTTokenValidationFilter;


public class UserUtil {

	public static String getUserDetails(HttpServletRequest request) {

		String authorizationHeader = ((HttpServletRequest) request).getHeader(HttpHeaders.AUTHORIZATION);
		
		CommonProfile profile = JWTTokenValidationFilter.getCommonProfile(authorizationHeader);
		
		return profile.getEmail();
	}
}
