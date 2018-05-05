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
            <th width="30%">${titleHeader}</th>
            <th width="30%">${authorHeader}</th>
            <th width="10%">${genreHeader}</th>
            <th width="5%">${yearHeader}</th>
            <th width="5%">Price</th>
            <th width="10%"></th>
            <c:if test="${user_role == 'user'}">
                <th width="10%"></th>
            </c:if>
        </tr>

        <c:forEach var="album" items="${albumList}">
            <c:if test="${album.status == 'active'}">
                <tr id="${album.id}" style="text-align: center">
                    <td id="${album.id}name">${album.name}</td>
                    <td id="${album.id}author">${album.author}</td>
                    <td id="${album.id}genre">${album.genre}</td>
                    <td id="${album.id}year">${album.year}</td>
                    <td id="${album.id}price">${album.tracksCount}</td>
                    <td id="${album.id}" style="background-color: #cffffc" onclick="showContent('contentForm', this.id)">Content</td>

                    <c:if test="${album.ownerId == user.id && album.ownerId != null}">
                        <td id="${album.id}" style="background-color: #4CAF50; text-align: center"
                            onclick="downloadEntity('downloadForm', this.id)">Скачать
                        </td>
                    </c:if>

                    <c:if test="${user_role == 'user' && album.ownerId != user.id}">
                        <td id="${album.id}" style="background-color: #4CAF50"
                            onclick="buyEntity('buyForm', this.id)">Купить
                        </td>
                    </c:if>

                </tr>
            </c:if>
        </c:forEach>

    </table>
</div>

<form id="contentForm" action="/mainServlet" method="get">
    <input type="hidden" name="command" value="show_album_content">
    <input type="hidden" id="content_album_id" name="album_id" value="">
</form>

<form id="buyForm" action="/mainServlet" method="get">
    <input type="hidden" name="command" value="buy_album">
    <input type="hidden" id="album_id" name="album_id" value="">
    <input type="hidden" id="album_price" name="album_price" value="">
</form>

<script>

    function showContent(formId, id) {
        document.getElementById("content_album_id").value = id;

        document.getElementById(formId).submit();
    }

    function buyEntity(formId, id) {
        document.getElementById("album_id").value = id;
        document.getElementById("album_price").value = document.getElementById(id + "price").textContent;

        document.getElementById(formId).submit();
    }

    function downloadEntity(formId, id) {
        document.getElementById("content_album_id").value = id;

        document.getElementById(formId).submit();
    }

</script>

</body>
</html>
