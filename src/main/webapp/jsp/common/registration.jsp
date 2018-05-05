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

<form action="/mainServlet" method="post">
    <table style="width: 80%; margin-left: auto; margin-right: auto;">
        <tr>
            <td colspan="2"><p>${fillAllFields}</p></td>
        </tr>

        <tr title="name input field">
            <td style="width: 220px">
                <input type="text" name="submit_name" placeholder="${name}" value="${user.name}"
                       style="width: 200Px">
            </td>
            <td style="text-align:left">
                <c:set var="message" value="${messages['nameMessage']}"/>
                <%@include file="/WEB-INF/jspf/error_handler.jsp" %>
            </td>
        </tr>
        <tr title="surname input field">
            <td>
                <input type="text" name="submit_surname" placeholder="${surname}" value="${user.surname}"
                       style="width: 200Px">
            </td>
            <td style="text-align:left">
                <c:set var="message" value="${messages['surnameMessage']}"/>
                <%@include file="/WEB-INF/jspf/error_handler.jsp" %>
            </td>
        </tr>
        <tr title="login input field">
            <td>
                <input type="text" name="submit_login" placeholder="${login}" value="${user.login}"
                       style="width: 200Px">
            </td>
            <td style="text-align:left">
                <c:set var="message" value="${messages['loginMessage']}"/>
                <%@include file="/WEB-INF/jspf/error_handler.jsp" %>
            </td>
        </tr>
        <tr title="password input field">
            <td>
                <input type="text" name="submit_password" placeholder="${password}" value="${user.password}"
                       style="width: 200Px">
            </td>
            <td style="text-align:left">
                <c:set var="message" value="${messages['passwordMessage']}"/>
                <%@include file="/WEB-INF/jspf/error_handler.jsp" %>
            </td>
        </tr>
        <tr title="email input field">
            <td>
                <input type="text" name="submit_email" placeholder="e-mail" value="${user.email}"
                       style="width: 200Px">
            </td>
            <td style="text-align:left">
                <c:set var="message" value="${messages['emailMessage']}"/>
                <%@include file="/WEB-INF/jspf/error_handler.jsp" %>
            </td>
        </tr>
        <tr>
            <td title="submit button">
                <input type="hidden" name="command" value="reg_new_user">
                <input type="submit" value="${registerButton}" style="width: 200Px">
            </td>
            <td></td>
        </tr>
    </table>
</form>

</body>
</html>
