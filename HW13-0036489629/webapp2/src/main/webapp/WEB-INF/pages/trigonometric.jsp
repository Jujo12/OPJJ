<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="header.jsp"%>
<table border="1">
	<tr>
		<th>x</th>
		<th>sin(x)</th>
		<th>cos(x)</th>
	</tr>
	<c:forEach var="r" items="${results}">
		<tr>
			<td>${r.degrees}°</td>
			<td><fmt:formatNumber type="number" groupingUsed="false" minFractionDigits="6" maxFractionDigits="6" value="${r.sin}" /></td>
			<td><fmt:formatNumber type="number" groupingUsed="false" minFractionDigits="6" maxFractionDigits="6" value="${r.cos}" /></td>
		</tr>
	</c:forEach>
</table>