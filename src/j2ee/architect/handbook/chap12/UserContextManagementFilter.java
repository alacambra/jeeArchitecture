package j2ee.architect.handbook.chap12;

import j2ee.architect.handbook.common.user.UserContext;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

/**
 * Manages the user context for for the currently logged in user.
 * @author D. Ashmore
 *
 */
public class UserContextManagementFilter implements Filter {
	
	public static final String USER_VO_LABEL="CURRENT_USER";

	@Override
	public void destroy() {
		// NoOp

	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain filterChain) throws IOException, ServletException {
		HttpServletRequest httpRequest = (HttpServletRequest)request;
		UserContext.UserVO currentUser = 
				(UserContext.UserVO)httpRequest.getSession().getAttribute(USER_VO_LABEL);
		
		if (currentUser != null) {
			new UserContext(currentUser);
		}
		else if (httpRequest.getUserPrincipal() != null 
				&& httpRequest.getUserPrincipal().getName() != null) {
			currentUser = new UserContext.UserVO();
			// TODO Put logic in to lookup user information, populate currentUser
			
			new UserContext(currentUser);
			httpRequest.getSession().setAttribute(USER_VO_LABEL, currentUser);
		}
		else {UserContext.clear();}

		try {filterChain.doFilter(request, response);}
		finally {UserContext.clear();}	

	}

	@Override
	public void init(FilterConfig config) throws ServletException {
		// NoOp

	}

}
