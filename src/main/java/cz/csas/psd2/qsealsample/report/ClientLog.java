package cz.csas.psd2.qsealsample.report;

public class ClientLog {

    private final ClientLogResponse response;
    private final ClientLogRequest request;

    public ClientLog(ClientLogRequest request, ClientLogResponse response){
        this.response = response;
        this.request = request;
    }

    public ClientLogRequest getRequest() {
        return request;
    }

    public ClientLogResponse getResponse() {
        return response;
    }
}
