package xdi2.oauth2.token;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.oltu.oauth2.as.issuer.MD5Generator;
import org.apache.oltu.oauth2.as.issuer.OAuthIssuer;
import org.apache.oltu.oauth2.as.issuer.OAuthIssuerImpl;
import org.apache.oltu.oauth2.as.request.OAuthTokenRequest;
import org.apache.oltu.oauth2.as.response.OAuthASResponse;
import org.apache.oltu.oauth2.common.exception.OAuthProblemException;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.apache.oltu.oauth2.common.message.OAuthResponse;
import org.springframework.web.HttpRequestHandler;

public class TokenServlet extends HttpServlet implements HttpRequestHandler {

	private static final long serialVersionUID = -2846188284801824380L;

	@Override
	public void handleRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		this.service(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		OAuthIssuer oauthIssuer = new OAuthIssuerImpl(new MD5Generator());

		try {

			try {

				OAuthTokenRequest oauthRequest = new OAuthTokenRequest(request);

				String authorizationCode = oauthRequest.getCode();

				// some code

				String accessToken = oauthIssuer.accessToken();
				String refreshToken = oauthIssuer.refreshToken();

				// build response

				OAuthResponse oauthResponse = OAuthASResponse
						.tokenResponse(HttpServletResponse.SC_OK)
						.setAccessToken(accessToken)
						.setExpiresIn("3600")
						.setRefreshToken(refreshToken)
						.buildJSONMessage();

				response.setStatus(oauthResponse.getResponseStatus());

				PrintWriter writer = response.getWriter();
				writer.print(oauthResponse.getBody());
				writer.flush();
				writer.close();
			} catch (OAuthProblemException ex) {

				OAuthResponse oauthResponse = OAuthResponse
						.errorResponse(401)
						.error(ex)
						.buildJSONMessage();

				response.setStatus(oauthResponse.getResponseStatus());

				PrintWriter writer = response.getWriter();
				writer.print(oauthResponse.getBody());
				writer.flush();
				writer.close();

				response.sendError(401);
			}
		} catch (OAuthSystemException ex) {

			throw new ServletException(ex.getMessage(), ex);
		}
	}
}