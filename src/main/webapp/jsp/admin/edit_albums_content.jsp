<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>

<html>
<head>
    <link href="<c:url value="/css/main.css" />" rel="stylesheet">
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>edit albums content page</title>

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

<table id="fancyTable" style="width: 80%; margin-left: auto; margin-right: auto;">
    <tr>
        <th style="width: 35%; alignment: center">${currentAlbum.name}</th>
        <th style="width: 25%; alignment: center">${currentAlbum.author}</th>
        <th style="width: 10%; alignment: center">${currentAlbum.genre}</th>
        <th style="width: 10%; alignment: center">${currentAlbum.year}</th>
    </tr>
</table>

</body>
</html>
