<c:choose>

    <c:when test = "${message == 'exists'}">
        <p>Can't create track. Track already exists.</p>
    </c:when>
    <c:when test = "${message == 'denied'}">
        <p>Access denied!</p>
    </c:when>
    <c:when test = "${message == 'undelete'}">
        <p>Can't delete entity.</p>
    </c:when>
    <c:when test = "${message == 'unupdate'}">
        <p>Can't update entity.</p>
    </c:when>
    <c:when test = "${message == 'null'}">
        Empty field. Fill it.
    </c:when>
    <c:when test = "${message == 'login'}">
        Login already in use.
    </c:when>
    <c:when test = "${message == 'nocmd'}">
        <p>Can't find command: ${cmd}</p>
    </c:when>
    <c:otherwise>
        <p>${message}</p>
    </c:otherwise>

</c:choose>