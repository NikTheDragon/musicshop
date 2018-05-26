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
<c:if test="${user_role != 'admin' && user_role != 'user'}">
    <c:redirect url="/mainServlet?command=show_main_page"/>
</c:if>

<body style="font-family: Arial, Helvetica, sans-serif">

<%@include file="/WEB-INF/jspf/header.jsp" %>

<c:if test="${user_role == 'admin'}">
    <%@include file="/WEB-INF/jspf/admin_menu.jsp" %>
</c:if>
<c:if test="${user_role == 'user'}">
    <%@include file="/WEB-INF/jspf/user_menu.jsp" %>
</c:if>

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
        <td style="width: 20%">${name}</td>
        <td style="width: 20%">${surname}</td>
        <td style="width: 15%">${login}</td>
        <td style="width: 15%">email</td>
        <td style="width: 10%"></td>
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
            <input type="text" id="userEmail" name="submit_email" value="${user.email}">
        </td>
        <td>
            <input type="button" value="${updateButton}" onclick="submitButton('updateUser')" style="width: 200px">
        </td>
    </tr>
</table>

<table width="100%">
    <tr>
        <td style="text-align: center">
            <h2>Пароль</h2>
        </td>
    </tr>
</table>

<table style="width: 50%; margin-left: auto; margin-right: auto;">
    <tr style="text-align: center">
        <td style="width: 20%">Старый</td>
        <td style="width: 20%">Новый</td>
        <td style="width: 10%"></td>
    </tr>
    <tr style="text-align: center">
        <td>
            <input type="text" id="oldPassword" name="submit_old_password" value="">
        </td>
        <td>
            <input type="text" id="newPassword" name="submit_new_password" value="">
        </td>
        <td>
            <input type="button" value="${updateButton}" onclick="submitPassword('updatePassword')"
                   style="width: 200px">
        </td>
    </tr>
    <tr style="text-align: center">
        <td>
            <c:set var="message" value="${oldPasswordResult}"/>
            <%@include file="/WEB-INF/jspf/error_handler.jsp" %>
        </td>
        <td>
            <c:set var="message" value="${newPasswordResult}"/>
            <%@include file="/WEB-INF/jspf/error_handler.jsp" %>
        </td>
        <td>

        </td>
    </tr>
    <tr style="text-align: center">
        <td colspan="3">${commandResult}</td>
    </tr>
</table>

<form id="updateUser" action="${absolutePath}/mainServlet" method="post">
    <input type="hidden" name="command" value="update_personal_info">
    <input type="hidden" id="submit_id" name="submit_id" value="${user.id}">
    <input type="hidden" id="submit_name" name="submit_name" value="">
    <input type="hidden" id="submit_surname" name="submit_surname" value="">
    <input type="hidden" id="submit_login" name="submit_login" value="">
    <input type="hidden" id="submit_password" name="submit_password" value="nothing">
    <input type="hidden" id="submit_email" name="submit_email" value="">
    <input type="hidden" id="submit_role" name="submit_role" value="${user.role}">
    <input type="hidden" id="submit_status" name="submit_status" value="${user.status}">
    <input type="hidden" id="submit_points" name="submit_points" value="${user.points}">
</form>

<form id="updatePassword" action="${absolutePath}/mainServlet" method="post">
    <input type="hidden" name="command" value="update_password">
    <input type="hidden" name="submit_id" value="${user.id}">
    <input type="hidden" id="submit_old_password" name="submit_old_password" value="">
    <input type="hidden" id="submit_new_password" name="submit_new_password" value="">
</form>

<script>

    function submitButton(formName) {
        var userName = document.getElementById("userName").value;
        var userSurname = document.getElementById("userSurname").value;
        var userLogin = document.getElementById("userLogin").value;
        var userEmail = document.getElementById("userEmail").value;

        document.getElementById("submit_name").value = userName;
        document.getElementById("submit_surname").value = userSurname;
        document.getElementById("submit_login").value = userLogin;
        document.getElementById("submit_email").value = userEmail;

        if (userName != "" && userSurname != "" && userLogin != "" && userEmail != "") {

            document.getElementById(formName).submit();
        }
    }

    function submitPassword(formName) {
        var oldPassword = document.getElementById("oldPassword").value;
        var newPassword = document.getElementById("newPassword").value;

        document.getElementById("submit_old_password").value = oldPassword;
        document.getElementById("submit_new_password").value = newPassword;

        if (oldPassword != "" && newPassword != "") {

            document.getElementById(formName).submit();
        }
    }

</script>

</body>
</html>
