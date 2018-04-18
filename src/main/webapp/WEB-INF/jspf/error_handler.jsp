<c:choose>

    <c:when test = "${message == 'track exists'}">
        <p>Can't create track. Track already exists.</p>
    </c:when>
    <c:when test = "${message == 'denied'}">
        <p>Access denied!</p>
    </c:when>
    <c:when test = "${message == 'undelete'}">
        <p>Can't delete entity.</p>
    </c:when>
    <c:otherwise>
        <p>${message}</p>
    </c:otherwise>

</c:choose>