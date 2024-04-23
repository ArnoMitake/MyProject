package utils;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.Header;
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

import java.net.URI;
import java.util.Arrays;
import java.util.List;

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
}
