import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.net.URI;

import utils.HttpClientUtil;

public class CoLineExample {
    public static void main(String[] args) throws Exception {
        //JwtUtil.getInstance().

        String response = HttpClientUtil.getInstance().httpGet(new URI("https://eim.mitake.com.tw/v3/open/users"), "509140f5-92b1-43b2-8d60-b5544c385ef3", null);
        System.out.println(response);

    }

    public static void httpPost() throws Exception {
        URI uri = new URI("https://eim.mitake.com.tw/v3/open/users");
//        URI uri = new URI("https://eim.mitake.com.tw/v3/open/users?key=arno@mitake.com.tw");
        HttpGet httpGet = new HttpGet(uri);
        HttpResponse response = null;
        HttpEntity entity = null;

        httpGet.addHeader("Content-Type", "application/json");
        httpGet.addHeader("li", "zh_TW");
        httpGet.addHeader("token", "509140f5-92b1-43b2-8d60-b5544c385ef3");

        CloseableHttpClient httpClient = HttpClients.createDefault();
        response = httpClient.execute(httpGet);

        if (response.getStatusLine().getStatusCode() == 200) {
            entity = response.getEntity();
            String jsonString = EntityUtils.toString(entity);
            System.out.println(entity);
            System.out.println(jsonString);
            System.out.println(">>>>>>>>>>>>>>>>");

            JsonParser parser = new JsonParser();
            JsonObject json = (JsonObject) parser.parse(jsonString);
            JsonArray array = ((JsonObject)json.get("data")).getAsJsonArray("users");
            for (JsonElement element : array) {
                JsonObject object = element.getAsJsonObject();
                System.out.println(object.get("employee_no"));
            }
        }
    }
}
