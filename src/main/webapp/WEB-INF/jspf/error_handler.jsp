<c:choose>

    <c:when test = "${message == 'unable'}">
        Unable to proceed command.
    </c:when>
    <c:when test = "${message == 'exists'}">
        ${errorExists}
    </c:when>
    <c:when test = "${message == 'denied'}">
        Access denied!
    </c:when>
    <c:when test = "${message == 'undelete'}">
        Can't delete entity.
    </c:when>
    <c:when test = "${message == 'unupdate'}">
        Can't update entity.
    </c:when>
    <c:when test = "${message == 'null'}">
        ${inputNull}
    </c:when>
    <c:when test = "${message == 'notText'}">
        ${inputNotText}
    </c:when>
    <c:when test = "${message == 'length'}">
        ${inputLength}
    </c:when>
    <c:when test = "${message == 'notLogPas'}">
        ${inputNotLogPas}
    </c:when>
    <c:when test = "${message == 'notMail'}">
        ${inputNotMail}
    </c:when>
    <c:when test = "${message == 'login'}">
        Login already in use.
    </c:when>
    <c:when test = "${message == 'nocmd'}">
        Can't find command: ${cmd}
    </c:when>
    <c:when test = "${message == 'notDigit'}">
        ${inputNotDigit}
    </c:when>
    <c:when test = "${message == 'noLP'}">
        Login and password mismatch
    </c:when>
    <c:when test = "${message == 'true'}">
    </c:when>
    <c:otherwise>
        ${message}
    </c:otherwise>

</c:choose>