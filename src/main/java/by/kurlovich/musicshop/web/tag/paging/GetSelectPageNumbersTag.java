package by.kurlovich.musicshop.web.tag.paging;

import by.kurlovich.musicshop.entity.Track;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.SimpleTagSupport;
import java.io.IOException;
import java.util.List;

public class GetSelectPageNumbersTag extends SimpleTagSupport {
    private String first;
    private String last;
    private String page;
    private List<Track> data;

    public void setFirst(String first) {
        this.first = first;
    }

    public void setLast(String last) {
        this.last = last;
    }

    public void setPage(String page) {
        this.page = page;
    }

    public void setData(List<Track> data) {
        this.data = data;
    }

    @Override
    public void doTag() throws JspException, IOException {
        int currentPage = Integer.parseInt(page);
        int totalPages = data.size() / 15 + 1;

        int firstActivePage = currentPage - 10;
        int lastActivePage = currentPage + 10;

        if (firstActivePage < 1) {
            lastActivePage = lastActivePage - firstActivePage;
            firstActivePage = 1;
        }

        if (lastActivePage > totalPages) {
            firstActivePage = firstActivePage - (lastActivePage - totalPages);
            lastActivePage = totalPages;
        }

        if (firstActivePage < 1) {
            firstActivePage = 1;
        }

        if (lastActivePage > totalPages) {
            lastActivePage = totalPages;
        }

        getJspContext().setAttribute(first, String.valueOf(firstActivePage));
        getJspContext().setAttribute(last, String.valueOf(lastActivePage));
    }
}
