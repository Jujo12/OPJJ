<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
    <title>Homework 14: Juraj Juričić - 0036489629</title>
    <meta charset="UTF-8">
    <link rel="stylesheet" href="style.css">
</head>
<body>
<h2>Voting results</h2>
<p>Here are the voting results.</p>
<table border="1">
    <thead>
    <tr>
        <th>Entry</th>
        <th>Score</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach var="o" items="${options}">
        <tr>
            <td>${o.optionTitle}</td>
            <td>${o.votesCount}</td>
        </tr>
    </c:forEach>
    </tbody>
</table>

<h2>Graphical representation of results</h2>
<img alt="Pie-chart" src="glasanje-grafika?pollID=${pollID}" />
<h2>Results in XLS format</h2>
<p>
    Results in XLS format are available <a href="glasanje-xls?pollID=${pollID}">here</a>.
</p>

<h2>Miscellaneous</h2>
<p>The winning options are:</p>
<ul>
    <c:forEach var="r" items="${topResults}">
        <li><a href="${r.optionLink}">${r.optionTitle}</a></li>
    </c:forEach>
</ul>