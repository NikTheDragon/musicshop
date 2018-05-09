<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>

<html>
<head>
    <link href="<c:url value="/css/main.css" />" rel="stylesheet">
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>edit albums content page</title>

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

<table style="width: 80%; margin-left: auto; margin-right: auto;">
    <tr>
        <td style="text-align: center">
            <h2>Содержимое альбома</h2>
        </td>
    </tr>
</table>

<table id="fancyTable" style="width: 80%; margin-left: auto; margin-right: auto;">
    <tr>
        <th width="70%">${titleHeader}</th>
        <th width="30%">${lengthHeader}</th>
    </tr>
    <c:forEach var="currentContent" items="${albumContent}">
        <c:if test="${currentContent.status == 'active'}">
            <tr id="${currentContent.trackId}" onclick="formSubmitInfo(this.id)">
                <td id="${currentContent.trackId}track">${currentContent.trackName}</td>
                <td style="text-align: center">${currentContent.length}</td>
            </tr>
        </c:if>
    </c:forEach>
</table>

<table style="width: 80%; margin-left: auto; margin-right: auto;">
    <tr>
        <td width="100%">
            <select id="trackNameSelector" name="trackNameSelector" size="1">
                <option style="background-color:lightskyblue" value=""></option>
                <c:forEach var="line" items="${authorTracks}">
                    <option value="${line.name}">${line.name}</option>
                </c:forEach>
            </select>
        </td>
        <form id="formAdd" action="${absolutePath}/mainServlet" method="get">
            <input type="hidden" name="command" value="add_track_to_album">
            <input type="hidden" name="submit_album_id" value="${currentAlbum.id}">
            <input type="hidden" name="submit_track" value="">
            <input type="hidden" name="submit_author" value="${currentAlbum.author}">
            <input type="hidden" name="submit_genre" value="${currentAlbum.genre}">
            <td><input type="button" name="button" onclick="submitButton('formAdd')"
                       value="${addButton}" style="width: 200px"></td>
        </form>
        <form id="formDelete" action="${absolutePath}/mainServlet" method="get">
            <input type="hidden" name="command" value="delete_track_from_album">
            <input type="hidden" name="submit_album_id" value="${currentAlbum.id}">
            <input type="hidden" name="submit_track_id" value="">
            <input type="hidden" name="submit_track" value="">
            <input type="hidden" name="submit_author" value="${currentAlbum.author}">
            <input type="hidden" name="submit_genre" value="${currentAlbum.genre}">
            <td><input type="button" name="button" onclick="submitButton('formDelete')"
                       value="${deleteButton}" style="width: 200px"></td>
        </form>
    </tr>
</table>

<script>

    $("tr").click(function(){
        $(this).addClass("selected").siblings().removeClass("selected");
    });

    function formSubmitInfo(trackId) {
        document.getElementById("trackNameSelector").value = document.getElementById(trackId + "track").textContent;

        var submitId = document.getElementsByName("submit_track_id");
        for (var i = 0; i < submitId.length; i++) {
            submitId[i].setAttribute("value", trackId);
        }
    }

    function submitButton(formName) {
        var trackName = document.getElementById("trackNameSelector").value;

        var submitName = document.getElementsByName("submit_track");
        for (var i = 0; i < submitName.length; i++) {
            submitName[i].setAttribute("value", trackName);
        }

        if (trackName != "") {
            document.getElementById(formName).submit();
        }
    }

</script>

</body>
</html>
