<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="/WEB-INF/paging.tld" prefix="pg" %>
<!DOCTYPE html>

<html>
<head>
    <link href="<c:url value="/css/main.css" />" rel="stylesheet">
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>tracks</title>

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
            <h2>Список треков</h2>
        </td>
    </tr>
</table>

<c:if test="${currentPage == null || currentPage == ''}">
    <c:set var="currentPage" value="1"/>
</c:if>

<pg:getFirstPageNumber data="${trackList}" variable="firstPage"/>
<pg:getLastPageNumber data="${trackList}" variable="lastPage"/>
<pg:getSelectPageNumbers data="${trackList}" page="${currentPage}" first="first" last="last"/>
<pg:getSelectedRows data="${trackList}" rows="selectedRows" page="${currentPage}"/>

<table style="width: 90%; margin-left: auto; margin-right: auto; font-size: 12px">
    <tr style="text-align: center">
        <td width="35%">
            ${titleHeader}
        </td>
        <td width="30%">
            ${authorHeader}
        </td>
        <td width="10%">
            ${genreHeader}
        </td>
        <td width="5%">
            ${yearHeader}
        </td>
        <td width="10%">
        </td>
    </tr>
    <tr>
        <form id="searchForm" action="${absolutePath}/mainServlet" method="post">
            <input type="hidden" id="command" name="command" value="search_tracks">
            <input type="hidden" name="currentURI" value="${pageContext.request.requestURI}">

            <td width="35%">
                <input type="text" id="search_name" name="search_name" value="">
            </td>
            <td width="30%">
                <input type="text" id="search_author" name="search_author" value="">
            </td>
            <td width="10%">
                <input type="text" id="search_genre" name="search_genre" value="">
            </td>
            <td width="5%">
                <input type="text" id="search_year" name="search_year" value="">
            </td>
            <td width="10%">
                <input type="button" id="search_button" name="search" value="Search" onclick="searchButton()">
            </td>
        </form>
    </tr>

</table>

<table id="fancyTable" style="width: 90%; margin-left: auto; margin-right: auto; font-size: 12px">
    <tr>
        <th width="30%">${titleHeader}</th>
        <th width="25%">${authorHeader}</th>
        <th width="10%">${genreHeader}</th>
        <th width="5%">${yearHeader}</th>
        <th width="5%">${lengthHeader}</th>
        <th width="5%">${priceHeader}</th>
        <c:if test="${user_role == 'user'}">
            <th width="10%"></th>
        </c:if>
    </tr>
    <c:forEach var="track" items="${selectedRows}">
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
                    <td id="${track.id}" style="background-color: #4CAF50; text-align: center"
                        onclick="buyEntity('buyForm', this.id)">${buyButton}
                    </td>
                </c:if>
            </tr>
        </c:if>
    </c:forEach>
</table>

<%@include file="/WEB-INF/jspf/paging_menu.jsp" %>

<form id="downloadForm" action="${absolutePath}/mainServlet" method="get">

</form>

<form id="buyForm" action="${absolutePath}/mainServlet" method="post">
    <input type="hidden" name="command" value="buy_track">
    <input type="hidden" id="track_id" name="track_id" value="">
    <input type="hidden" id="track_price" name="track_price" value="">
</form>

<script>

    function buyEntity(formId, id) {
        document.getElementById("track_id").value = id;
        document.getElementById("track_price").value = document.getElementById(id + "price").textContent;

        document.getElementById(formId).submit();
    }

    function downloadEntity(formId, id) {
        alert(formId + ", " + id);
    }

    function searchButton() {
        document.getElementById("searchForm").submit();
    }

</script>

</body>
</html>
