<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="header.jsp"%>
<h1>Registration</h1>

<form method="post" action="" id="registrationForm" class="accForm">
    <div class="row"><label for="nick">Nick:</label><input type="text" name="nick" value="<c:out value="${entry.nick}"></c:out>" id="nick"></div>
    <div class="row"><label for="password">Password:</label><input type="password" name="password" id="password"></div>
    <div class="row"><label for="email">E-mail:</label><input type="email" name="email" value="<c:out value="${entry.email}"></c:out>" id="email"></div>
    <div class="row"><label for="firstName">First name:</label><input type="text" name="firstName" value="<c:out value="${entry.firstName}"></c:out>" id="firstName"></div>
    <div class="row"><label for="lastName">Last name:</label><input type="text" name="lastName" value="<c:out value="${entry.lastName}"></c:out>" id="lastName"></div>
    <div class="row"><input type="submit" name="submit" value="Register"></div>
    <div class="cleaner"></div>
</form>

</div>
</body>
</html>