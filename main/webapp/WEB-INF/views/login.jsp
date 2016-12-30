<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<!DOCTYPE html>
<html>
<head>
    <spring:url value="/resources/style-sheets/login.css" var="styleSheet"/>
    <link rel="stylesheet" type="text/css" href="${styleSheet}">
    <title>Login</title>
    <script src="https://www.google.com/recaptcha/api.js" async defer></script>
</head>
<body class="login">
<form:form class="form" method="post" action="/login" modelAttribute="user">
    <legend>LOG IN</legend>
    <form:input type="email" path="email" placeholder="Email Address"/>
    <form:password path="password" placeholder="Password"/><br>
    <div class="g-recaptcha" data-sitekey="6LeTFg4UAAAAANHiHf2jFWzi1CTDe8LaKtAxcrRq"></div>
    <input type="submit" value="Login"/>
    <p>${message}</p>
</form:form>
<div class="footer">
    Don't have an account? <a href="/signup">Sign Up</a>
</div>
</body>
</html>
