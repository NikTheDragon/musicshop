<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>

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
                <input type="text" name="name" placeholder="${name}" value="${user.name}" style="width: 200Px">${nameMessage}<br>
                <input type="text" name="surname" placeholder="${surname}" value="${user.surname}" style="width: 200Px">${surnameMessage}<br>
                <input type="text" name="login" placeholder="${login}" value="${user.login}" style="width: 200Px">${loginMessage}<br>
                <input type="text" name="password" placeholder="${password}" value="${user.password}" style="width: 200Px">${passwordMessage}<br>
                <input type="text" name="e-mail" placeholder="e-mail" value="${user.email}" style="width: 200Px">${emailMessage}<br>
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
