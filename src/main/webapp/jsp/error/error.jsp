<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>

<html>
<head>
    <link href="<c:url value="/css/main.css" />" rel="stylesheet">
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>error page</title>

    <%@include file="/WEB-INF/jspf/locale.jsp" %>

</head>
<body style="font-family: Arial, Helvetica, sans-serif">

<%@include file="/WEB-INF/jspf/header.jsp" %>

<c:set var="user_role" scope="session" value="${user.role}"/>
<c:choose>
    <c:when test="${user_role == 'user'}">
        <%@include file="/WEB-INF/jspf/user_menu.jsp" %>
    </c:when>
    <c:when test="${user_role == 'admin'}">
        <%@include file="/WEB-INF/jspf/admin_menu.jsp" %>
    </c:when>
    <c:otherwise>
        <%@include file="/WEB-INF/jspf/menu.jsp" %>
    </c:otherwise>
</c:choose>

<table width="100%">
    <tr style="alignment: center">
        <td width="20%">
        </td>
        <td width="60%" align="center">
            <p>Error!</p>
            <%@include file="/WEB-INF/jspf/error_handler.jsp" %>
        </td>
        <td width="20%">
        </td>
    </tr>
</table>

<table width="100%">
    <tr>
        <td width="100%" align="center">
            <form action="${absolutePath}/mainServlet" method="get">
                <input type="hidden" name="command" value="back_to_page">
                <input type="submit" name="button" value="${returnButton}" style="width: 200px">
            </form>
        </td>
    </tr>
</table>


</body>
</html>
