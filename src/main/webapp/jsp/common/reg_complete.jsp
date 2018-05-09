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

<table style="width: 80%; margin-left: auto; margin-right: auto;">
    <tr style="text-align: center">
        <td>
            <p>Registration complete.</p>

            <form action="${absolutePath}/mainServlet" method="get">
                <input type="hidden" name="command" value="show_user_page">
                <input type="submit" value="${showUserPageButton}" style="width: 300px">
            </form>
        </td>
    </tr>
</table>

</body>
</html>
