<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="header.jsp"%>

<h2>Voting results</h2>
<p>Here are the voting results.</p>
<table border="1">
	<thead>
		<tr>
			<th>Band</th>
			<th>Score</th>
		</tr>
	</thead>
	<tbody>
		<c:forEach var="b" items="${bands}">
			<tr>
				<td>${b.name}</td>
				<td>${b.score}</td>
			</tr>
		</c:forEach>
	</tbody>
</table>

<h2>Graphical representation of results</h2>
<img alt="Pie-chart" src="glasanje-grafika" />
<h2>Results in XLS format</h2>
<p>
	Results in XLS format are available <a href="glasanje-xls">here</a>.
</p>

<h2>Miscellaneous</h2>
<p>The winning bands and their sample songs are:</p>
<ul>
	<c:forEach var="r" items="${topBands}">
		<li><a href="${r.songUrl}">${r.name}</a></li>
	</c:forEach>
</ul>