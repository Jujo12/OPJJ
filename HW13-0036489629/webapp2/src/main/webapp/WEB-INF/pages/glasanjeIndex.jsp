<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="header.jsp"%>

<h2>Band voting page</h2>
<p>Vote for one of the following bands:</p>

<ul>
	<c:forEach var="r" items="${bands}">
		<li><a href="glasanje-glasaj?id=${r.id}">${r.name}</a> - <a href="${r.songUrl}" target="_blank">LISTEN</a></li>
	</c:forEach>
</ul>