package CamelExample.Client;

import org.apache.commons.lang3.StringUtils;

import java.text.MessageFormat;
import java.util.Arrays;
import java.util.List;

import mycode.utils.HttpClientUtil;

public class ClientAppMain {
//	static String url = "https://10.99.0.38:7416/SmSend?username={0}&password={1}&dstaddr={2}&CharsetURL={3}&smbody={4}";
//    static String username = "arno";
//    static String password = "12345";
    static String dstaddr;
    static String CharsetURL = "utf8";
    static String smbody;
    
    //STG
//    static String url = "https://stgsmsapi.mitake.com.tw:8102/api/mtk/SmSend?username={0}&password={1}&dstaddr={2}&CharsetURL={3}&smbody={4}";
    static String url = "https://10.3.2.116:6667/SmSend?dstaddr=0900000000&smbody=20241118_test&username=MKT&password=12345&CharsetURL=UTF8";
    static String username = "Arno";
    static String password = "ArnoABCD";
    
    public static void main(String[] args) {
        try {
            String urlString = null;
            //測試國際門號，第三碼替換
            //澳大利亞國碼一共11碼，61 + XYYYYYYYY，X = 1, 4
            String destNo = "91{0}456789012";
            String content = "第{0}次，印度國碼測試";
            for (int i=0; i<1; i++) {
                dstaddr = MessageFormat.format(destNo, i);
                smbody = MessageFormat.format(content, i);
                
                urlString = MessageFormat.format(url, username, password, dstaddr, CharsetURL, smbody);
                HttpClientUtil.getInstance().doHttpsGetUrl(urlString);
//                HttpClientUtil.getInstance().doHttpGetUrl(urlString);
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
