package cz.csas.psd2.qsealsample.report;

import java.util.Map;

public class ClientLogResponse extends AbstractClientLog {

    private final int statusCode;

    public ClientLogResponse(int statusCode, String body, Map<String, String> responseHeaders) {
        super(body,responseHeaders);
        this.statusCode = statusCode;
    }

    @Override
    public String getLog(){
        return "==================  Response ==================" +
                AbstractClientLog.NEW_LINE +
                "[Status Code]" +
                AbstractClientLog.NEW_LINE +
                getStatusCode() +
                AbstractClientLog.NEW_LINE +
                AbstractClientLog.NEW_LINE +
                super.getLog() +
                AbstractClientLog.NEW_LINE;
    }

    public int getStatusCode() {
        return statusCode;
    }
}
