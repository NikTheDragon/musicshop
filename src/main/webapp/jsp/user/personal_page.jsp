<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>

<html>
<head>
    <link href="<c:url value="/css/main.css" />" rel="stylesheet">
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>personal info</title>

    <script src="/js/jquery.min.js"></script>

    <%@include file="/WEB-INF/jspf/locale.jsp" %>

</head>

<c:set var="user_role" scope="session" value="${user.role}"/>
<c:if test="${user_role != 'admin'}">
    <c:redirect url="/mainServlet?command=show_main_page"/>
</c:if>

<body style="font-family: Arial, Helvetica, sans-serif">

<%@include file="/WEB-INF/jspf/header.jsp" %>
<%@include file="/WEB-INF/jspf/admin_menu.jsp" %>

<br>

<table width="100%">
    <tr>
        <td style="text-align: center">
            <h2>Личные данные</h2>
        </td>
    </tr>
</table>

<table style="width: 90%; margin-left: auto; margin-right: auto;">
    <tr style="text-align: center">
        <td style="width: 25%">${name}</td>
        <td style="width: 25%">${surname}</td>
        <td style="width: 15%">${login}</td>
        <td style="width: 15%">${password}</td>
        <td style="width: 15%">email</td>
    </tr>
    <tr style="text-align: center">
        <td>
            <input type="text" id="userName" name="submit_name" value="${user.name}">
        </td>
        <td>
            <input type="text" id="userSurname" name="submit_surname" value="${user.surname}">
        </td>
        <td>
            <input type="text" id="userLogin" name="submit_login" value="${user.login}">
        </td>
        <td>
            <input type="text" id="userPassword" name="submit_password" value="${user.password}">
        </td>
        <td>
            <input type="text" id="userEmail" name="submit_email" value="${user.email}">
        </td>
    </tr>
</table>

<form id="updateUser" action="/mainServlet" method="get">
    <input type="hidden" name="command" value="update_personal_info">
    <input type="hidden" id="submit_id" name="submit_id" value="${user.id}">
    <input type="hidden" id="submit_name" name="submit_name" value="">
    <input type="hidden" id="submit_surname" name="submit_surname" value="">
    <input type="hidden" id="submit_login" name="submit_login" value="">
    <input type="hidden" id="submit_password" name="submit_password" value="">
    <input type="hidden" id="submit_email" name="submit_email" value="">
</form>

<table style="width: 90%; margin-left: auto; margin-right: auto;">
    <tr style="text-align: right">
        <td>
            <input type="button" value="${updateButton}" onclick="submitButton('updateUser')" style="width: 200px">
        </td>
    </tr>
</table>

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

        if (userName != "" && userSurname != "" && userLogin != "" &&
            userPassword != "" && userEmail != "") {

            document.getElementById(formName).submit();
        }
    }

</script>

</body>
</html>
