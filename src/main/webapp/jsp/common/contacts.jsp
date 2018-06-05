<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="/WEB-INF/paging.tld" prefix="pg" %>
<!DOCTYPE html>

<html>
<head>
    <link href="<c:url value="/css/main.css" />" rel="stylesheet">
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>albums</title>

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

<table style="width: 90%; text-align: center">
    <tr>
        <td>
            <p><h2>Магазин продажи музыкальных треков.</h2></p>
            <p><h4>Автор: Курлович Николай</h4></p>
            <p>Минск 2018</p>
            <p>Наш адрес: Ул. Якубова д 14</p>
            <p>Проезд троллейбусом 36 или автобусом 127 до останвки Серебрянка 3</p>
            <p>Телефон 8-029-0000000</p>
            <img src="<c:url value="${pageContext.request.contextPath}/img/map.jpg"/>" />
        </td>
    </tr>
</table>


</body>
</html>