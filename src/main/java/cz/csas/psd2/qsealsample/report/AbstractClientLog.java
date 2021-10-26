package cz.csas.psd2.qsealsample.report;

import java.util.Map;

public abstract class AbstractClientLog {

    private final String body;
    private final Map<String, String> headers;
    protected static final String NEW_LINE = "\r\n";

    public AbstractClientLog(String body, Map<String, String> headers) {
        this.body = body;
        this.headers = headers;
    }

    public String getLog() {
        StringBuilder logOutput = new StringBuilder();
        logOutput.append("[Headers]");
        logOutput.append(NEW_LINE);
        for (String header : headers.keySet()) {
            logOutput.append(header);
            logOutput.append(": ");
            logOutput.append(headers.get(header));
            logOutput.append(NEW_LINE);
        }
        logOutput.append(NEW_LINE);
        logOutput.append("[Body]");
        logOutput.append(NEW_LINE);
        logOutput.append(body);
        return logOutput.toString();
    }

    public void printLog() {
        System.out.println(getLog());
    }
}
