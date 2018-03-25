package by.kurlovich.musicshop.content;

public class CommandResult {
    public enum ResponseType {
        FORWARD,
        REDIRECT
    }

    private ResponseType responseType;
    private String page;

    public CommandResult() {
    }

    public CommandResult(ResponseType responseType, String page) {
        this.responseType = responseType;
        this.page = page;
    }

    public ResponseType getResponseType() {
        return responseType;
    }

    public String getPage() {
        return page;
    }

    public void setResponseType(ResponseType responseType) {
        this.responseType = responseType;
    }
}
