<%@ page import="java.util.Date" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="WEB-INF/pages/header.jsp" %>
<h2>App uptime</h2>

<%
	Date start = (Date) getServletContext().getAttribute("startTime");
	
	//seconds
	long s = (new Date().getTime() - start.getTime()) / 1000;
	
	//days
	long d = s / (3600 * 24);
	s %= (3600*24);
	
	//hours
	long h = s / 3600;
	s %= 3600;
	
	//minutes
	long m = s / 60;
	s %= 60;
%>

   <p>Application uptime: <%= d %> days <%= h %> hours <%= m %> minutes <%= s %> seconds</p>
   
</body>
</html>
