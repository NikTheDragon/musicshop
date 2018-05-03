<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>

<html>
<head>
    <link href="<c:url value="/css/main.css" />" rel="stylesheet">
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>albums</title>

    <%@include file="/WEB-INF/jspf/locale.jsp" %>
</head>
<body style="font-family: Arial, Helvetica, sans-serif">

<%@include file="/WEB-INF/jspf/header.jsp" %>

<c:set var="user_role" scope="session" value="${user.role}"/>
<c:choose>
    <c:when test="${user_role == 'user'}">
        <%@include file="/WEB-INF/jspf/user_menu.jsp" %>
    </c:when>
    <c:when test="${user_role == 'admin'}">
        <%@include file="/WEB-INF/jspf/admin_menu.jsp" %>
    </c:when>
    <c:otherwise>
        <%@include file="/WEB-INF/jspf/menu.jsp" %>
    </c:otherwise>
</c:choose>

<table width="100%">
    <tr>
        <td style="text-align: center">
            <h2>Список альбомов</h2>
        </td>
    </tr>
</table>

<br>

<div style="width:100%; height:400px; overflow:auto;">
    <table id="fancyTable" style="width: 90%; margin-left: auto; margin-right: auto;">
        <tr>
            <th width="35%">${titleHeader}</th>
            <th width="35%">${authorHeader}</th>
            <th width="10%">${genreHeader}</th>
            <th width="5%">${yearHeader}</th>
            <c:if test="${user_role == 'user'}">
                <th width="10%"></th>
            </c:if>
        </tr>

        <c:forEach var="album" items="${albumList}">
            <c:if test="${album.status == 'active'}">
                <tr id="${album.id}">
                    <td id="${album.id}name">${album.name}</td>
                    <td id="${album.id}author">${album.author}</td>
                    <td id="${album.id}genre">${album.genre}</td>
                    <td id="${album.id}year">${album.year}</td>
                    <c:if test="${user_role == 'user'}">
                        <td id="${album.id}" style="background-color: #4CAF50; text-align: center"
                            onclick="downloadEntity('downloadForm', this.id)">Скачать
                        </td>
                    </c:if>
                </tr>
            </c:if>
        </c:forEach>

    </table>
</div>

<form name="downloadForm">

</form>

<script>

    function downloadEntity(formId, id) {
        alert(formId + ", " + id);
    }

</script>

</body>
</html>
