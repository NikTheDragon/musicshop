<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>

<html>
<head>
    <link href="<c:url value="/css/main.css" />" rel="stylesheet">
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>edit mixes content page</title>

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

<table id="fancyTable" style="width: 80%; margin-left: auto; margin-right: auto;">
    <tr>
        <th style="width: 60%; alignment: center">${currentMix.name}</th>
        <th style="width: 10%; alignment: center">${currentMix.genre}</th>
        <th style="width: 10%; alignment: center">${currentMix.year}</th>
    </tr>
</table>

<br>

<table id="fancyTable" style="width: 80%; margin-left: auto; margin-right: auto;">
    <tr>
        <th width="45%">${nameHeader}</th>
        <th width="45%">${authorHeader}</th>
        <th width="10%">${genreHeader}</th>
    </tr>
    <c:forEach var="content" items="${contentList}">
        <c:if test="${content.status != 'active'}">
            <tr>
                <td>${content.trackName}</td>
                <td>${content.authorName}</td>
                <td>${currentMix.genre}</td>
            </tr>
        </c:if>
    </c:forEach>
</table>

<table style="width: 80%; margin-left: auto; margin-right: auto;">
    <tr>
        <td width="45%">
            <select id="track" name="track" size="1">
                <option style="background-color:lightskyblue" value=""></option>
                <c:forEach var="line" items="${namesSet}">
                    <option value="${line}">${line}</option>
                </c:forEach>
            </select>
        </td>
        <td width="45%">
            <select id="author" name="author" size="1" onchange="sendParams('submitAuthor')">
                <option style="background-color:lightskyblue" value="${currentAuthor}">${currentAuthor}</option>
                <c:forEach var="line" items="${authorsSet}">
                    <option value="${line}">${line}</option>
                </c:forEach>
            </select>
        </td>
        <td width="10%">
            <select id="genre" name="genre" size="1" onchange="sendParams('submitGenre')">
                <c:if test="${currentGenre!=null}">
                    <option style="background-color:lightskyblue" value="${currentGenre}">${currentGenre}</option>
                </c:if>
                <option value="*">*</option>
                <c:forEach var="line" items="${genresSet}">
                    <option value="${line}">${line}</option>
                </c:forEach>
            </select>
        </td>
    </tr>
</table>

<table style="width: 80%; margin-left: auto; margin-right: auto;">
    <tr>
        <td style="width: 100%"></td>
        <form id="formAdd" action="/mainServlet" method="get">
            <input type="hidden" name="command" value="add_track_to_mix">
            <input type="hidden" name="submit_mix_id" value="${currentMix.id}">
            <input type="hidden" name="submit_track" value="">
            <input type="hidden" name="submit_author" value="">
            <input type="hidden" name="submit_genre" value="">
            <td><input type="button" name="button" onclick="submitButton('formAdd')"
                       value="${createButton}" style="width: 200px"></td>
        </form>
        <form id="formDelete" action="/mainServlet" method="get">
            <input type="hidden" name="command" value="delete_track_from_mix">
            <input type="hidden" name="submit_mix_id" value="${currentMix.id}">
            <input type="hidden" name="submit_track" value="">
            <input type="hidden" name="submit_author" value="">
            <input type="hidden" name="submit_genre" value="">
            <td><input type="button" name="button" onclick="submitButton('formDelete')"
                       value="${deleteButton}" style="width: 200px"></td>
        </form>
        <form id="submitAuthor" action="/mainServlet" method="get">
            <input type="hidden" name="command" value="submit_track">
            <input type="hidden" name="submit_author" value="">
            <input type="hidden" name="submit_genre" value="">
        </form>
        <form id="submitGenre" action="/mainServlet" method="get">
            <input type="hidden" name="command" value="submit_track">
            <input type="hidden" name="submit_genre" value="">
        </form>
    </tr>
</table>

<script>
    function sendParams(formName) {
        var trackAuthor = document.getElementById("author");
        var trackGenre = document.getElementById("genre");

        var countAuthor = document.getElementsByName("submit_author");

        for (var i = 0; i < countAuthor.length; i++) {
            countAuthor[i].setAttribute("value", trackAuthor.value);
        }

        var countGenre = document.getElementsByName("submit_genre");

        for (var i = 0; i < countGenre.length; i++) {
            countGenre[i].setAttribute("value", trackGenre.value);
        }

        document.getElementById(formName).submit();
    }

    function submitButton(formName) {
        var trackAuthor = document.getElementById("author");
        var trackTrack = document.getElementById("track");

        var countAuthor = document.getElementsByName("submit_author");

        for (var i = 0; i < countAuthor.length; i++) {
            countAuthor[i].setAttribute("value", trackAuthor.value);
        }

        var countTrack = document.getElementsByName("submit_track");

        for (var i = 0; i < countTrack.length; i++) {
            countTrack[i].setAttribute("value", trackTrack.value);
        }

        if(trackAuthor.value != "" && trackTrack.value != "") {
            document.getElementById(formName).submit();
        }
    }

</script>

</body>
</html>
