package cz.csas.psd2.qsealsample;

import cz.csas.psd2.qsealsample.model.payment.Payment;
import cz.csas.psd2.qsealsample.report.ClientLog;
import cz.csas.psd2.qsealsample.sampleclient.QSealSampleClient;
import cz.csas.psd2.qsealsample.utils.PaymentUtils;

public class Main {

    /* Fill in your web-api-key from https://developers.erstegroup.com/  */
    private static final String WEB_API_KEY = "XYZ";

    /* Fill in your access token  */
    private static final String ACCESS_TOKEN = "XYZ";

    private static final String AUTHORIZATION = "Bearer " + ACCESS_TOKEN;

    public static final String REQUEST_URI = "https://webapi.developers.erstegroup.com/api/csas/production/v4/payment-initiation/my/payments";

    public static void main(String[] args) {
        QSealSampleClient<Payment> signPSD2SampleClient = new QSealSampleClient<>(WEB_API_KEY, AUTHORIZATION);

        Payment demoPaymentBody = new PaymentUtils().createDemoRequest();
        ClientLog clientLog = signPSD2SampleClient.doSampleRequest(REQUEST_URI, demoPaymentBody);

        clientLog.getRequest().printLog();
        clientLog.getResponse().printLog();
    }
}
