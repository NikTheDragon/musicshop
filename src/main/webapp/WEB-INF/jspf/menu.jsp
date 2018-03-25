<table width="100%">
    <tr align="center">
        <td width="20%">
        </td>
        <td>
            <ul>
                <li><a href="common?command=show_main_page">${mainPage}</a></li>
                <li class="dropdown">
                    <a href="javascript:void(0)" class="dropbtn">${catalogue}</a>
                    <div class="dropdown-content">
                        <a href="#">${tracks}</a>
                        <a href="#">${albums}</a>
                        <a href="#">${mixes}</a>
                    </div>
                </li>
                <li><a href="#news">${contacts}</a></li>
                <li class="dropdown">
                    <a href="javascript:void(0)" class="dropbtn">${search}</a>
                    <div class="dropdown-content">
                        <form target="/common" method="get">
                            <input type="hidden" name="command" value="search">
                            <input type="text" name="text" value="" placeholder="Search..">
                        </form>
                    </div>
                </li>
                <li class="dropdown" style="float: right">
                    <a href="javascript:void(0)" class="dropbtn">${language}</a>
                    <div class="dropdown-content">
                        <form target="/common" method="post">
                            <a href="<c:url value="/common">
                                <c:param name="command" value="change_language"/>
                                <c:param name="locale" value="en"/>
                                </c:url>
                            ">English</a>
                            <a href="<c:url value="/common">
                                <c:param name="command" value="change_language"/>
                                <c:param name="locale" value="ru"/>
                                </c:url>
                            ">Russian</a>
                        </form>
                    </div>
                </li>
            </ul>
        </td>
        <td width="20%">
        </td>
    </tr>
</table>
