<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="/WEB-INF/paging.tld" prefix="pg" %>
<!DOCTYPE html>

<html>
<head>
    <link href="<c:url value="/css/main.css" />" rel="stylesheet">
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>my albums</title>

    <%@include file="/WEB-INF/jspf/locale.jsp" %>
</head>
<body style="font-family: Arial, Helvetica, sans-serif">

<c:set var="user_role" scope="session" value="${user.role}"/>
<c:if test="${user_role != 'admin' && user_role != 'user'}">
    <c:redirect url="/mainServlet?command=show_main_page"/>
</c:if>

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

<c:if test="${currentPageNumber == null || currentPageNumber == ''}">
    <c:set var="currentPageNumber" value="1"/>
</c:if>

<pg:getFirstPageNumber data="${albumList}" variable="firstPage"/>
<pg:getLastPageNumber data="${albumList}" variable="lastPage"/>
<pg:getSelectPageNumbers data="${albumList}" page="${currentPageNumber}" first="first" last="last"/>
<pg:getSelectedRows data="${albumList}" rows="selectedRows" page="${currentPageNumber}"/>

<table width="100%">
    <tr>
        <td style="text-align: center">
            <h2>Список альбомов</h2>
        </td>
    </tr>
</table>

<br>

<table id="fancyTable" style="width: 90%; margin-left: auto; margin-right: auto;">
    <tr>
        <th width="30%">${titleHeader}</th>
        <th width="30%">${authorHeader}</th>
        <th width="10%">${genreHeader}</th>
        <th width="5%">${yearHeader}</th>
        <th width="10%"></th>
    </tr>

    <c:forEach var="album" items="${selectedRows}">
        <tr id="${album.id}" style="text-align: center">
            <td id="${album.id}name">${album.name}</td>
            <td id="${album.id}author">${album.author}</td>
            <td id="${album.id}genre">${album.genre}</td>
            <td id="${album.id}year">${album.year}</td>
            <td id="${album.id}" style="background-color: #cffffc"
                onclick="showContent('contentForm', this.id)">${contentButton}</td>
        </tr>
    </c:forEach>

</table>

<%@include file="/WEB-INF/jspf/paging_menu.jsp" %>

<form id="contentForm" action="${absolutePath}/mainServlet" method="get">
    <input type="hidden" name="command" value="show_album_content">
    <input type="hidden" id="content_album_id" name="album_id" value="">
</form>

<script>

    function showContent(formId, id) {
        document.getElementById("content_album_id").value = id;

        document.getElementById(formId).submit();
    }

</script>

</body>
</html>
