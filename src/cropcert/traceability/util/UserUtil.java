package cropcert.traceability.util;

import javax.servlet.http.HttpServletRequest;

import org.jasig.cas.client.validation.Assertion;


public class UserUtil {

	public static String getUserDetails(HttpServletRequest request) {
		Assertion assertion = (Assertion) request.getSession().getAttribute("_const_cas_assertion_");
		String email        = assertion.getPrincipal().getName();
		return email; 
	}
}
