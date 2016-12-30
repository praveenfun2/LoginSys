<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" %>
<!DOCTYPE html>
<html>
<head>
    <spring:url value="/resources/style-sheets/login.css" var="styleSheet"/>
    <link rel="stylesheet" type="text/css" href="${styleSheet}">
    <title>Sign Up</title>
</head>
<body class="login">
<form:form cssClass="form" method="post" action="/signup" modelAttribute="User">
    <legend>SIGN UP</legend>
    <div>
        <form:radiobutton path="userType" value="1" label="Type 1"/>
        <form:radiobutton path="userType" value="2" label="Type 2"/>
        <form:radiobutton path="userType" value="3" label="Type 3"/>
    </div>
    <form:input type="email" placeholder="Email Address" path="email"/><br>
    <form:password path="password" placeholder="Password"/><br>
    <input type="submit" value="Sign Up"/>
    <p>${message}</p>
</form:form>
</body>
</html>
