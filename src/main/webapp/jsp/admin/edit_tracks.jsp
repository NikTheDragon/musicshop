<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
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

<br>

<table width="100%">
    <tr>
        <td style="text-align: center">
            <h2>Список треков</h2>
        </td>
    </tr>
</table>

<div style="width:100%; height:400px; overflow:auto;">
    <table id="fancyTable" style="width: 90%; margin-left: auto; margin-right: auto;">
        <tr>
            <th width="35%">${titleHeader}</th>
            <th width="35%">${authorHeader}</th>
            <th width="10%">${genreHeader}</th>
            <th width="10%">${yearHeader}</th>
            <th width="10%">${lengthHeader}</th>
        </tr>

        <c:forEach var="track" items="${trackList}">
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
</div>

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
    <tr>
        <td></td>
        <td></td>
        <form id="formCreate" action="${absolutePath}/mainServlet" method="get">
            <input type="hidden" name="command" value="create_track">
            <input type="hidden" name="submit_id" value="">
            <input type="hidden" name="submit_name" value="">
            <input type="hidden" name="submit_author" value="">
            <input type="hidden" name="submit_genre" value="">
            <input type="hidden" name="submit_year" value="">
            <input type="hidden" name="submit_length" value="">
            <td><input type="button" name="button" onclick="submitButton('formCreate')"
                       value="${createButton}"></td>
        </form>
        <form id="formUpdate" action="${absolutePath}/mainServlet" method="get">
            <input type="hidden" name="command" value="update_track">
            <input type="hidden" name="submit_id" value="">
            <input type="hidden" name="submit_name" value="">
            <input type="hidden" name="submit_author" value="">
            <input type="hidden" name="submit_genre" value="">
            <input type="hidden" name="submit_year" value="">
            <input type="hidden" name="submit_length" value="">
            <td><input type="button" name="button" onclick="submitButton('formUpdate')"
                       value="${updateButton}"></td>
        </form>
        <form id="formDelete" action="${absolutePath}/mainServlet" method="get">
            <input type="hidden" name="command" value="delete_track">
            <input type="hidden" name="submit_id" value="">
            <input type="hidden" name="submit_name" value="">
            <input type="hidden" name="submit_author" value="">
            <input type="hidden" name="submit_genre" value="">
            <input type="hidden" name="submit_year" value="">
            <input type="hidden" name="submit_length" value="">
            <td><input type="button" name="button" onclick="submitButton('formDelete')"
                       value="${deleteButton}"></td>
        </form>
    </tr>
</table>

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

        var countId = document.getElementsByName("submit_id");
        for (var i = 0; i < countId.length; i++) {
            countId[i].setAttribute("value", trackId);
        }
    }

    function submitButton(formName) {
        var trackName = document.getElementById("name").value;
        var trackAuthor = document.getElementById("author").value;
        var trackGenre = document.getElementById("genre").value;
        var trackYear = document.getElementById("year").value;
        var trackLength = document.getElementById("length").value;

        var countName = document.getElementsByName("submit_name");
        for (var i = 0; i < countName.length; i++) {
            countName[i].setAttribute("value", trackName);
        }

        var countAuthor = document.getElementsByName("submit_author");
        for (var i = 0; i < countAuthor.length; i++) {
            countAuthor[i].setAttribute("value", trackAuthor);
        }

        var countGenre = document.getElementsByName("submit_genre");
        for (var i = 0; i < countGenre.length; i++) {
            countGenre[i].setAttribute("value", trackGenre);
        }

        var countYear = document.getElementsByName("submit_year");
        for (var i = 0; i < countYear.length; i++) {
            countYear[i].setAttribute("value", trackYear);
        }

        var countLength = document.getElementsByName("submit_length");
        for (var i = 0; i < countLength.length; i++) {
            countLength[i].setAttribute("value", trackLength);
        }

        if(trackName != "" && trackAuthor != "" && trackGenre != "" && trackYear != "") {
            document.getElementById(formName).submit();
        }
    }

</script>

</body>
</html>