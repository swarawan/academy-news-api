package id.investree.news.utilities.log;

import javax.servlet.http.HttpServletRequest;

public class LoggerModel {

    private HttpServletRequest meta;
    private Object requestBody;

    public LoggerModel(HttpServletRequest meta, Object requestBody) {
        this.meta = meta;
        this.requestBody = requestBody;
    }

    public LoggerModel(HttpServletRequest meta) {
        this.meta = meta;
    }

    public HttpServletRequest getMeta() {
        return meta;
    }

    public Object getRequestBody() {
        return requestBody;
    }
}
