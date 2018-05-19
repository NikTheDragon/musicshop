<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>

<html>
<head>
    <link href="<c:url value="/css/main.css" />" rel="stylesheet">
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>edit genres page</title>

    <script src="<c:url value="/js/jquery.min.js" />"></script>

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

<table style="width: 80%; margin-left: auto; margin-right: auto;">
    <tr>
        <td style="text-align: center">
            <h2>Список жанров</h2>
        </td>
    </tr>
</table>

<table id="fancyTable" style="width: 80%; margin-left: auto; margin-right: auto;">
    <tr align="center">
        <th width="35%">${titleHeader}</th>
    </tr>

    <c:forEach var="currentGenre" items="${genreList}">
        <c:if test="${currentGenre.status == 'active'}">
            <tr id="${currentGenre.id}" onclick="formSubmitInfo(this.id)">
                <td id="${currentGenre.id}name">${currentGenre.name}</td>
            </tr>
        </c:if>
    </c:forEach>

</table>

<table style="width: 80%; margin-left: auto; margin-right: auto;">
    <tr>
        <td width="200">
            <input id="name" name="name" type="text" value="">
        </td>
        <td><input type="button" name="button" onclick="submitButton('formCreate')" value="${createButton}"></td>
        <td><input type="button" name="button" onclick="submitButton('formUpdate')" value="${updateButton}"></td>
        <td><input type="button" name="button" onclick="submitButton('formDelete')" value="${deleteButton}"></td>
    </tr>
    <tr>
        <td style="text-align: center">
            <c:set var="message" value="${messages['nameResult']}"/>
            <%@include file="/WEB-INF/jspf/error_handler.jsp" %>
        </td>
        <td></td>
        <td></td>
        <td></td>
    </tr>
</table>

<form id="CUDform" action="${absolutePath}/mainServlet" method="get">
    <input type="hidden" id="command" name="command" value="">
    <input type="hidden" id="submit_id" name="submit_id" value="">
    <input type="hidden" id="submit_name" name="submit_name" value="">
    <input type="hidden" id="submit_status" name="submit_status" value="active">
</form>

<script>

    $("tr").click(function () {
        $(this).addClass("selected").siblings().removeClass("selected");
    });

    function formSubmitInfo(genreId) {
        document.getElementById("name").value = document.getElementById(genreId + "name").textContent;
        document.getElementById("submit_id").value = genreId;
    }

    function submitButton(formName) {
        var genreName = document.getElementById("name").value;

        document.getElementById("submit_name").value = genreName;

        if (formName == "formCreate") {
            document.getElementById("command").value = "create_genre"
        }

        if (formName == "formUpdate") {
            document.getElementById("command").value = "update_genre"
        }

        if (formName == "formDelete") {
            document.getElementById("command").value = "delete_genre"
        }

        if (genreName != "") {
            document.getElementById("CUDform").submit();
        }
    }

</script>

</body>
</html>
