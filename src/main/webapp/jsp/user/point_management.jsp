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
            <h2>Управление баллами</h2>
        </td>
    </tr>
</table>

<table style="width: 90%; margin-left: auto; margin-right: auto;">
    <tr style="text-align: center">
        <td>
            У Вас ${user.points} баллов. Баллы тратятся на покупку композиций, альбомов и сборок.<br>
            Выберите нужноe количество баллов и нажмите купить.
        </td>
    </tr>
</table>

<br>

<table id="fancyTable" style="width: 50%; margin-left: auto; margin-right: auto;">
    <tr>
        <th>Название</th>
        <th>Баллы</th>
        <th>Цена</th>
    </tr>
    <tr id="min" onclick="formSubmitInfo(this.id)">
        <td>Минимальный</td>
        <td>1</td>
        <td>1 руб.</td>
    </tr>
    <tr id="opt" onclick="formSubmitInfo(this.id)">
        <td>Оптимальный</td>
        <td>10</td>
        <td>7 руб.</td>
    </tr>
    <tr id="max" onclick="formSubmitInfo(this.id)">
        <td>Максимальный</td>
        <td>50</td>
        <td>30 руб.</td>
    </tr>
</table>

<table style="width: 50%; margin-left: auto; margin-right: auto;">
    <tr style="text-align: right">
        <form id="formAdd" action="${absolutePath}/mainServlet" method="post">
            <input type="hidden" name="command" value="add_points">
            <input type="hidden" id="tariffId" name="submit_tariff" value="">
            <td><input type="button" name="button" onclick="submitButton('formAdd')" value="${addButton}"></td>
        </form>
    </tr>
</table>

<script>

    $("tr").click(function () {
        $(this).addClass("selected").siblings().removeClass("selected");
    });

    function formSubmitInfo(tariffId) {
        document.getElementById("tariffId").value = tariffId;
    }

    function submitButton(formName) {
        var tariffId = document.getElementById("tariffId").value;

        if (tariffId != "") {
            document.getElementById(formName).submit();
        }
    }

</script>

</body>
</html>
