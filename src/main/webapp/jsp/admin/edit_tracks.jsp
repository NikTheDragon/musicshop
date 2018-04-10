<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>

<html>
<head>
    <link href="<c:url value="/css/main.css" />" rel="stylesheet">
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>edit tracks page</title>

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
            <table id="customers" width="100%">
                <tr>
                    <th width="35%">Name</th>
                    <th width="35%">Author</th>
                    <th width="10%">Genre</th>
                    <th width="10%">Year</th>
                    <th width="10%">Length</th>
                </tr>
                <tr id="001" onclick="show_name(this.id)">
                    <td id="${'001'}name">Beat it</td>
                    <td id="${'001'}author">Michael Jackson</td>
                    <td id="${'001'}genre">pop</td>
                    <td id="${'001'}year">1980</td>
                    <td id="${'001'}length">5.31</td>
                </tr>
                <tr id="002" onclick="show_name(this.id)">
                    <td id="${'002'}name">It's my life</td>
                    <td id="${'002'}author">Bon Jowi</td>
                    <td id="${'002'}genre">rock</td>
                    <td id="${'002'}year">1985</td>
                    <td id="${'002'}length">4.23</td>
                </tr>
            </table>
        </td>
        <td width="20%">
        </td>
    </tr>
</table>

<br>

<table width="100%">
    <tr>
        <td width="20%">
        </td>
        <td>
            <table width="100%">
                <tr>
                    <th width="35%">Name</th>
                    <th width="35%">Author</th>
                    <th width="10%">Genre</th>
                    <th width="10%">Year</th>
                    <th width="10%">Length</th>
                </tr>
                <tr>
                    <td><input id="name" name="name" type="text" value=""></td>
                    <td><input id="author" name="author" type="text" value=""></td>
                    <td><input id="genre" name="genre" type="text" value=""></td>
                    <td><input id="year" name="year" type="text" value=""></td>
                    <td><input id="length" name="length" type="text" value=""></td>
                </tr>
                <tr>
                    <td></td>
                    <td></td>
                    <form action="/mainServlet" method="get">
                        <input type="hidden" name="command" value="delete_track">
                        <td><input type="submit" name="delete" value="Delete"></td>
                    </form>
                    <td><input type="button" name="update" value="Update"></td>
                    <td><input type="button" name="create" value="Create"></td>
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
        var author = document.getElementById("author");
        var genre = document.getElementById("genre");
        var year = document.getElementById("year");
        var length = document.getElementById("length");
        name.value = document.getElementById(clicked_row + "name").textContent;
        author.value = document.getElementById(clicked_row + "author").textContent;
        genre.value = document.getElementById(clicked_row + "genre").textContent;
        year.value = document.getElementById(clicked_row + "year").textContent;
        length.value = document.getElementById(clicked_row + "length").textContent;
    }

</script>

</body>
</html>