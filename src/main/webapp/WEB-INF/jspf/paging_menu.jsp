<table style="text-align: center; width: 90%; margin-left: auto; margin-right: auto; font-size: 14px">
    <tr>
        <td>
            <a href="<c:url value="/mainServlet">
                <c:param name="command" value="show_selected_rows"/>
                <c:param name="currentURI" value="${pageContext.request.requestURI}"/>
                <c:param name="currentPageNumber" value="${firstPage}"/>
                </c:url>">${firstPage}</a>
        </td>
        <td>
            <c:forEach var="i" begin="${first}" end="${last}">
                <c:url value="/mainServlet" var="myURL">
                    <c:param name="command" value="show_selected_rows"/>
                    <c:param name="currentURI" value="${pageContext.request.requestURI}"/>
                    <c:param name="currentPageNumber" value="${i}"/>
                </c:url>
                <a href="${myURL}"><c:out value="${i}"/></a>
            </c:forEach>
        </td>
        <td>
            <a href="<c:url value="/mainServlet">
                <c:param name="command" value="show_selected_rows"/>
                <c:param name="currentURI" value="${pageContext.request.requestURI}"/>
                <c:param name="currentPageNumber" value="${lastPage}"/>
                </c:url>">${lastPage}</a>
        </td>
    </tr>
</table>
