<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>

<html>
<head>
    <link href="<c:url value="/css/main.css" />" rel="stylesheet">
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>edit genres page</title>

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
        <td width="20%">
        </td>
        <td>
            <table id="customers" align="center">
                <tr align="center">
                    <th width="35%">Genre's name</th>
                </tr>

                <c:forEach var="genre" items="${genres}">
                    <c:if test="${genre.status == 'active'}">
                        <tr id="${genre.id}" onclick="show_name(this.id)">
                            <td id="${genre.id}name">${genre.name}</td>
                        </tr>
                    </c:if>
                </c:forEach>

            </table>

            <table width="100%">
                <tr>
                    <td width="200">
                        <input id="name" name="name" type="text" value="">
                    </td>
                    <form id="formCreate" action="/mainServlet" method="get">
                        <input type="hidden" name="command" value="create_genre">
                        <input id="idCreate" type="hidden" name="id" value="">
                        <input id="nameCreate" type="hidden" name="name" value="">
                        <td><input type="button" name="button" onclick="createName()" value="Create"></td>
                    </form>
                    <form id="formUpdate" action="/mainServlet" method="get">
                        <input type="hidden" name="command" value="update_genre">
                        <input id="idUpdate" type="hidden" name="id" value="">
                        <input id="nameUpdate" type="hidden" name="name" value="">
                        <td><input type="button" name="button" onclick="updName()" value="Update"></td>
                    </form>
                    <form id="formDelete" action="/mainServlet" method="get">
                        <input type="hidden" name="command" value="delete_genre">
                        <input id="idDelete" type="hidden" name="id" value="">
                        <input id="nameDelete" type="hidden" name="name" value="">
                        <td><input type="button" name="button" onclick="delName()" value="Delete"></td>
                    </form>
                </tr>

            </table>
        </td>
        <td width="20%">
        </td>
    </tr>

</table>

<script>
    function show_name(clicked_row) {
        var name = document.getElementById("name");
        name.value = document.getElementById(clicked_row + "name").textContent;
        document.getElementById("idCreate").setAttribute("value", clicked_row);
        document.getElementById("idUpdate").setAttribute("value", clicked_row);
        document.getElementById("idDelete").setAttribute("value", clicked_row);
    }

    function createName() {
        var genreName = document.getElementById("name");
        document.getElementById("nameCreate").setAttribute("value", genreName.value);
        document.getElementById("formCreate").submit();
    }

    function updName() {
        var genreName = document.getElementById("name");
        document.getElementById("nameUpdate").setAttribute("value", genreName.value);
        document.getElementById("formUpdate").submit();
    }

    function delName() {
        var genreName = document.getElementById("name");
        document.getElementById("nameDelete").setAttribute("value", genreName.value);
        document.getElementById("formDelete").submit();
    }
</script>

</body>
</html>
