package xdi2.oauth2.client;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.binary.Base64;
import org.apache.oltu.oauth2.client.request.OAuthClientRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.HttpRequestHandler;

public class ClientServlet extends HttpServlet implements HttpRequestHandler {

	private static final long serialVersionUID = -9068348992575289725L;

	private static final Logger log = LoggerFactory.getLogger(ClientServlet.class);

	static String sampleInput;
	static String sampleAuthorizationendpoint;
	static String sampleRedirecturi;
	static String sampleClientid;

	static {

		InputStream inputStream = ClientServlet.class.getResourceAsStream("message.xdi");
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		int i;

		try {

			while ((i = inputStream.read()) != -1) outputStream.write(i);
			sampleInput = new String(outputStream.toByteArray());
		} catch (Exception ex) {

		} finally {

			try {

				inputStream.close();
				outputStream.close();
			} catch (Exception ex) {

			}
		}

		sampleAuthorizationendpoint = "/xdi/oauth2-authorization"; 
		sampleRedirecturi = "/client-redirect"; 
		sampleClientid = "[=]!:uuid:e2ad93e9-511b-4334-9f33-0b29b435bf95"; 
	}

	public ClientServlet() {

	}

	@Override
	public void handleRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		this.service(request, response);
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		log.debug("Incoming GET request to " + request.getRequestURL() + ". Content-Type: " + request.getContentType() + ", Content-Length: " + request.getContentLength());

		// start OAuth?

		if ("Request Access Token!".equals(request.getParameter("submit"))) {

			String input = request.getParameter("input");
			String authorizationendpoint = request.getParameter("authorizationendpoint");
			String redirecturi = request.getParameter("authorizationendpoint");
			String clientid = request.getParameter("clientid");

			String scope;

			// parse the input

			String[] lines = input.split("\n");
			StringBuilder buffer = new StringBuilder();

			for (String line : lines) buffer.append(Base64.encodeBase64URLSafeString(line.getBytes("UTF-8")) + " ");

			scope = buffer.toString();
			scope = scope.substring(0, scope.length() - 1);

			log.debug("Scope: " + scope);

			// issue the OAuth2 redirect

			try {

				OAuthClientRequest oauthClientRequest = OAuthClientRequest
						.authorizationLocation(authorizationendpoint)
						.setClientId(clientid)
						.setRedirectURI(redirecturi)
						.buildQueryMessage();

				response.sendRedirect(oauthClientRequest.getLocationUri());
			} catch (Exception ex) {

				request.setAttribute("error", ex.getMessage());
			}
		}

		// revoke OAuth?

		if ("Revoke Access Token!".equals(request.getParameter("submit"))) {

			request.setAttribute("error", "Not implemented.");
		}

		// display results

		request.setAttribute("input", sampleInput);
		request.setAttribute("authorizationendpoint", request.getRequestURL().substring(0, request.getRequestURL().lastIndexOf("/")) + sampleAuthorizationendpoint);
		request.setAttribute("redirecturi", request.getRequestURL().substring(0, request.getRequestURL().lastIndexOf("/")) + sampleRedirecturi);
		request.setAttribute("clientid", sampleClientid);

		request.getRequestDispatcher("/Client.jsp").forward(request, response);
	}
}
