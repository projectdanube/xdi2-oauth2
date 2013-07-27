package xdi2.oauth2.authorization;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.oltu.oauth2.as.issuer.MD5Generator;
import org.apache.oltu.oauth2.as.issuer.OAuthIssuer;
import org.apache.oltu.oauth2.as.issuer.OAuthIssuerImpl;
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

		OAuthIssuer oauthIssuer = new OAuthIssuerImpl(new MD5Generator());

		try {

			try {

				// parse request

				OAuthAuthzRequest oauthRequest = new OAuthAuthzRequest(request);

				String redirectURI = oauthRequest.getRedirectURI();

				// some code

				// ....

				// build response

				OAuthResponse oauthResponse = OAuthASResponse
						.authorizationResponse(request, HttpServletResponse.SC_FOUND)
						.setCode(oauthIssuer.authorizationCode())
						.location(redirectURI)
						.buildQueryMessage();

				response.sendRedirect(oauthResponse.getLocationUri());
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
	}
	}