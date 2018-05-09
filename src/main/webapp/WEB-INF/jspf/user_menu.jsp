<table style="width: 80%; margin-left: auto; margin-right: auto;">
    <tr align="center">
        <td>
            <ul>
                <li><a href="<c:url value="/mainServlet">
                                <c:param name="command" value="show_main_page"/>
                                 </c:url>
                            ">${mainPage}</a>
                <li class="dropdown">
                    <a href="javascript:void(0)" class="dropbtn">${catalogue}</a>
                    <div class="dropdown-content">
                        <form action="${absolutePath}/mainServlet" method="get">
                            <a href="<c:url value="/mainServlet">
                                <c:param name="command" value="show_all_tracks"/>
                                 </c:url>
                            ">${tracks}</a>
                        </form>
                        <form action="${absolutePath}/mainServlet" method="get">
                            <a href="<c:url value="/mainServlet">
                                <c:param name="command" value="show_all_albums"/>
                                 </c:url>
                            ">${albums}</a>
                        </form>
                        <form action="${absolutePath}/mainServlet" method="get">
                            <a href="<c:url value="/mainServlet">
                                <c:param name="command" value="show_all_mixes"/>
                                 </c:url>
                            ">${mixes}</a>
                        </form>
                    </div>
                </li>
                <li><a href="#news">${contacts}</a></li>
                <li class="dropdown">
                    <a href="javascript:void(0)" class="dropbtn">${folders}</a>
                    <div class="dropdown-content">
                        <a href="#">${cart}</a>
                        <a href="#">${bought}</a>
                    </div>
                </li>
                <li class="dropdown">
                    <a href="javascript:void(0)" class="dropbtn">${user.name} (${user.points})</a>
                    <div class="dropdown-content">
                        <form action="${absolutePath}/mainServlet" method="get">
                            <a href="<c:url value="/mainServlet">
                                <c:param name="command" value="show_personal_page"/>
                                 </c:url>
                            ">${personal}</a>
                        </form>
                        <form action="${absolutePath}/mainServlet" method="get">
                            <a href="<c:url value="/mainServlet">
                                <c:param name="command" value="show_point_management_page"/>
                                 </c:url>
                            ">Add points</a>
                        </form>
                        <form action="${absolutePath}/mainServlet" method="get">
                            <a href="<c:url value="/mainServlet">
                                <c:param name="command" value="logout"/>
                                 </c:url>
                            ">${logout}</a>
                        </form>
                    </div>
                </li>
                <li class="dropdown" style="float: right">
                    <a href="javascript:void(0)" class="dropbtn">${language}</a>
                    <div class="dropdown-content">
                        <form action="${absolutePath}/mainServlet" method="post">
                            <a href="<c:url value="/mainServlet">
                                <c:param name="command" value="change_language"/>
                                <c:param name="locale" value="en"/>
                                </c:url>
                            ">English</a>
                            <a href="<c:url value="/mainServlet">
                                <c:param name="command" value="change_language"/>
                                <c:param name="locale" value="ru"/>
                                </c:url>
                            ">Russian</a>
                        </form>
                    </div>
                </li>
            </ul>
        </td>
    </tr>
</table>
