package cz.csas.psd2.qsealsample.report;

import java.util.Map;

public class ClientLogRequest extends AbstractClientLog {

    private final String URI;

    public ClientLogRequest(String URI, String body, Map<String, String> requestHeaders) {
        super(body,requestHeaders);
        this.URI = URI;
    }

    @Override
    public String getLog(){
        return "================== Request ==================" +
                AbstractClientLog.NEW_LINE +
                "[URI]" +
                AbstractClientLog.NEW_LINE +
                getURI() +
                AbstractClientLog.NEW_LINE +
                AbstractClientLog.NEW_LINE +
                super.getLog() +
                AbstractClientLog.NEW_LINE;
    }

    public String getURI() {
        return URI;
    }
}
