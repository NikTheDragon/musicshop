package by.kurlovich.musicshop.tag.paging;

import by.kurlovich.musicshop.entity.Track;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.List;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.SkipPageException;
import javax.servlet.jsp.tagext.SimpleTagSupport;

public final class GetFirstPageNumberTag extends SimpleTagSupport {
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
        getJspContext().setAttribute(variable, "1");
    }
}
