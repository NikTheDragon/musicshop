<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>

<html>

<head>
    <link href="<c:url value="/css/main.css" />" rel="stylesheet">
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>reg complete page</title>

    <%@include file="/WEB-INF/jspf/locale.jsp" %>
</head>

<body style="font-family: Arial, Helvetica, sans-serif">

<%@include file="/WEB-INF/jspf/header.jsp" %>
<%@include file="/WEB-INF/jspf/menu.jsp" %>

<table width="100%">
    <tr align="center">
        <td width="20%">
        </td>
        <td>
            <p>Registration complete.</p>

            <form action="/mainServlet" method="get">
                <input type="hidden" name="command" value="show_user_page">
                <input type="submit" value="${showUserPageButton}" style="width: 300px" >
            </form>
        </td>
        <td width="20%">
        </td>
    </tr>
</table>

</body>
</html>
