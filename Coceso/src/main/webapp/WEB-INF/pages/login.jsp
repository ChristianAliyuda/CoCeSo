<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<html>
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <!--link rel="shortcut icon" href=""-->

    <title>Login</title>

    <c:url var="bootstrap" value="/static/bootstrap.css" />
    <link href="${bootstrap}" rel="stylesheet">

    <c:url var="bootstrap_theme" value="/static/bootstrap-theme.css" />
    <link href="${bootstrap_theme}" rel="stylesheet">

    <c:url var="bootstrap_signin" value="/static/signin.css" />
    <link href="${bootstrap_signin}" rel="stylesheet">

</head>

<body>
<!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
<c:url var="jquery" value="/static/jquery.js" />
<script src="${jquery}"></script>
<!-- Include all compiled plugins (below), or include individual files as needed -->
<c:url var="bootstrap_js" value="/static/bootstrap.js" />
<script src="${bootstrap_js}"></script>

<div class="container">
    <c:if test="${not empty error}">
        <div class="alert alert-danger">
            Your login attempt was not successful, try again.<br />
            Is your Account enabled?
        </div>
    </c:if>

    <form class="form-signin" action="<c:url value='j_spring_security_check' />" method="POST">
        <h2 class="form-signin-heading">Coceso Login<br />(NIU User)</h2>
        <input type="text" class="form-control" name="j_username" placeholder="Username" required autofocus>
        <input type="password" class="form-control" name="j_password" placeholder="Password" required>
        <button class="btn btn-lg btn-primary btn-block" type="submit">Login</button>
    </form>

</div>
</body>
</html>
