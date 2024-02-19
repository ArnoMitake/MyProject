import com.google.gson.Gson;
import java.net.URI;

import model.CoLineApiModel;
import utils.HttpClientUtil;

public class CoLineExample {
    public static void main(String[] args) throws Exception {
        //JwtUtil.getInstance().

        String response = HttpClientUtil.getInstance().httpGet(new URI("https://eim.mitake.com.tw/v3/open/users"), "509140f5-92b1-43b2-8d60-b5544c385ef3", null);
        System.out.println("body: " + parserJson(response));

    }

    public static CoLineApiModel parserJson(String body) throws Exception {
        return new Gson().fromJson(body, CoLineApiModel.class);
    }
}

