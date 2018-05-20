<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>

<html>
<head>
    <link href="<c:url value="/css/main.css" />" rel="stylesheet">
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>edit albums page</title>

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
            <h2>Список альбомов</h2>
        </td>
    </tr>
</table>

<table id="fancyTable" style="width: 80%; margin-left: auto; margin-right: auto;">
    <tr>
        <th width="35%">${titleHeader}</th>
        <th width="35%">${authorHeader}</th>
        <th width="10%">${genreHeader}</th>
        <th width="10%">${yearHeader}</th>
    </tr>

    <c:forEach var="currentAlbum" items="${albumList}">
        <c:if test="${currentAlbum.status == 'active'}">
            <tr id="${currentAlbum.id}" onclick="formSubmitInfo(this.id)">
                <td id="${currentAlbum.id}name">${currentAlbum.name}</td>
                <td id="${currentAlbum.id}author">${currentAlbum.author}</td>
                <td id="${currentAlbum.id}genre">${currentAlbum.genre}</td>
                <td id="${currentAlbum.id}year">${currentAlbum.year}</td>
            </tr>
        </c:if>
    </c:forEach>

</table>

<br>

<table style="width: 80%; margin-left: auto; margin-right: auto;">
    <tr>
        <th width="35%">${titleHeader}</th>
        <th width="35%">${authorHeader}</th>
        <th width="10%">${genreHeader}</th>
        <th width="10%">${yearHeader}</th>
    </tr>
    <tr>
        <td><input id="name" name="name" type="text" value=""></td>
        <td>
            <select id="authorSelector" name="authorSelector" size="1">
                <option value=""></option>
                <c:forEach var="line" items="${authorList}">
                    <c:if test="${line.status=='active'}">
                        <option value="${line.name}">${line.name}</option>
                    </c:if>
                </c:forEach>
            </select>
        </td>
        <td>
            <select id="genreSelector" name="genreSelector" size="1">
                <option value=""></option>
                <c:forEach var="line" items="${genreList}">
                    <c:if test="${line.status=='active'}">
                        <option value="${line.name}">${line.name}</option>
                    </c:if>
                </c:forEach>
            </select>
        </td>
        <td><input id="year" name="year" type="text" value=""></td>
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
    </tr>
</table>

<table style="width: 80%; margin-left: auto; margin-right: auto;">
    <tr>
        <td style="width: 100%"></td>
        <form id="formContent" action="${absolutePath}/mainServlet" method="get">
            <input type="hidden" name="command" value="show_edit_albums_content_page">
            <input type="hidden" name="submit_id" value="">
            <td><input id="contentButton" type="submit" name="button" value="${editContentButton}"
                       style="display: none; width: 200px"></td>
        </form>

        <td><input type="button" name="button" onclick="submitButton('formCreate')"
                   value="${createButton}" style="width: 200px"></td>
        <td><input type="button" name="button" onclick="submitButton('formUpdate')"
                   value="${updateButton}" style="width: 200px"></td>
        <td><input type="button" name="button" onclick="submitButton('formDelete')"
                   value="${deleteButton}" style="width: 200px"></td>
    </tr>
</table>

<form id="CUDform" action="${absolutePath}/mainServlet" method="get">
    <input type="hidden" id="command" name="command" value="">
    <input type="hidden" id="submit_id" name="submit_id" value="">
    <input type="hidden" id="submit_name" name="submit_name" value="">
    <input type="hidden" id="submit_author" name="submit_author" value="">
    <input type="hidden" id="submit_genre" name="submit_genre" value="">
    <input type="hidden" id="submit_year" name="submit_year" value="">
    <input type="hidden" id="submit_status" name="submit_status" value="active">
</form>

<script>

    $("tr").click(function () {
        $(this).addClass("selected").siblings().removeClass("selected");
    });

    function formSubmitInfo(albumId) {
        document.getElementById("name").value = document.getElementById(albumId + "name").textContent;
        document.getElementById("authorSelector").value = document.getElementById(albumId + "author").textContent;
        document.getElementById("genreSelector").value = document.getElementById(albumId + "genre").textContent;
        document.getElementById("year").value = document.getElementById(albumId + "year").textContent;
        document.getElementById("contentButton").style.display = "block";

        var countId = document.getElementsByName("submit_id");
        for (var i = 0; i < countId.length; i++) {
            countId[i].setAttribute("value", albumId);
        }
    }

    function submitButton(formName) {
        var albumName = document.getElementById("name").value;
        var albumAuthor = document.getElementById("authorSelector").value;
        var albumGenre = document.getElementById("genreSelector").value;
        var albumYear = document.getElementById("year").value;

        document.getElementById("submit_name").value = albumName;
        document.getElementById("submit_author").value = albumAuthor;
        document.getElementById("submit_genre").value = albumGenre;
        document.getElementById("submit_year").value = albumYear;

        if (formName == "formCreate") {
            document.getElementById("command").value = "create_album"
        }

        if (formName == "formUpdate") {
            document.getElementById("command").value = "update_album"
        }

        if (formName == "formDelete") {
            document.getElementById("command").value = "delete_album"
        }

        if (albumName != "" && albumAuthor != "" && albumGenre != "" && albumYear != "") {
            document.getElementById("CUDform").submit();
        }
    }

</script>

</body>
</html>