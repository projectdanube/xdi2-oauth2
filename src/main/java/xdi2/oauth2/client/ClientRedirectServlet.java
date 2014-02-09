package xdi2.oauth2.client;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.HttpRequestHandler;

public class ClientRedirectServlet extends HttpServlet implements HttpRequestHandler {

	private static final long serialVersionUID = -9068348992575289725L;

	private static final Logger log = LoggerFactory.getLogger(ClientRedirectServlet.class);

	public ClientRedirectServlet() {

	}

	@Override
	public void handleRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		this.service(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		log.debug("Incoming POST request to " + request.getRequestURL() + ". Content-Type: " + request.getContentType() + ", Content-Length: " + request.getContentLength());

		// error from OAuth?

		if (request.getParameter("error") != null) {

			request.setAttribute("error", request.getParameter("error"));
		}

		// callback from OAuth?

		if (request.getParameter("code") != null) {

		}

		// display results

		request.getRequestDispatcher("/ClientRedirect.jsp").forward(request, response);
	}
}
