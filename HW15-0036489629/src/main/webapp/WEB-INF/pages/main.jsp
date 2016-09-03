<%@ page import="hr.fer.zemris.java.hw15.Authentication" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="header.jsp"%>

<h1>Authors</h1>
<c:choose>
    <c:when test="${authors.size() == 0}">
        empty
    </c:when>
    <c:otherwise>
        <c:forEach items="${authors}" var="i">
            <a href="author/${i.nick}"><c:out value="${i.firstName}"/> <c:out value="${i.lastName}"/> (<c:out value="${i.nick}"/>)</a>
            <br>
        </c:forEach>
    </c:otherwise>
</c:choose>

<% if (!Authentication.isUserLoggedIn(request)) { %>

<hr>

<h1>Login</h1>

<form method="post" action="" id="loginForm" class="accForm">
    <div class="row"><label for="nick">Nick:</label><input type="text" name="nick" value="<c:out value="${entry.nick}"></c:out>" id="nick"></div>
    <div class="row"><label for="password">Password:</label><input type="password" name="password" id="password"></div>
    <div class="row"><input type="submit" name="submit" value="Login"><a class="button" href="register">Register</a></div>
    <div class="cleaner"></div>
</form>

<% } %>

</div>
</body>
</html>