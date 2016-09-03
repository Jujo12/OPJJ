<%@ page import="hr.fer.zemris.java.hw15.model.BlogUser" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="header.jsp"%>

<h1>Author page: ${author.firstName} ${author.lastName}
<% if (Authentication.isUserLoggedIn(request) &&
        Authentication.getCurrentUser(request).getId() == ((BlogUser)request.getAttribute("author")).getId()){ %>
    <div class="right"><a href="${author.nick}/new" class="button">new post</a></div>
<% } %>
</h1>

<c:choose>
    <c:when test="${posts.size() == 0}">
        ${author.firstName} ${author.lastName} has no posts at the moment.
    </c:when>
    <c:otherwise>
        <c:forEach items="${posts}" var="i">
            <a href="${author.nick}/${i.id}">${i.title}</a>
            <br>
        </c:forEach>
    </c:otherwise>
</c:choose>

<div class="cleaner"></div>

</div>
</body>
</html>