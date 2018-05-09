<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>

<html>
<head>
    <link href="<c:url value="/css/main.css" />" rel="stylesheet">
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>album content</title>

    <script src="/js/jquery.min.js"></script>

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

<br>


<table width="100%">
    <tr>
        <td style="text-align: center">
            <h2>Альбом</h2>
        </td>
    </tr>
</table>

<table id="fancyTable" style="width: 80%; margin-left: auto; margin-right: auto;">
    <tr>
        <th style="width: 35%; alignment: center">${currentAlbum.name}</th>
        <th style="width: 25%; alignment: center">${currentAlbum.author}</th>
        <th style="width: 10%; alignment: center">${currentAlbum.genre}</th>
        <th style="width: 10%; alignment: center">${currentAlbum.year}</th>
    </tr>
</table>

<table width="100%">
    <tr>
        <td style="text-align: center">
            <h2>Содержимое альбома</h2>
        </td>
    </tr>
</table>


<table id="fancyTable" style="width: 90%; margin-left: auto; margin-right: auto;">
    <tr>
        <th width="35%">${titleHeader}</th>
        <th width="30%">${authorHeader}</th>
        <th width="10%">${genreHeader}</th>
        <th width="5%">${yearHeader}</th>
        <th width="5%">${lengthHeader}</th>
        <th width="5%">${priceHeader}</th>
        <c:if test="${user_role == 'user'}">
            <th width="10%"></th>
        </c:if>
    </tr>

    <c:forEach var="track" items="${contentList}">
        <c:if test="${track.status == 'active'}">
            <tr id="${track.id}">
                <td id="${track.id}name">${track.name}</td>
                <td id="${track.id}author">${track.author}</td>
                <td id="${track.id}genre">${track.genre}</td>
                <td id="${track.id}year">${track.year}</td>
                <td id="${track.id}length">${track.length}</td>
                <td id="${track.id}price">1</td>

                <c:if test="${track.ownerId == user.id && track.ownerId != null}">
                    <td id="${track.id}" style="background-color: #7df9ef; text-align: center"
                        onclick="downloadEntity('downloadForm', this.id)">${downloadButton}
                    </td>
                </c:if>

                <c:if test="${user_role == 'user' && track.ownerId != user.id}">
                    <td id="${track.id}" style="background-color: #dbffff; text-align: center">

                    </td>
                </c:if>
            </tr>
        </c:if>
    </c:forEach>

</table>


<form id="buyForm" action="${absolutePath}/mainServlet" method="get">
    <input type="hidden" name="command" value="buy_track">
    <input type="hidden" id="track_id" name="track_id" value="">
    <input type="hidden" id="track_price" name="track_price" value="">
</form>

<script>

    $("tr").click(function () {
        $(this).addClass("selected").siblings().removeClass("selected");
    });

    function buyEntity(formId, id) {
        document.getElementById("track_id").value = id;
        document.getElementById("track_price").value = document.getElementById(id + "price").textContent;

        document.getElementById(formId).submit();
    }

    function downloadEntity(formId, id) {
        alert(formId + ", " + id);
    }

</script>

</body>
</html>
