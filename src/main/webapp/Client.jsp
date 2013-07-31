<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>xdi2-oauth2</title>
<link rel="stylesheet" target="_blank" href="style.css" TYPE="text/css" MEDIA="screen">
</head>
<body style="background-image: url('images/back.png'); background-repeat: repeat-y; margin-left: 60px;">

	<div class="header">
	<img src="images/oauth2-logo.png" align="middle">&nbsp;&nbsp;&nbsp;
	<img src="images/arrow.png" align="middle">&nbsp;&nbsp;&nbsp;
	<img src="images/logo64.png" align="middle">&nbsp;&nbsp;&nbsp;<span id="appname">xdi2-connector-facebook</span>
	</div>

	<% if (request.getAttribute("error") != null) { %>

		<p style="font-family: monospace; white-space: pre; color: red;"><%= request.getAttribute("error") != null ? request.getAttribute("error") : "" %></p>

	<% } %>

	<% if (request.getAttribute("feedback") != null) { %>

		<p><font color="#5e1bda"><%= request.getAttribute("feedback") != null ? request.getAttribute("feedback") : "" %></font></p>

	<% } %>

	<p class="subheader">Obtain XDI2 Access Token</p>

	<p>This step will initiate an OAuth "code flow" (also known as "server-side flow") to the XDI2 server, in order to obtain an access token.</p>
	<p>The access token is then used to send an authenticated XDI message to the XDI2 server.</p>

	<form action="client" method="post">

	<% String authorizationendpoint = (String) request.getAttribute("authorizationendpoint"); if (authorizationendpoint == null) authorizationendpoint = ""; %>
	<% String clientid = (String) request.getAttribute("clientid"); if (clientid == null) clientid = ""; %>
	<% String redirecturi = (String) request.getAttribute("redirecturi"); if (redirecturi == null) redirecturi = ""; %>
	<% String scope = (String) request.getAttribute("scope"); if (scope == null) scope = ""; %>

	<table>
	<tr>
	
	<td><img src="images/oauth2-logo.png" align="middle" style="float:left;padding-right:10px;"></td>
	<td>OAuth 2.0 Authorization Endpoint: <input type="text" name="authorizationendpoint" value="<%= authorizationendpoint %>" style="width: 400px"></td>
	</tr><tr>
	
	<td><img src="images/oauth2-logo.png" align="middle" style="float:left;padding-right:10px;"></td>
	<td>OAuth 2.0 Client ID: <input type="text" name="clientid" value="<%= clientid %>" style="width: 400px"></td>

	</tr><tr>
	
	<td><img src="images/oauth2-logo.png" align="middle" style="float:left;padding-right:10px;"></td>
	<td>OAuth 2.0 Redirect URI: <input type="text" name="redirecturi" value="<%= redirecturi %>" style="width: 400px"></td>


	</table>

	<p>OAuth 2.0 Scope:</p>
	<textarea name="scope" style="width: 100%" rows="4"><%= request.getAttribute("scope") != null ? request.getAttribute("scope") : "" %></textarea>
	
	<table>

	</tr><tr>

	<td><input type="submit" name="submit" value="Request Access Token!"></td>
	<td><input type="submit" name="submit" value="Revoke Access Token!"></td>

	</tr>
	</table>
	
	</form>

</body>
</html>
