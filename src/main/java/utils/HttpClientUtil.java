package utils;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.net.URI;

public class HttpClientUtil {
    private HttpClientUtil(){
    }

    public static HttpClientUtil httpClientUtil;

    public static synchronized HttpClientUtil getInstance(){
        return httpClientUtil == null ? new HttpClientUtil() : httpClientUtil;
    }

    //todo params
    public String httpGet(URI uri, String token, Object params) throws Exception {
        HttpGet httpGet = new HttpGet(uri);
        HttpResponse response = null;
        HttpEntity entity = null;

        httpGet.addHeader("Content-Type", "application/json");
        httpGet.addHeader("li", "zh_TW");
        httpGet.addHeader("token", token);

        CloseableHttpClient httpClient = HttpClients.createDefault();
        response = httpClient.execute(httpGet);

        if(response.getStatusLine().getStatusCode() == 200){
            entity = response.getEntity();
            return EntityUtils.toString(entity);
        }

        throw new Exception("httpClient error status : " + response.getStatusLine().getStatusCode());
    }
}
