<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>

<html>
<head>
    <link href="<c:url value="/css/main.css" />" rel="stylesheet">
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>edit mixes content page</title>

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
            <h2>Сборка</h2>
        </td>
    </tr>
</table>

<table id="fancyTable" style="width: 80%; margin-left: auto; margin-right: auto;">
    <tr>
        <th style="width: 60%; alignment: center">${currentMix.name}</th>
        <th style="width: 10%; alignment: center">${currentMix.genre}</th>
        <th style="width: 10%; alignment: center">${currentMix.year}</th>
    </tr>
</table>

<table width="100%">
    <tr>
        <td style="text-align: center">
            <h2>Содержимое сборки</h2>
        </td>
    </tr>
</table>

<table id="fancyTable" style="width: 80%; margin-left: auto; margin-right: auto;">
    <tr>
        <th width="60%">${titleHeader}</th>
        <th width="40%">${authorHeader}</th>
    </tr>
    <c:forEach var="content" items="${contentList}">
        <c:if test="${content.status == 'active'}">
            <tr id="${content.trackId}" onclick="formSubmitInfo(this.id)">
                <td id="${content.trackId}track">${content.trackName}</td>
                <td id="${content.trackId}author">${content.authorName}</td>
            </tr>
        </c:if>
    </c:forEach>
</table>

<table style="width: 80%; margin-left: auto; margin-right: auto;">
    <tr>
        <td width="45%">
            <select id="trackSelector" name="trackSelector" size="1">
                <option style="background-color:lightskyblue" value=""></option>
                <c:forEach var="line" items="${namesSet}">
                    <option value="${line}">${line}</option>
                </c:forEach>
            </select>
        </td>
        <td width="45%">
            <select id="authorSelector" name="authorSelector" size="1" onchange="submitSelector('submitAuthor')">
                <option style="background-color:lightskyblue" value="${currentAuthor}">${currentAuthor}</option>
                <c:forEach var="line" items="${authorsSet}">
                    <option value="${line}">${line}</option>
                </c:forEach>
            </select>
        </td>
        <td width="10%">
            <select id="genreSelector" name="genreSelector" size="1" onchange="submitSelector('submitGenre')">
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
        <form id="formAdd" action="${absolutePath}/mainServlet" method="get">
            <input type="hidden" name="command" value="add_track_to_mix">
            <input type="hidden" name="submit_mix_id" value="${currentMix.id}">
            <input type="hidden" name="submit_track" value="">
            <input type="hidden" name="submit_author" value="">
            <input type="hidden" name="submit_genre" value="">
            <td><input type="button" name="button" onclick="submitButton('formAdd')"
                       value="${addButton}" style="width: 200px"></td>
        </form>
        <form id="formDelete" action="${absolutePath}/mainServlet" method="get">
            <input type="hidden" name="command" value="delete_track_from_mix">
            <input type="hidden" name="submit_mix_id" value="${currentMix.id}">
            <input type="hidden" id="id_for_delete" name="submit_track_id" value="">
            <input type="hidden" name="submit_track" value="">
            <input type="hidden" name="submit_author" value="">
            <input type="hidden" name="submit_genre" value="">
            <td><input type="button" name="button" onclick="submitDelete('formDelete')"
                       value="${deleteButton}" style="width: 200px"></td>
        </form>
        <form id="submitAuthor" action="${absolutePath}/mainServlet" method="get">
            <input type="hidden" name="command" value="form_mix_content_input_data">
            <input type="hidden" name="submit_author" value="">
            <input type="hidden" name="submit_genre" value="">
        </form>
        <form id="submitGenre" action="${absolutePath}/mainServlet" method="get">
            <input type="hidden" name="command" value="form_mix_content_input_data">
            <input type="hidden" name="submit_genre" value="">
        </form>
    </tr>
</table>

<script>

    $("tr").click(function () {
        $(this).addClass("selected").siblings().removeClass("selected");
    });

    function formSubmitInfo(trackId) {
        var countId = document.getElementsByName("submit_track_id");
        for (var i = 0; i < countId.length; i++) {
            countId[i].setAttribute("value", trackId);
        }
    }

    function submitSelector(formName) {
        var trackAuthor = document.getElementById("authorSelector");
        var trackGenre = document.getElementById("genreSelector");

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
        var trackAuthor = document.getElementById("authorSelector").value;
        var trackTrack = document.getElementById("trackSelector").value;

        var countAuthor = document.getElementsByName("submit_author");
        for (var i = 0; i < countAuthor.length; i++) {
            countAuthor[i].setAttribute("value", trackAuthor);
        }

        var countTrack = document.getElementsByName("submit_track");
        for (var i = 0; i < countTrack.length; i++) {
            countTrack[i].setAttribute("value", trackTrack);
        }

        if (trackAuthor != "" && trackTrack != "") {
            document.getElementById(formName).submit();
        }
    }

    function submitDelete(formName) {
        var trackId = document.getElementById("id_for_delete").value;

        if (trackId != "") {
            document.getElementById(formName).submit();
        }
    }

</script>

</body>
</html>
