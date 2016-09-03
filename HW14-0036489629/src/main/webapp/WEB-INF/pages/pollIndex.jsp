<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
    <title>Homework 14: Juraj Juričić - 0036489629</title>
    <meta charset="UTF-8">
    <link rel="stylesheet" href="style.css">
</head>
<body>
<h2>Polls</h2>
<p>Here are the available polls:</p>

<ol>
    <c:forEach var="r" items="${polls}">
        <li><a href="glasanje?pollID=${r.id}">${r.title}</a></li>
    </c:forEach>
</ol>
</body>