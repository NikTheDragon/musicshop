<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>

<html>
<head>
    <link href="<c:url value="/css/main.css" />" rel="stylesheet">
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>reg page</title>

    <%@include file="/WEB-INF/jspf/locale.jsp" %>
</head>
<body style="font-family: Arial, Helvetica, sans-serif">

<%@include file="/WEB-INF/jspf/header.jsp" %>
<%@include file="/WEB-INF/jspf/menu.jsp" %>

<table style="width: 80%; margin-left: auto; margin-right: auto;">
    <tr>
        <td colspan="2"><p>${fillAllFields}</p></td>
    </tr>

    <tr title="name input field">
        <td style="width: 220px">
            <input type="text" id="userName" name="submit_name" placeholder="${name}" value="${user.name}"
                   style="width: 200Px">
        </td>
        <td style="text-align:left">
            <c:set var="message" value="${nameResult}"/>
            <%@include file="/WEB-INF/jspf/error_handler.jsp" %>
        </td>
    </tr>
    <tr title="surname input field">
        <td>
            <input type="text" id="userSurname" name="submit_surname" placeholder="${surname}" value="${user.surname}"
                   style="width: 200Px">
        </td>
        <td style="text-align:left">
            <c:set var="message" value="${surnameResult}"/>
            <%@include file="/WEB-INF/jspf/error_handler.jsp" %>
        </td>
    </tr>
    <tr title="login input field">
        <td>
            <input type="text" id="userLogin" name="submit_login" placeholder="${login}" value="${user.login}"
                   style="width: 200Px">
        </td>
        <td style="text-align:left">
            <c:set var="message" value="${loginResult}"/>
            <%@include file="/WEB-INF/jspf/error_handler.jsp" %>
        </td>
    </tr>
    <tr title="password input field">
        <td>
            <input type="text" id="userPassword" name="submit_password" placeholder="${password}"
                   value="${user.password}"
                   style="width: 200Px">
        </td>
        <td style="text-align:left">
            <c:set var="message" value="${passwordResult}"/>
            <%@include file="/WEB-INF/jspf/error_handler.jsp" %>
        </td>
    </tr>
    <tr title="email input field">
        <td>
            <input type="text" id="userEmail" name="submit_email" placeholder="e-mail" value="${user.email}"
                   style="width: 200Px">
        </td>
        <td style="text-align:left">
            <c:set var="message" value="${emailResult}"/>
            <%@include file="/WEB-INF/jspf/error_handler.jsp" %>
        </td>
    </tr>
    <tr>
        <td title="submit button">
            <input type="hidden" name="command" value="reg_new_user">
            <input type="button" value="${registerButton}" onclick="submitButton('addNewUser')" style="width: 200Px">
        </td>
        <td></td>
    </tr>
</table>

<form id="addNewUser" action="${absolutePath}/mainServlet" method="get">
    <input type="hidden" name="command" value="reg_new_user">
    <input type="hidden" id="submit_id" name="submit_id" value="0">
    <input type="hidden" id="submit_name" name="submit_name" value="">
    <input type="hidden" id="submit_surname" name="submit_surname" value="">
    <input type="hidden" id="submit_login" name="submit_login" value="">
    <input type="hidden" id="submit_password" name="submit_password" value="">
    <input type="hidden" id="submit_email" name="submit_email" value="">
    <input type="hidden" id="submit_role" name="submit_role" value="user">
    <input type="hidden" id="submit_status" name="submit_status" value="active">
    <input type="hidden" id="submit_points" name="submit_points" value="0">
</form>

<script>

    function submitButton(formName) {
        var userName = document.getElementById("userName").value;
        var userSurname = document.getElementById("userSurname").value;
        var userLogin = document.getElementById("userLogin").value;
        var userPassword = document.getElementById("userPassword").value;
        var userEmail = document.getElementById("userEmail").value;

        document.getElementById("submit_name").value = userName;
        document.getElementById("submit_surname").value = userSurname;
        document.getElementById("submit_login").value = userLogin;
        document.getElementById("submit_password").value = userPassword;
        document.getElementById("submit_email").value = userEmail;

        document.getElementById(formName).submit();
    }

</script>

</body>
</html>
