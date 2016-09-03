<%@ page import="hr.fer.zemris.java.hw15.model.BlogEntry" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="header.jsp"%>

<h1 class="entryTitle">${entry.title}</h1>
<% if (Authentication.isUserLoggedIn(request) &&
        Authentication.getCurrentUser(request).getId()
                == ((BlogEntry)request.getAttribute("entry")).getCreator().getId()){ %>
<div class="right"><a href="edit?id=${entry.id}" class="button">edit</a></div>
<% } %>
<div class="cleaner"></div>

${entry.text}

<div class="cleaner"></div>

</div>

<div class="content container">
    <c:forEach items="${entry.comments}" var="i">
        <b>${i.usersEMail}</b>
        <p>${i.message}</p>
        <hr>
    </c:forEach>

    <form action="" method="post" id="commentForm">
        <input type="hidden" name="entryId" value="${entry.id}">
        <input type="email" name="email" placeholder="E-mail">
        <textarea name="message" placeholder="Comment..."></textarea>
        <input type="submit" value="Comment" name="comment">
    </form>
    <div class="cleaner"></div>
</div>

</body>
</html>