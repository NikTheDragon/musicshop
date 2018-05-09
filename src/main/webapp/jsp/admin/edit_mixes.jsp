<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>

<html>
<head>
    <link href="<c:url value="/css/main.css" />" rel="stylesheet">
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>edit mixes page</title>

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
            <h2>Сборки песен</h2>
        </td>
    </tr>
</table>

<table id="fancyTable" style="width: 80%; margin-left: auto; margin-right: auto;">
    <tr>
        <th width="80%">${titleHeader}</th>
        <th width="10%">${genreHeader}</th>
        <th width="10%">${yearHeader}</th>
    </tr>

    <c:forEach var="currentMix" items="${mixList}">
        <c:if test="${currentMix.status == 'active'}">
            <tr id="${currentMix.id}" onclick="formSubmitInfo(this.id)">
                <td id="${currentMix.id}name">${currentMix.name}</td>
                <td id="${currentMix.id}genre">${currentMix.genre}</td>
                <td id="${currentMix.id}year">${currentMix.year}</td>
            </tr>
        </c:if>
    </c:forEach>

</table>

<br>

<table style="width: 80%; margin-left: auto; margin-right: auto;">
    <tr>
        <th width="80%">${titleHeader}</th>
        <th width="10%">${genreHeader}</th>
        <th width="10%">${yearHeader}</th>
    </tr>
    <tr>
        <td><input id="name" name="name" type="text" value=""></td>
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
</table>

<table style="width: 80%; margin-left: auto; margin-right: auto;">
    <tr>
        <td style="width: 100%"></td>
        <form id="formContent" action="${absolutePath}/mainServlet" method="get">
            <input type="hidden" name="command" value="show_edit_mixes_content_page">
            <input type="hidden" name="submit_id" value="">
            <td><input id="contentButton" type="submit" name="button" value="${editContentButton}"
                       style="display: none; width: 200px"></td>
        </form>
        <form id="formCreate" action="${absolutePath}/mainServlet" method="get">
            <input type="hidden" name="command" value="create_mix">
            <input type="hidden" name="submit_id" value="">
            <input type="hidden" name="submit_name" value="">
            <input type="hidden" name="submit_genre" value="">
            <input type="hidden" name="submit_year" value="">
            <td><input type="button" name="button" onclick="submitButton('formCreate')"
                       value="${createButton}" style="width: 200px"></td>
        </form>
        <form id="formUpdate" action="${absolutePath}/mainServlet" method="get">
            <input type="hidden" name="command" value="update_mix">
            <input type="hidden" name="submit_id" value="">
            <input type="hidden" name="submit_name" value="">
            <input type="hidden" name="submit_genre" value="">
            <input type="hidden" name="submit_year" value="">
            <td><input type="button" name="button" onclick="submitButton('formUpdate')"
                       value="${updateButton}" style="width: 200px"></td>
        </form>
        <form id="formDelete" action="${absolutePath}/mainServlet" method="get">
            <input type="hidden" name="command" value="delete_mix">
            <input type="hidden" name="submit_id" value="">
            <input type="hidden" name="submit_name" value="">
            <input type="hidden" name="submit_genre" value="">
            <input type="hidden" name="submit_year" value="">
            <td><input type="button" name="button" onclick="submitButton('formDelete')"
                       value="${deleteButton}" style="width: 200px"></td>
        </form>
    </tr>
</table>

<script>

    $("tr").click(function () {
        $(this).addClass("selected").siblings().removeClass("selected");
    });

    function formSubmitInfo(mixId) {
        document.getElementById("name").value = document.getElementById(mixId + "name").textContent;
        document.getElementById("genreSelector").value = document.getElementById(mixId + "genre").textContent;
        document.getElementById("year").value = document.getElementById(mixId + "year").textContent;

        document.getElementById("contentButton").style.display = "block";

        var countId = document.getElementsByName("submit_id");
        for (var i = 0; i < countId.length; i++) {
            countId[i].setAttribute("value", mixId);
        }
    }

    function submitButton(formName) {
        var trackName = document.getElementById("name").value;
        var trackGenre = document.getElementById("genreSelector").value;
        var trackYear = document.getElementById("year").value;

        var countName = document.getElementsByName("submit_name");
        for (var i = 0; i < countName.length; i++) {
            countName[i].setAttribute("value", trackName);
        }

        var countGenre = document.getElementsByName("submit_genre");
        for (var i = 0; i < countGenre.length; i++) {
            countGenre[i].setAttribute("value", trackGenre);
        }

        var countYear = document.getElementsByName("submit_year");
        for (var i = 0; i < countYear.length; i++) {
            countYear[i].setAttribute("value", trackYear);
        }
        if(trackName != "" && trackGenre != "" && trackYear != "") {
            document.getElementById(formName).submit();
        }
    }

</script>

</body>
</html>