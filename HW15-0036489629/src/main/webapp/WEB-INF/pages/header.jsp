<%@ page import="hr.fer.zemris.java.hw15.Authentication" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page session="true" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!doctype html>
<html>
<head>
    <title>Blog</title>
    <meta charset="UTF-8">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/style.css">
</head>
<body>
<div id="header">
    <div class="container">
        <a href="${pageContext.request.contextPath}"><div id="logo">OPJJ Blog</div></a>
        <div id="userBar">
            <% if (!Authentication.isUserLoggedIn(request)){ %>
                <a href="${pageContext.request.contextPath}">Login</a>
            <% }else{ %>
                Hello, <%= Authentication.getCurrentUser(request).getFirstName() %>. <a href="${pageContext.request.contextPath}/servleti/logout">Log out</a>
            <% } %>
        </div>
        <div class="cleaner"></div>
    </div>
</div>
<div class="content container">