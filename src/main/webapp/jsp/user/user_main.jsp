<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>

<html>
<head>
    <link href="<c:url value="/css/main.css" />" rel="stylesheet">
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>user main page</title>

    <%@include file="/WEB-INF/jspf/locale.jsp"%>

</head>

<c:set var = "user_role" scope = "session" value = "${user.role}"/>
<c:if test="${user_role != 'user'}">
    <c:redirect url="/mainServlet?command=show_main_page"/>
</c:if>

<body style="font-family: Arial, Helvetica, sans-serif">

<%@include file="/WEB-INF/jspf/header.jsp"%>
<%@include file="/WEB-INF/jspf/user_menu.jsp"%>

<table width="100%">
    <tr>
        <td width="20%">
        </td>
        <td>
        <p>USER PAGE</p>
        </td>
        <td width="20%">
        </td>
    </tr>
</table>

</body>
</html>
