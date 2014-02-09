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

	<p class="subheader">Request for XDI2 Access Token</p>

	<p>We have received an OAuth 2.0 request to access your XDI endpoint (the protected resource).</p>

	<form action="oauth2-authorization" method="post">

	<% String cloudnumber = (String) request.getAttribute("cloudnumber"); if (cloudnumber == null) cloudnumber = ""; %>
	<% String clientid = (String) request.getAttribute("clientid"); if (clientid == null) clientid = ""; %>
	<% String scope = (String) request.getAttribute("scope"); if (scope == null) scope = ""; %>
	<% String redirecturi = (String) request.getAttribute("redirecturi"); if (redirecturi == null) redirecturi = ""; %>
	<% String state = (String) request.getAttribute("state"); if (state == null) state = ""; %>

	<table>
	<tr>
	
	<td>Cloud Number: <span><%= cloudnumber %></span></td>

	</tr><tr>
	
	<td>Client ID: <span><%= clientid %></span></td>

	</tr><tr>

	<td>Scope: <span><%= scope %></span></td>

	</tr><tr>

	<td>Redirect URI: <span><%= redirecturi %></span></td>

	</tr><tr>

	<td>State: <span><%= state %></span></td>

	</tr>
	</table>

	</form>

</body>
</html>
