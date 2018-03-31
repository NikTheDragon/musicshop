<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>

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
        <td width="45%">

        </td>
        <td width="15%">
            <form action="/common" method="post">
                <br>
                <input type="text"
                       name="login"
                       placeholder="${login}"
                       pattern="[a-z]{3,15}"
                       title="Username should only contain lowercase letters. e.g. john">
                <br><br>
                <input type="password"
                       name="password"
                       placeholder="${password}"
                       pattern="[a-z]{3,15}"
                       title="Username should only contain lowercase letters. e.g. john">
                <br><br>
                <input type="hidden" name="command" value="login_user">
                <input type="submit" value=${loginButton}>
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
