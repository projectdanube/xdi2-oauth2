
package xdi2.oauth2.authorization;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.oltu.oauth2.as.request.OAuthAuthzRequest;
import org.apache.oltu.oauth2.as.response.OAuthASResponse;
import org.apache.oltu.oauth2.common.exception.OAuthProblemException;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.apache.oltu.oauth2.common.message.OAuthResponse;
import org.springframework.web.HttpRequestHandler;

public class AuthorizationServlet extends HttpServlet implements HttpRequestHandler {

	private static final long serialVersionUID = -5859730421228617475L;

	@Override
	public void handleRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		this.service(request, response);
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String cloudNumber = null;
		String clientId = null;
		String redirectURI = null;
		String scope = null;

		try {

			try {

				// parse request

				cloudNumber = extractCloudNumberFromRequest(request);
				OAuthAuthzRequest oauthRequest = new OAuthAuthzRequest(request);

				clientId = oauthRequest.getClientId();
				redirectURI = oauthRequest.getRedirectURI();
				scope = oauthRequest.getScopes().iterator().next();

				// some code

				// ....

				// build response

/*				OAuthResponse oauthResponse = OAuthASResponse
						.authorizationResponse(request, HttpServletResponse.SC_FOUND)
						.setCode(oauthIssuer.authorizationCode())
						.location(redirectURI)
						.buildQueryMessage();

				response.sendRedirect(oauthResponse.getLocationUri());*/
			} catch (OAuthProblemException ex) {

				if (ex.getRedirectUri() != null) {

					OAuthResponse oauthResponse = OAuthASResponse
							.errorResponse(HttpServletResponse.SC_FOUND)
							.error(ex)
							.location(ex.getRedirectUri())
							.buildQueryMessage();

					response.sendRedirect(oauthResponse.getLocationUri());
				} else {

					throw new ServletException(ex.getMessage(), ex);
				}
			}
		} catch (OAuthSystemException ex) {

			throw new ServletException(ex.getMessage(), ex);
		}

		// display UI

		request.setAttribute("cloudnumber", cloudNumber);
		request.setAttribute("clientid", clientId);
		request.setAttribute("redirecturi", redirectURI);
		request.setAttribute("scope", scope);

		request.getRequestDispatcher("/Authorization.jsp").forward(request, response);
	}
	
	private static String extractCloudNumberFromRequest(HttpServletRequest request) {
		
		String requestURI = request.getRequestURI();
		if (requestURI.endsWith("/")) requestURI = requestURI.substring(0, requestURI.length() - 1);

		String cloudNumber = requestURI.substring(requestURI.lastIndexOf('/') + 1);
		
		return cloudNumber;
	}
}