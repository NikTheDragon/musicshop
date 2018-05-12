package by.kurlovich.musicshop.tag.paging;

import by.kurlovich.musicshop.entity.Track;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.SimpleTagSupport;
import java.io.IOException;
import java.util.List;

public class GetLastPageNumberTag extends SimpleTagSupport {
    private String variable;
    private List<Track> data;

    public void setVariable(String variable) {
        this.variable = variable;
    }

    public void setData(List<Track> data) {
        this.data = data;
    }

    @Override
    public void doTag() throws JspException, IOException {
        getJspContext().setAttribute(variable, String.valueOf(data.size() / 15 + 1));
    }
}
