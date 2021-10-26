package cz.csas.psd2.qsealsample.utils;

import org.apache.http.Header;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Map;
import java.util.TreeMap;

public class QSealSampleUtils {

    public static String transformResponseBody(InputStream inputStream) {
        StringBuilder responseBody = new StringBuilder();
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            String inputLine;
            while ((inputLine = reader.readLine()) != null) {
                responseBody.append(inputLine);
            }
            reader.close();
        } catch (IOException ignored) {}
        return responseBody.toString();
    }

    public static Map<String, String> transformHeaders(Header[] headers) {
        Map<String, String> transformedHeaders = new TreeMap<>();
        for (Header header : headers) {
            transformedHeaders.put(header.getName(), header.getValue());
        }
        return transformedHeaders;
    }
}
