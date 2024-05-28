package LogTools;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.commons.lang3.time.StopWatch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Dear 舜基
 * 請協助由DB統計三站4月份，
 * <p>
 * 新API發送，
 * StatusFlag=6，
 * DestNo格式為8868869xxxxxxxx、+88609xxxxxxxx、88609xxxxxxxx
 * <p>
 * 的客戶數量，
 * <p>
 * 統計出來的欄位請包含 帳號、部門、客戶名稱 及 筆數。
 * <p>
 * Dear Arno
 * 請協助統計三站4月份新API AP Log，
 * <p>
 * statuscode=v，
 * DestNo開頭為09或8869
 * 且包含符號(-、.、空白)
 * <p>
 * 的客戶數量，
 * <p>
 * 統計出來的欄位請包含 帳號、部門、客戶名稱 及 筆數。
 * <p>
 * Log請維運幫你抓
 */
//reference MikateLog folder
public class MitakeLogTools {
    private final static Logger logger = LoggerFactory.getLogger(MitakeLogTools.class);
    private static Pattern PATTERN_FILTER_FILE = Pattern.compile("\\.zip");//排除.zip檔
    private static Pattern PATTERN_KEYWORD = Pattern.compile("username=(.*?),|dstaddr=(.*?),");//取出關鍵字
    private static final String FOLDER_PATH = "D:\\Program Files\\SourceTree\\Java_Project\\Github\\MyProject\\MitakeLog\\SMSMitakeLmAPI_Log_202404";//指定目錄
    private static Set<String> FILE_PATHS;//紀錄檔案位置
    private static Map<String, Set<String>> ALL_FILE_PATH_MAP = new HashMap<>();//紀錄某個目錄、檔案位置
    private static Map<String, Integer> USERNAME_COUNT_MAP_FOR_09 = new ConcurrentHashMap<>();//紀錄帳號與數量，ConcurrentHashMap防止併發
    private static Map<String, Integer> USERNAME_COUNT_MAP_FOR_8869 = new ConcurrentHashMap<>();//紀錄帳號與數量，ConcurrentHashMap防止併發
    private static Map<String, Long> TIME_MAP = new HashMap<>();//統計每個Method執行時間
    private static List<Pattern> PATTERN_LIST = Arrays.asList(
            Pattern.compile("^(\\+?09|\\+?8869)(\\d{2}|\\d{1})\\-\\d{3}\\-\\d{3}$"),
            Pattern.compile("^(\\+?09|\\+?8869)(\\d{2}|\\d{1})\\ \\d{3}\\ \\d{3}$"),
            Pattern.compile("^(\\+?09|\\+?8869)(\\d{2}|\\d{1})\\.\\d{3}\\.\\d{3}$")
    );//正規手機匹配

    /**
     * 先保存帳號
     * 檢查以下條件
     * statuscode=v，
     * DestNo開頭為09或8869
     * 且包含符號(-、.、空白)
     * 符合
     * 紀錄帳號 跟 數量
     * 找 sms2 對應的帳號 紀錄 部門 客戶名稱
     */
    public static void mains(String[] args) throws FileNotFoundException {
        StopWatch sw = new StopWatch();

        if ("".equals(""))
            return;

        sw.start();
        parserFolder();
        sw.stop();
        TIME_MAP.put("parserFolder", sw.getTime());
        sw.reset();
        //ALL_FILE_PATH_MAP.entrySet().forEach(k -> System.out.println(String.format("資料夾:%s, 檔案路徑:%s", k.getKey(), k.getValue())));

        sw.start();
        parserLog();
        sw.stop();
        TIME_MAP.put("parserLog", sw.getTime());
        sw.reset();

        sw.start();
        printResult();
        sw.stop();
        TIME_MAP.put("printResult", sw.getTime());
        sw.reset();

        TIME_MAP.entrySet()
                .forEach(k -> System.out.println(String.format("方法:%s, 耗時:%sms", k.getKey(), k.getValue())));

    }

    //todo 臨時塞資料用
    public static void main(String[] args) throws FileNotFoundException {
        setData();
    }

    public static void printResult() {
        logger.info(" printResult start 09 >>>>>>>>>>> ");
        USERNAME_COUNT_MAP_FOR_09.entrySet()
                .forEach(k -> System.out.println(String.format("帳號:%s, 數量:%s", k.getKey(), k.getValue())));
        logger.info(" printResult end 09 <<<<<<<<<<< ");

        logger.info(" printResult start 8869 >>>>>>>>>>> ");
        USERNAME_COUNT_MAP_FOR_8869.entrySet()
                .forEach(k -> System.out.println(String.format("帳號:%s, 數量:%s", k.getKey(), k.getValue())));
        logger.info(" printResult end 8869 <<<<<<<<<<< ");
    }

    public static void parserLog() {
        System.out.println(" parserLog start >>>>>>>>>>> ");
        String line = null;
        Matcher matcher = null;

        for (Map.Entry<String, Set<String>> map : ALL_FILE_PATH_MAP.entrySet()) {
            for (String filePath : map.getValue()) {
//                System.out.println(String.format("當前目錄為:%s, 當前檔案為:%s", map.getKey(), filePath));

                //讀取檔案，解析Log
                try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(filePath), StandardCharsets.UTF_8))) {
                    while ((line = br.readLine()) != null) {
                        if (line.contains("Error=無效的手機號碼")) {
                            matcher = PATTERN_KEYWORD.matcher(line);
                            String userName = null;
                            String dstaddr = null;
                            while (matcher.find()) {
                                if (matcher.group().contains("username")) {
                                    userName = matcher.group(1);
                                } else if (matcher.group().contains("dstaddr")) {
                                    dstaddr = StringUtils.strip(matcher.group(2));
                                }
                            }
                            if (userName != null && dstaddr != null) {
                                if (dstaddr.startsWith("09") || dstaddr.startsWith("8869")) {
                                    String dstaddrReplace = dstaddr.replace("-", "").replace(" ", "").replace(".", "");
                                    if (NumberUtils.isDigits(dstaddrReplace) && dstaddr.startsWith("09") && dstaddrReplace.length() == 10) {
                                        synchronized (USERNAME_COUNT_MAP_FOR_09) {//統計帳號
                                            System.out.println(userName + " : " + dstaddr);
                                            USERNAME_COUNT_MAP_FOR_09.put(userName, USERNAME_COUNT_MAP_FOR_09.getOrDefault(userName, 0) + 1);
                                        }
                                    } else if (NumberUtils.isDigits(dstaddrReplace) && dstaddr.startsWith("8869") && dstaddrReplace.length() == 12) {
                                        synchronized (USERNAME_COUNT_MAP_FOR_8869) {//統計帳號
                                            System.out.println(userName + " : " + dstaddr);
                                            USERNAME_COUNT_MAP_FOR_8869.put(userName, USERNAME_COUNT_MAP_FOR_8869.getOrDefault(userName, 0) + 1);
                                        }
                                    }
                                }

//                                for (Pattern pattern : PATTERN_LIST) {
//                                    matcher = pattern.matcher(dstaddr);
//                                    if (matcher.find()) {
//                                        System.out.println(userName + " : " + dstaddr);
//                                        if (dstaddr.startsWith("09")) {
//                                            synchronized (USERNAME_COUNT_MAP_FOR_09) {//統計帳號
//                                                USERNAME_COUNT_MAP_FOR_09.put(userName, USERNAME_COUNT_MAP_FOR_09.getOrDefault(userName, 0) + 1);
//                                            }
//                                        } else if (dstaddr.startsWith("8869")) {
//                                            synchronized (USERNAME_COUNT_MAP_FOR_8869) {//統計帳號
//                                                USERNAME_COUNT_MAP_FOR_8869.put(userName, USERNAME_COUNT_MAP_FOR_8869.getOrDefault(userName, 0) + 1);
//                                            }
//                                        }
//                                    }
//                                }
                            }
                        }
                    }
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        System.out.println(" parserLog end <<<<<<<<<<< ");
    }

    public static void parserFolder() {
        System.out.println(" parserFolder start >>>>>>>>>>> ");
        Matcher matcher = null;
        File filePath = new File(FOLDER_PATH);

        for (File parent : filePath.listFiles()) {
            if (parent.exists()) {
                FILE_PATHS = new LinkedHashSet<>();
                for (File child : parent.listFiles()) {
                    matcher = PATTERN_FILTER_FILE.matcher(child.getName());
                    if (!matcher.find()) {
                        FILE_PATHS.add(child.getPath());
                    }
                }
                ALL_FILE_PATH_MAP.put(parent.getName(), FILE_PATHS);
            }
        }
        System.out.println(" parserFolder end <<<<<<<<<<< ");
    }


    public static Map<String, String> map = new HashMap<>();//for setData()

    public static void setData() throws FileNotFoundException {
        String[] filePath = new String[]{"D:\\Program Files\\SourceTree\\Java_Project\\Github\\MyProject\\MitakeLog\\test.txt",
                "D:\\Program Files\\SourceTree\\Java_Project\\Github\\MyProject\\MitakeLog\\帳號清單資料.csv"};
        Matcher matcher = null;
        Pattern pattern = Pattern.compile("帳號:(.*?),|數量:(\\d+)");

        for (String file : filePath) {
            try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8))) {
                String line = null;
                if (file.equals(filePath[0])) {
                    while ((line = br.readLine()) != null) {
                        matcher = pattern.matcher(line);
                        String userName = null;
                        String count = null;
                        while (matcher.find()) {
                            if (matcher.group().contains("帳號")) {
                                userName = matcher.group(1);
                            } else if (matcher.group().contains("數量")) {
                                count = matcher.group(2);
                            }
                        }
                        map.put(userName, count);
                    }
                } else if (file.equals(filePath[1])) {
                    while ((line = br.readLine()) != null) {
                        String[] data = line.split(",");
                        if (map.get(data[1]) != null) {
                            System.out.println(line + "," + map.get(data[1]));
                        }
                    }
                }
//                map.entrySet()
//                        .forEach(k -> System.out.println(k.getKey() + "," + k.getValue()));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
