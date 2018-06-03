<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="/WEB-INF/paging.tld" prefix="pg" %>
<!DOCTYPE html>

<html>
<head>
    <link href="<c:url value="/css/main.css" />" rel="stylesheet">
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>edit tracks page</title>

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

<c:if test="${currentPageNumber == null || currentPageNumber == ''}">
    <c:set var="currentPageNumber" value="1"/>
</c:if>

<pg:getFirstPageNumber data="${trackList}" variable="firstPage"/>
<pg:getLastPageNumber data="${trackList}" variable="lastPage"/>
<pg:getSelectPageNumbers data="${trackList}" page="${currentPageNumber}" first="first" last="last"/>
<pg:getSelectedRows data="${trackList}" rows="selectedRows" page="${currentPageNumber}"/>

<table width="100%">
    <tr>
        <td style="text-align: center">
            <h2>Список треков</h2>
        </td>
    </tr>
</table>

<table style="width: 90%; margin-left: auto; margin-right: auto; font-size: 12px">
    <tr>
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
    <form id="searchForm" action="${absolutePath}/mainServlet" method="post">
        <input type="hidden" name="command" value="search_tracks">
        <input type="hidden" name="currentURI" value="${pageContext.request.requestURI}">

        <td width="35%">
            <input type="text" id="search_name" name="search_name" value="">
        </td>
        <td width="35%">
            <input type="text" id="search_author" name="search_author" value="">
        </td>
        <td width="10%">
            <input type="text" id="search_genre" name="search_genre" value="">
        </td>
        <td width="10%">
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
        <th width="35%">${titleHeader}</th>
        <th width="35%">${authorHeader}</th>
        <th width="10%">${genreHeader}</th>
        <th width="10%">${yearHeader}</th>
        <th width="10%">${lengthHeader}</th>
    </tr>

    <c:forEach var="track" items="${selectedRows}">
        <c:if test="${track.status == 'active'}">
            <tr id="${track.id}" onclick="formSubmitInfo(this.id)">
                <td id="${track.id}name">${track.name}</td>
                <td id="${track.id}author">${track.author}</td>
                <td id="${track.id}genre">${track.genre}</td>
                <td id="${track.id}year">${track.year}</td>
                <td id="${track.id}length">${track.length}</td>
            </tr>
        </c:if>
    </c:forEach>

</table>

<%@include file="/WEB-INF/jspf/paging_menu.jsp" %>

<br>

<table style="width: 90%; margin-left: auto; margin-right: auto;">
    <tr>
        <th width="35%">${titleHeader}</th>
        <th width="35%">${authorHeader}</th>
        <th width="10%">${genreHeader}</th>
        <th width="10%">${yearHeader}</th>
        <th width="10%">${lengthHeader}</th>
    </tr>
    <tr>
        <td><input id="name" name="name" type="text" value=""></td>
        <td>
            <select id="author" name="author" size="1">
                <option value=""></option>
                <c:forEach var="line" items="${authorList}">
                    <c:if test="${line.status=='active'}">
                        <option value="${line.name}">${line.name}</option>
                    </c:if>
                </c:forEach>
            </select>
        </td>
        <td>
            <select id="genre" name="genre" size="1">
                <option value=""></option>
                <c:forEach var="line" items="${genreList}">
                    <c:if test="${line.status=='active'}">
                        <option value="${line.name}">${line.name}</option>
                    </c:if>
                </c:forEach>
            </select>
        </td>
        <td><input id="year" name="year" type="text" value=""></td>
        <td><input id="length" name="length" type="text" value=""></td>
    </tr>
    <tr style="text-align: center">
        <td>
            <c:set var="message" value="${messages['nameResult']}"/>
            <%@include file="/WEB-INF/jspf/error_handler.jsp" %>
        </td>
        <td>
            <c:set var="message" value="${messages['authorResult']}"/>
            <%@include file="/WEB-INF/jspf/error_handler.jsp" %>
        </td>
        <td>
            <c:set var="message" value="${messages['genreResult']}"/>
            <%@include file="/WEB-INF/jspf/error_handler.jsp" %>
        </td>
        <td>
            <c:set var="message" value="${messages['yearResult']}"/>
            <%@include file="/WEB-INF/jspf/error_handler.jsp" %>
        </td>
        <td>
            <c:set var="message" value="${messages['lengthResult']}"/>
            <%@include file="/WEB-INF/jspf/error_handler.jsp" %>
        </td>

    </tr>
</table>

<table style="width: 90%; margin-left: auto; margin-right: auto; text-align: right">
    <tr>
        <td>
            <input type="button" name="button" onclick="submitButton('formCreate')"
                   value="${createButton}" style="width: 200px">
            <input type="button" name="button" onclick="submitButton('formUpdate')"
                   value="${updateButton}" style="width: 200px">
            <input type="button" name="button" onclick="submitButton('formDelete')"
                   value="${deleteButton}" style="width: 200px">
        </td>
    </tr>
</table>

<form id="CUDform" action="${absolutePath}/mainServlet" method="get">
    <input type="hidden" id="command" name="command" value="">
    <input type="hidden" id="submit_id" name="submit_id" value="">
    <input type="hidden" id="submit_name" name="submit_name" value="">
    <input type="hidden" id="submit_author" name="submit_author" value="">
    <input type="hidden" id="submit_genre" name="submit_genre" value="">
    <input type="hidden" id="submit_year" name="submit_year" value="">
    <input type="hidden" id="submit_length" name="submit_length" value="">
    <input type="hidden" id="submit_status" name="submit_status" value="active">
</form>

<script>

    $("tr").click(function () {
        $(this).addClass("selected").siblings().removeClass("selected");
    });

    function formSubmitInfo(trackId) {
        document.getElementById("name").value = document.getElementById(trackId + "name").textContent;
        document.getElementById("author").value = document.getElementById(trackId + "author").textContent;
        document.getElementById("genre").value = document.getElementById(trackId + "genre").textContent;
        document.getElementById("year").value = document.getElementById(trackId + "year").textContent;
        document.getElementById("length").value = document.getElementById(trackId + "length").textContent;
        document.getElementById("submit_id").value = trackId;
    }

    function submitButton(formName) {
        var trackName = document.getElementById("name").value;
        var trackAuthor = document.getElementById("author").value;
        var trackGenre = document.getElementById("genre").value;
        var trackYear = document.getElementById("year").value;
        var trackLength = document.getElementById("length").value;

        document.getElementById("submit_name").value = trackName;
        document.getElementById("submit_author").value = trackAuthor;
        document.getElementById("submit_genre").value = trackGenre;
        document.getElementById("submit_year").value = trackYear;
        document.getElementById("submit_length").value = trackLength;

        if (formName == "formCreate") {
            document.getElementById("command").value = "create_track"
        }

        if (formName == "formUpdate") {
            document.getElementById("command").value = "update_track"
        }

        if (formName == "formDelete") {
            document.getElementById("command").value = "delete_track"
        }

        if (trackName != "" && trackAuthor != "" && trackGenre != "" && trackYear != "") {
            document.getElementById("CUDform").submit();
        }
    }

    function searchButton() {
        document.getElementById("searchForm").submit();
    }

</script>

</body>
</html>