<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>

<html>
<head>
    <link href="<c:url value="/css/main.css" />" rel="stylesheet">
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>edit authors</title>

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
            <h2>Список исполнителей</h2>
        </td>
    </tr>
</table>

<div style="width:100%; height:400px; overflow:auto;">
    <table id="fancyTable" style="width: 90%; margin-left: auto; margin-right: auto;">
        <tr>
            <th width="80%" onclick="sortTable('fancyTable', 0)">${nameHeader}</th>
            <th width="10%" onclick="sortTable('fancyTable', 1)">${genreHeader}</th>
            <th width="10%" onclick="sortTable('fancyTable', 2)">${typeHeader}</th>
        </tr>

        <c:forEach var="author" items="${authorList}">
            <c:if test="${author.status == 'active'}">
                <tr id="${author.id}" onclick="formSubmitInfo(this.id)">
                    <td id="${author.id}name">${author.name}</td>
                    <td id="${author.id}genre">${author.genre}</td>
                    <td id="${author.id}type">${author.type}</td>
                </tr>
            </c:if>
        </c:forEach>

    </table>
</div>

<br>

<table style="width: 90%; margin-left: auto; margin-right: auto;">
    <tr>
        <th width="80%">${nameHeader}</th>
        <th width="10%">${genreHeader}</th>
        <th width="10%">${typeHeader}</th>
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
        <td>
            <select id="type" name="type" size="1">
                <option value=""></option>
                <option value="Певец">${singleSelector}</option>
                <option value="Группа">${bandSelector}</option>
            </select>
        </td>

    </tr>
</table>

<table style="width: 90%; margin-left: auto; margin-right: auto;">
    <tr>
        <td width="100%"></td>
        <form id="formCreate" action="/mainServlet" method="get">
            <input type="hidden" name="command" value="create_author">
            <input type="hidden" name="submit_id" value="">
            <input type="hidden" name="submit_name" value="">
            <input type="hidden" name="submit_genre" value="">
            <input type="hidden" name="submit_type" value="">
            <td><input type="button" name="button" onclick="submitButton('formCreate')"
                       value="${createButton}"
                       style="width: 200px"></td>
        </form>
        <form id="formUpdate" action="/mainServlet" method="get">
            <input type="hidden" name="command" value="update_author">
            <input type="hidden" name="submit_id" value="">
            <input type="hidden" name="submit_name" value="">
            <input type="hidden" name="submit_genre" value="">
            <input type="hidden" name="submit_type" value="">
            <td><input type="button" name="button" onclick="submitButton('formUpdate')"
                       value="${updateButton}"
                       style="width: 200px"></td>
        </form>
        <form id="formDelete" action="/mainServlet" method="get">
            <input type="hidden" name="command" value="delete_author">
            <input type="hidden" name="submit_id" value="">
            <input type="hidden" name="submit_name" value="">
            <input type="hidden" name="submit_genre" value="">
            <input type="hidden" name="submit_type" value="">
            <td><input type="button" name="button" onclick="submitButton('formDelete')"
                       value="${deleteButton}"
                       style="width: 200px"></td>
        </form>
    </tr>
</table>

<script>

    $("tr").click(function () {
        $(this).addClass("selected").siblings().removeClass("selected");
    });

    function formSubmitInfo(authorId) {
        document.getElementById("name").value = document.getElementById(authorId + "name").textContent;
        document.getElementById("genre").value = document.getElementById(authorId + "genre").textContent;
        document.getElementById("type").value = document.getElementById(authorId + "type").textContent;

        var countId = document.getElementsByName("submit_id");

        for (var i = 0; i < countId.length; i++) {
            countId[i].setAttribute("value", authorId);
        }
    }

    function submitButton(formName) {
        var authorName = document.getElementById("name").value;
        var authorGenre = document.getElementById("genre").value;
        var authorType = document.getElementById("type").value;

        var countName = document.getElementsByName("submit_name");
        for (var i = 0; i < countName.length; i++) {
            countName[i].setAttribute("value", authorName.value);
        }

        var countGenre = document.getElementsByName("submit_genre");
        for (var i = 0; i < countGenre.length; i++) {
            countGenre[i].setAttribute("value", authorGenre.value);
        }

        var countType = document.getElementsByName("submit_type");
        for (var i = 0; i < countType.length; i++) {
            countType[i].setAttribute("value", authorType.value);
        }

        if (authorName != "" && authorGenre != "" && authorType != "") {
            document.getElementById(formName).submit();
        }
    }

</script>

<script src="/js/tableSort.js"></script>

</body>
</html>