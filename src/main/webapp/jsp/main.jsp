<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
    <link href="<c:url value="/css/main.css" />" rel="stylesheet">
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>main page</title>

    <%@include file="/WEB-INF/jspf/locale.jsp"%>

</head>

<body style="font-family: Arial, Helvetica, sans-serif">

<%@include file="/WEB-INF/jspf/header.jsp"%>
<%@include file="/WEB-INF/jspf/menu.jsp"%>

<table width="100%">
    <tr><td width="20%">

    </td>
        <td width="50%">

        </td>
        <td width="10%">
            <form>
                <input type="text" name="login" placeholder="Login">
                <br>
                <input type="password" name="password" placeholder="Password">
                <input type="button" value=${loginButton}>
            </form>
            <form action="/common" method="get">
                <input type="hidden" name="command" value="show_reg_page">
                <input type="submit" value=${registerButton}>
            </form>
        </td>
        <td width="20%">

        </td>
    </tr>

</table>
</body>
</html>
