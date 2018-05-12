package by.kurlovich.musicshop.tag.paging;

import by.kurlovich.musicshop.entity.Track;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.SimpleTagSupport;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GetSelectedRowsTag extends SimpleTagSupport {
    private String page;
    private List<Track> data;
    private String rows;

    public void setPage(String page) {
        this.page = page;
    }

    public void setData(List<Track> data) {
        this.data = data;
    }

    public void setRows(String rows) {
        this.rows = rows;
    }

    @Override
    public void doTag() throws JspException, IOException {
        if (page == null) {
            page = "1";
        }

        int currentPage = Integer.parseInt(page);

        int firstRow = (currentPage - 1) * 15;
        int lastRow = (currentPage * 15);

        if (lastRow > data.size()) {
            lastRow = data.size();
        }

        List<Track> selectedRows = new ArrayList<>(data.subList(firstRow, lastRow));
        getJspContext().setAttribute(rows, selectedRows);
    }
}
