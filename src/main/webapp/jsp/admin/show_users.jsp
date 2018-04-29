<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>

<html>
<head>
    <link href="<c:url value="/css/main.css" />" rel="stylesheet">
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>users list</title>

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
            <h2>Список пользователей</h2>
        </td>
    </tr>
</table>

<table id="fancyTable" style="width: 80%; margin-left: auto; margin-right: auto;">
    <tr>
        <th style="width: 25%; alignment: center">${name}</th>
        <th style="width: 25%; alignment: center">${surname}</th>
        <th style="width: 15%; alignment: center">${login}</th>
        <th style="width: 15%; alignment: center">e-mail</th>
        <th style="width: 10%; alignment: center">role</th>
        <th style="width: 10%; alignment: center">status</th>
    </tr>
    <c:forEach var="currentUser" items="${allUsers}">
        <c:if test="${currentUser.status == 'active'}">
            <tr id="${currentUser.id}" onclick="formSubmitInfo(this.id)">
                <td id="${currentUser.id}name">${currentUser.name}</td>
                <td id="${currentUser.id}surname">${currentUser.surname}</td>
                <td id="${currentUser.id}login">${currentUser.login}</td>
                <td id="${currentUser.id}email">${currentUser.email}</td>
                <td id="${currentUser.id}email">${currentUser.role}</td>
                <td id="${currentUser.id}email">${currentUser.status}</td>
            </tr>
        </c:if>
    </c:forEach>
</table>
<table style="width: 80%; margin-left: auto; margin-right: auto;">
    <tr>
        <td style="width: 100%"></td>
        <td>
            <form id="formEdit" action="/mainServlet" method="get">
                <input type="hidden" name="command" value="show_edit_user_page">
                <input type="hidden" name="submit_user_id" id="submit_user_id" value="">
                <input type="button" name="button" onclick="editUser('formEdit')"
                       value="${editButton}" style="width: 200px">
            </form>
        </td>
    </tr>
</table>

<script>

    $("tr").click(function () {
        $(this).addClass("selected").siblings().removeClass("selected");
    });

    function formSubmitInfo(userId) {
        document.getElementById("submit_user_id").setAttribute("value", userId);
    }

    function editUser(formName) {
        var userId = document.getElementById("submit_user_id").value;

        if (userId != "") {
            document.getElementById(formName).submit();
        }
    }

</script>

</body>
</html>
