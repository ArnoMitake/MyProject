package mycode.main.Test;

import org.apache.commons.lang3.StringUtils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GetDomainFromSMSMainTest {
    private static final String REGEXPATTERN_STR = "(?:https?:\\/\\/|(?:www\\.))?(?:[a-zA-Z0-9@%.\\-]{1,256}\\.[a-zA-Z]{2,6}|(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?))(?:[-a-zA-Z0-9@:%_+.~#?&\\\\/=\\-]*)";
    private static final Pattern URL_PATTERN = Pattern.compile(REGEXPATTERN_STR);

    public static void main(String[] args) {

        System.out.println("Start!");

        String fileName = "C:\\Users\\chris.chiu\\Desktop\\新增資料夾 (3)\\新莊遠傳20241024.txt";
        String outputName = "C:\\Users\\chris.chiu\\Desktop\\新增資料夾 (3)\\新莊遠傳20241024output.txt";

        try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(fileName), "UTF-8"));
             BufferedWriter bw = new BufferedWriter(
                     new OutputStreamWriter(new FileOutputStream(new File(outputName)), "UTF-8"), 524288)) {
            String line = null;

            while ((line = br.readLine()) != null) {
                if (StringUtils.isNotBlank(line)) {
                    String sms = line.split("	")[3];

                    List<String> list = getHostFromSMS(sms);

                    if (list.size() > 0) {
                        String host = String.join(",", list);
                        bw.write(StringUtils.stripEnd(line, null) + "	" + host);
                        bw.newLine();
                    }else {
                        bw.write(StringUtils.stripEnd(line, null) + "	");
                        bw.newLine();
                    }
                    bw.flush();
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Finish!");
    }

    public static List<String> getHostFromSMS(String sms) {
        List<String> list = new ArrayList<>();
        Matcher matcher = URL_PATTERN.matcher(sms);

        // 使用 while 循環來查找所有匹配的URL
        while (matcher.find()) {
            URI uri = link2URI(matcher.group());

            if(uri != null)
                list.add(getCorrectHost(link2URI(matcher.group())));
        }

        return list;
    }

    public static String getCorrectHost(URI uri) {
        if(uri.getHost() == null)
            return "";
        else
            return uri.getHost();
    }

    public static URI link2URI(String link) {
        URI uri = null;
        try {
            if (!link.contains("http") && !link.contains("https")) {
                link = "http://" + link;
            }
            uri = new URI(link);
        }
        // if any error occurs
        catch (URISyntaxException e) {
            // display the error
        }
        return uri;

    }
}
