<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
    <link href="<c:url value="/css/main.css" />" rel="stylesheet">
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>reg page</title>

    <%@include file="/WEB-INF/jspf/locale.jsp" %>
</head>
<body style="font-family: Arial, Helvetica, sans-serif">

<%@include file="/WEB-INF/jspf/header.jsp" %>
<%@include file="/WEB-INF/jspf/menu.jsp" %>

<table width="100%">
    <tr>
        <td width="20%">
        </td>
        <td bgcolor="#dcf7ff">
            <form action="/common" method="post">
                <p>Please, fill all fields.</p>
                <br>
                <input type="text" name="name" placeholder="name" style="width: 200Px"><br>
                <input type="text" name="surname" placeholder="surname" style="width: 200Px"><br>
                <input type="text" name="login" placeholder="login" style="width: 200Px"><br>
                <input type="text" name="password" placeholder="password" style="width: 200Px"><br>
                <input type="text" name="e-mail" placeholder="e-mail" style="width: 200Px"><br>
                <br>
                <input type="hidden" name="command" value="reg_new_user"/>
                <input type="submit" value="${registerButton}" style="width: 200Px">
            </form>
        </td>
        <td width="20%">
        </td>
    </tr>
</table>

</body>
</html>
