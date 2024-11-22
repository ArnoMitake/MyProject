package mycode.utils;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.security.cert.X509Certificate;
import java.util.Arrays;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

public class HttpClientUtil { //目前用 Instance 暫時手動關閉 implements Closeable
    private static HttpClientUtil httpClientUtil;

    public HttpClientUtil() {
    }

    public static synchronized HttpClientUtil getInstance() {
        return httpClientUtil == null ? new HttpClientUtil() : httpClientUtil;
    }

    public String doHttpPostJsonBody(String uri, String token, String jsonBody) throws Exception {
        StringEntity reqEntity = null;

        reqEntity = new StringEntity(StringUtils.defaultString(jsonBody), "UTF-8");
        reqEntity.setContentType("application/json");

        return doHttpPostBody(URI.create(uri), token, reqEntity);
    }

    private String doHttpPostBody(URI uri, String token, HttpEntity bodyEntity) throws Exception {
        HttpPost httpPost = null;
        HttpEntity httpEntity = null;
        HttpResponse response = null;
        CloseableHttpClient httpClient = null;
        try {
            httpPost = new HttpPost(uri);
            httpPost.addHeader("li", "zh_TW");
            httpPost.addHeader("token", token);

            httpPost.setEntity(bodyEntity);

            httpClient = HttpClients.createDefault();

            response = httpClient.execute(httpPost);

            Arrays.stream(response.getAllHeaders())
                    .forEach(System.out::println);

            if (response.getStatusLine().getStatusCode() == 200) {
                return EntityUtils.toString(response.getEntity());
            }

            throw new Exception("httpClient error status : " + response.getStatusLine().getStatusCode());

        } finally {
            httpClient.close();
        }
    }

    public String doHttpGetUrl(String uri, String token, final List<NameValuePair> params) throws Exception {
        StringBuilder sb = new StringBuilder();
        if (params != null && params.size() > 0) {
            sb.append("?");
            sb.append(URLEncodedUtils.format(params, "UTF-8"));
        }
        return doHttpGetUrl(URI.create(uri + sb), token);
    }

    private String doHttpGetUrl(URI uri, String token) throws Exception {
        HttpGet httpGet = null;
        HttpResponse response = null;
        HttpEntity entity = null;
        CloseableHttpClient httpClient = null;

        try {

            httpGet = new HttpGet(uri);

            httpGet.addHeader("Content-Type", "application/json");
            httpGet.addHeader("li", "zh_TW");
            httpGet.addHeader("token", token);

            httpClient = HttpClients.createDefault();

            response = httpClient.execute(httpGet);

            Arrays.stream(response.getAllHeaders())
                    .forEach(System.out::println);

            if (response.getStatusLine().getStatusCode() == 200) {
                entity = response.getEntity();
                return EntityUtils.toString(entity);
            }

            throw new Exception("httpClient error status : " + response.getStatusLine().getStatusCode());
        } finally {
            httpClient.close();
        }
    }
    
    public void doHttpsGetUrl(String urlString) throws Exception {
        // Disable SSL certificate validation (for testing purposes only)
        disableSSLValidation();

        // Create URL object
        URL url = new URL(urlString);
        
        HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();

        // Set request method to GET
        conn.setRequestMethod("GET");

        // Send the request and get the response code
        int responseCode = conn.getResponseCode();
        System.out.println("Response Code: " + responseCode);

        // If the request is successful (HTTP code 200)
        if (responseCode == HttpsURLConnection.HTTP_OK) {
            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String inputLine;
            StringBuilder response = new StringBuilder();

            // Read the response
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            // Print the response
            System.out.println("Response: " + response.toString());
        } else {
            System.out.println("GET request failed.");
        }
    }

    public void doHttpGetUrl(String urlString) throws Exception {
        // Create URL object
        URL url = new URL(urlString);
        
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();

        // Set request method to GET
        conn.setRequestMethod("GET");

        // Send the request and get the response code
        int responseCode = conn.getResponseCode();
        System.out.println("Response Code: " + responseCode);

        // If the request is successful (HTTP code 200)
        if (responseCode == HttpsURLConnection.HTTP_OK) {
            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String inputLine;
            StringBuilder response = new StringBuilder();

            // Read the response
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            // Print the response
            System.out.println("Response: " + response.toString());
        } else {
            System.out.println("GET request failed.");
        }
    }

    // Method to disable SSL certificate validation (for testing purposes only)
    private void disableSSLValidation() throws Exception {
        TrustManager[] trustAllCerts = new TrustManager[] {
                new X509TrustManager() {
                    public X509Certificate[] getAcceptedIssuers() { return null; }
                    public void checkClientTrusted(X509Certificate[] certs, String authType) { }
                    public void checkServerTrusted(X509Certificate[] certs, String authType) { }
                }
        };
        SSLContext sc = SSLContext.getInstance("SSL");
        sc.init(null, trustAllCerts, new java.security.SecureRandom());
        HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
        HttpsURLConnection.setDefaultHostnameVerifier((hostname, session) -> true);
    }
}
