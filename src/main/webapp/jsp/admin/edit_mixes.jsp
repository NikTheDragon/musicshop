<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>

<html>
<head>
    <link href="<c:url value="/css/main.css" />" rel="stylesheet">
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>edit mixes page</title>

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
        <td width="5%">
        </td>
        <td>
            <table id="customers" width="100%">
                <tr>
                    <th width="80%">${nameHeader}</th>
                    <th width="10%">${genreHeader}</th>
                    <th width="10%">${yearHeader}</th>
                </tr>

                <c:forEach var="album" items="${albumList}">
                    <c:if test="${album.status == 'active'}">
                        <tr id="${album.id}" onclick="getMixInfo(this.id)">
                            <td id="${album.id}name">${album.name}</td>
                            <td id="${album.id}genre">${album.genre}</td>
                            <td id="${album.id}year">${album.year}</td>
                        </tr>
                    </c:if>
                </c:forEach>

            </table>
        </td>
        <td width="5%">
        </td>
    </tr>
</table>

<br>

<table width="100%">
    <tr>
        <td width="5%">
        </td>
        <td>
            <table width="100%">
                <tr>
                    <th width="80%">${nameHeader}</th>
                    <th width="10%">${genreHeader}</th>
                    <th width="10%">${yearHeader}</th>
                </tr>
                <tr>
                    <td><input id="name" name="name" type="text" value=""></td>
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
                </tr>
            </table>
            <table>
                <tr>
                    <td width="100%"></td>
                    <form id="formCreate" action="/mainServlet" method="get">
                        <input type="hidden" name="command" value="create_mix">
                        <input type="hidden" name="submit_id" value="">
                        <input type="hidden" name="submit_name" value="">
                        <input type="hidden" name="submit_genre" value="">
                        <input type="hidden" name="submit_year" value="">
                        <td><input type="button" name="button" onclick="submitButton('formCreate')" value="${createButton}" style="width: 200px"></td>
                    </form>
                    <form id="formUpdate" action="/mainServlet" method="get">
                        <input type="hidden" name="command" value="update_mix">
                        <input type="hidden" name="submit_id" value="">
                        <input type="hidden" name="submit_name" value="">
                        <input type="hidden" name="submit_genre" value="">
                        <input type="hidden" name="submit_year" value="">
                        <td><input type="button" name="button" onclick="submitButton('formUpdate')" value="${updateButton}" style="width: 200px"></td>
                    </form>
                    <form id="formDelete" action="/mainServlet" method="get">
                        <input type="hidden" name="command" value="delete_mix">
                        <input type="hidden" name="submit_id" value="">
                        <input type="hidden" name="submit_name" value="">
                        <input type="hidden" name="submit_genre" value="">
                        <input type="hidden" name="submit_year" value="">
                        <td><input type="button" name="button" onclick="submitButton('formDelete')" value="${deleteButton}" style="width: 200px"></td>
                    </form>
                </tr>
            </table>
        </td>
        <td width="5%">
        </td>
    </tr>
</table>

<script>
    function getMixInfo(clicked_row) {
        var name = document.getElementById("name");
        var genre = document.getElementById("genre");
        var year = document.getElementById("year");
        name.value = document.getElementById(clicked_row + "name").textContent;
        genre.value = document.getElementById(clicked_row + "genre").textContent;
        year.value = document.getElementById(clicked_row + "year").textContent;

        var countId = document.getElementsByName("submit_id");

        for (var i = 0; i < countId.length; i++) {
            countId[i].setAttribute("value", clicked_row);
        }
    }

    function submitButton(formName) {
        var trackName = document.getElementById("name");
        var trackGenre = document.getElementById("genre");
        var trackYear = document.getElementById("year");

        var countName = document.getElementsByName("submit_name");

        for (var i = 0; i < countName.length; i++) {
            countName[i].setAttribute("value", trackName.value);
        }

        var countGenre = document.getElementsByName("submit_genre");

        for (var i = 0; i < countGenre.length; i++) {
            countGenre[i].setAttribute("value", trackGenre.value);
        }

        var countYear = document.getElementsByName("submit_year");

        for (var i = 0; i < countYear.length; i++) {
            countYear[i].setAttribute("value", trackYear.value);
        }

        document.getElementById(formName).submit();
    }

</script>

</body>
</html>