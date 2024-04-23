import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.List;

import model.CoLineApiModel;
import utils.HttpClientUtil;
import utils.JwtUtil;

/**
 * 1. 排程 -> 每天更新一次 CoLine 取得成員資訊 Table (insert or update)
 * todo 可考慮先 truncate 再 insert (if truncate error can rollback)
 * 2. 主動 call CoLine 建立聊天室資訊 -> OTP發送群組
 * 3. 告警 -> WebHook (type : MESSAGE)-> 解析jwt -> 驗證ip -> parser resp -> mapping 成員資訊 Table -> 取文字訊息(判斷哪種請求) -> 企業邏輯(找出請求所需的資訊) -> 回覆 CoLine api  -> 檢查回覆狀態 == 200
 *
 * 群組 chatroom_Id ??
 */
public class CoLineExample {
    private static CoLineExample coLineExample;

    private CoLineExample() {
    }

    public static synchronized CoLineExample getInstance() {
        return coLineExample == null ? new CoLineExample() : coLineExample;
    }

    public static void main(String[] args) throws Exception {
        System.out.println("取得成員資訊測試開始");
        CoLineExample.getInstance().getCoLineApi(null);
        System.out.println("取得成員資訊測試結束");

        System.out.println("建立聊天室訊息測試開始");
        CoLineExample.getInstance().sendCoLineApi();
        System.out.println("建立聊天室訊息測試結束");

        System.out.println("接收 WebHook JWT 驗證測試開始");
        CoLineExample.getInstance().checkWebhookJwt();
        System.out.println("接收 WebHook JWT 驗證測試結束");

    }

    public void sendCoLineApi() throws Exception {

        String uri = "https://eim.mitake.com.tw/v3/open/messages";
        String token = "509140f5-92b1-43b2-8d60-b5544c385ef3";

        String response = HttpClientUtil.getInstance().doHttpPostJsonBody(uri, token, CoLineExample.getInstance().doFakeJsonBody());
        System.out.println("response: " + parserJson(response));
    }

    public void getCoLineApi(List<NameValuePair> params) throws Exception {
        params = new ArrayList<>();
        params.add(0, new BasicNameValuePair("key", "arno@mitake.com.tw"));
//        params.add(1, new BasicNameValuePair("next", ""));
//        params.add(2, new BasicNameValuePair("branch_id", ""));

        String uri = "https://eim.mitake.com.tw/v3/open/users";
//        String token = "509140f5-92b1-43b2-8d60-b5544c385ef3";//測試用
        String token = "843fde28-b8a1-4639-8160-17cd20e89a46";//正式三竹群組

        String response = HttpClientUtil.getInstance().doHttpGetUrl(uri, token, params);
//        CoLineApiModel model = parserJson(response);
        ObjectMapper om = new ObjectMapper();

        CoLineApiModel model = om.convertValue(response, CoLineApiModel.class);
        System.out.println("response: " + model);
        System.out.println("response: " + model.data.users.size());
        if (model.data.next != null) {
            params = new ArrayList<>();
            params.add(0, new BasicNameValuePair("next", model.data.next));
            getCoLineApi(params);
        }

    }

    public void checkWebhookJwt() {
        JwtUtil.main(null);
        //JwtUtil.getInstance().parseJWT("");
    }

    private CoLineApiModel parserJson(String body) throws Exception {
        return new Gson().fromJson(body, CoLineApiModel.class);
    }

    private String doFakeJsonBody() throws Exception {
        JsonObject json = new JsonObject();
        JsonObject body = new JsonObject();
        body.addProperty("type", "text");
        body.addProperty("content", "test");
        json.addProperty("user_id", "5b9d45bc-5926-48fa-8ac3-5f089a5f3ff4");
        json.add("message", body);

        System.out.println("FakeJsonBody : " + json);
        return json.toString();
    }
}

