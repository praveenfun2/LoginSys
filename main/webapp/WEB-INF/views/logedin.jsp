<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1" %>
<!DOCTYPE html>
<html>
<head>
    <style>
        body{
            background:#0080ff;
            font-family:"Courier New", "sans-serif";
            padding:0;
            margin:0
        }
        div{
            padding: 10px;
            background:white;
            width: 400px;
            height: auto;
            margin: auto;
            border: 2px solid grey;
        }
        h2{
            padding: 15px;
            text-align: justify;
            margin: 0 auto 15px;
            font-size: 25px;
            font-weight: 700;
        }
        a{
            background: #15CD72;
            font-size: 25px;
            color: #E7F1F9;
            display: block;
            text-align: center;
            width: 370px;
            height: auto;
            font-weight: 700;
            margin-top:15px;
            padding: 15px;
        }
    </style>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <title>Result</title>
</head>

<body class="logout">
<div>
    <h2>${message}</h2>
    <a id="button" href="/logout">Log Out</a>
</div>

</body>
</html>