<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="/WEB-INF/paging.tld" prefix="pg" %>
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

<c:if test="${currentPage == null || currentPage == ''}">
    <c:set var="currentPage" value="1"/>
</c:if>

<pg:getFirstPageNumber data="${authorList}" variable="firstPage"/>
<pg:getLastPageNumber data="${authorList}" variable="lastPage"/>
<pg:getSelectPageNumbers data="${authorList}" page="${currentPage}" first="first" last="last"/>
<pg:getSelectedRows data="${authorList}" rows="selectedRows" page="${currentPage}"/>

<br>

<table width="100%">
    <tr>
        <td style="text-align: center">
            <h2>Список исполнителей</h2>
        </td>
    </tr>
</table>

<table style="width: 90%; margin-left: auto; margin-right: auto; font-size: 12px">
    <tr>
    <tr style="text-align: center">
        <td width="70%">
            ${nameHeader}
        </td>
        <td width="10%">
            ${genreHeader}
        </td>
        <td width="10%">
            ${typeHeader}
        </td>
        <td width="10%">
        </td>
    </tr>
    <form id="searchForm" action="${absolutePath}/mainServlet" method="post">
        <input type="hidden" name="command" value="search_authors">
        <input type="hidden" name="currentURI" value="${pageContext.request.requestURI}">

        <td width="70%">
            <input type="text" id="search_name" name="search_name" value="">
        </td>
        <td width="10%">
            <input type="text" id="search_genre" name="search_genre" value="">
        </td>
        <td width="10%">
            <input type="text" id="search_year" name="search_type" value="">
        </td>
        <td width="10%">
            <input type="button" id="search_button" name="search" value="Search" onclick="searchButton()">
        </td>
    </form>
    </tr>

</table>

<table id="fancyTable" style="width: 90%; margin-left: auto; margin-right: auto; font-size: 12px">
    <tr>
        <th width="80%" onclick="sortTable('fancyTable', 0)">${nameHeader}</th>
        <th width="10%" onclick="sortTable('fancyTable', 1)">${genreHeader}</th>
        <th width="10%" onclick="sortTable('fancyTable', 2)">${typeHeader}</th>
    </tr>

    <c:forEach var="author" items="${selectedRows}">
        <c:if test="${author.status == 'active'}">
            <tr id="${author.id}" onclick="formSubmitInfo(this.id)">
                <td id="${author.id}name">${author.name}</td>
                <td id="${author.id}genre">${author.genre}</td>
                <td id="${author.id}type">${author.type}</td>
            </tr>
        </c:if>
    </c:forEach>

</table>

<%@include file="/WEB-INF/jspf/paging_menu.jsp" %>

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
    <tr>
        <td>
            <c:set var="message" value="${messages['nameResult']}"/>
            <%@include file="/WEB-INF/jspf/error_handler.jsp" %>
        </td>
        <td>
            <c:set var="message" value="${messages['genreResult']}"/>
            <%@include file="/WEB-INF/jspf/error_handler.jsp" %>
        </td>
        <td>
            <c:set var="message" value="${messages['typeResult']}"/>
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
    <input type="hidden" id="submit_genre" name="submit_genre" value="">
    <input type="hidden" id="submit_type" name="submit_type" value="">
    <input type="hidden" id="submit_status" name="submit_status" value="active">
</form>

<script>

    $("tr").click(function () {
        $(this).addClass("selected").siblings().removeClass("selected");
    });

    function formSubmitInfo(authorId) {
        document.getElementById("name").value = document.getElementById(authorId + "name").textContent;
        document.getElementById("genre").value = document.getElementById(authorId + "genre").textContent;
        document.getElementById("type").value = document.getElementById(authorId + "type").textContent;
        document.getElementById("submit_id").value = authorId;
    }

    function submitButton(formName) {
        var authorName = document.getElementById("name").value;
        var authorGenre = document.getElementById("genre").value;
        var authorType = document.getElementById("type").value;

        document.getElementById("submit_name").value = authorName;
        document.getElementById("submit_genre").value = authorGenre;
        document.getElementById("submit_type").value = authorType;

        if (formName == "formCreate") {
            document.getElementById("command").value = "create_author"
        }

        if (formName == "formUpdate") {
            document.getElementById("command").value = "update_author"
        }

        if (formName == "formDelete") {
            document.getElementById("command").value = "delete_author"
        }

        if (authorName != "" && authorGenre != "" && authorType != "") {
            document.getElementById("CUDform").submit();
        }
    }

    function searchButton() {
        document.getElementById("searchForm").submit();
    }

</script>

<script src="/js/tableSort.js"></script>

</body>
</html>