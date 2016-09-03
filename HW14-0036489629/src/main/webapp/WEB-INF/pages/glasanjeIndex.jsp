<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
    <title>Homework 14: Juraj Juričić - 0036489629</title>
    <meta charset="UTF-8">
    <link rel="stylesheet" href="style.css">
</head>
<body>
<h2>${title}</h2>

<p>${message}</p>

<ul>
    <c:forEach var="r" items="${options}">
        <li><a href="glasanje-glasaj?id=${r.id}">${r.optionTitle}</a> - <a href="${r.optionLink}" target="_blank">Link</a></li>
    </c:forEach>
</ul>