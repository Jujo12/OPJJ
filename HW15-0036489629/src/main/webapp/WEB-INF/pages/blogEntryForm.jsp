<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="header.jsp"%>

<form method="post" action="" id="blogEntryForm">
    <input type="hidden" name="id" value="${entry.id}">
    <input type="text" name="title" placeholder="Title" value="${entry.title}">
    <textarea name="text" placeholder="Blog entry text">${entry.text}</textarea>
    <input type="submit" value="Save">
</form>

</div>
</body>
</html>