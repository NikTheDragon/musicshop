<table width="100%">
    <tr align="center">
        <td width="20%">
        </td>
        <td>
            <ul>
                <li><a href="/mainServlet?command=show_main_page">${mainPage}</a></li>
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
                        <form target="/mainServlet" method="get">
                            <input type="hidden" name="command" value="search">
                            <input type="text" name="text" value="" placeholder="Search..">
                        </form>
                    </div>
                </li>
                <li class="dropdown">
                    <a href="javascript:void(0)" class="dropbtn">${adminMusic}</a>
                    <div class="dropdown-content">
                        <form target="/mainServlet" method="get">
                            <a href="<c:url value="/mainServlet">
                                <c:param name="command" value="show_edit_tracks_page"/>
                                 </c:url>
                            ">${editTracks}</a>
                        </form>
                        <form target="/mainServlet" method="get">
                            <a href="<c:url value="/mainServlet">
                                <c:param name="command" value="show_edit_genres_page"/>
                                 </c:url>
                            ">${editGenres}</a>
                        </form>
                        <form target="/mainServlet" method="get">
                            <a href="<c:url value="/mainServlet">
                                <c:param name="command" value="show_edit_authors_page"/>
                                 </c:url>
                            ">${editAuthors}</a>
                        </form>
                        <form target="/mainServlet" method="get">
                            <a href="<c:url value="/mainServlet">
                                <c:param name="command" value="show_edit_albums_page"/>
                                 </c:url>
                            ">${editAlbums}</a>
                        </form>
                        <form target="/mainServlet" method="get">
                            <a href="<c:url value="/mainServlet">
                                <c:param name="command" value="show_edit_mixes_page"/>
                                 </c:url>
                            ">${editMixes}</a>
                        </form>
                    </div>
                </li>
                <li class="dropdown">
                    <a href="javascript:void(0)" class="dropbtn">Admin users</a>
                    <div class="dropdown-content">
                        <a href="#">Edit users</a>
                        <a href="#">Add new user</a>
                    </div>
                </li>
                <li class="dropdown">
                    <a href="javascript:void(0)" class="dropbtn">${user.name}</a>
                    <div class="dropdown-content">
                        <a href="#">${personal}</a>
                        <form target="/mainServlet" method="get">
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
                        <form target="/mainServlet" method="post">
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
        <td width="20%">
        </td>
    </tr>
</table>
