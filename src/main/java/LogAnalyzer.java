import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class LogAnalyzer {

    private final static String folderPath = "D:\\Program Files\\SourceTree\\Java_Project\\Github\\MyProject\\for_LogAnalyzer_demo\\smpp_nginx_log\\smpp_nginx\\123.16";

    public static void main(String[] args) {
        File folder = new File(folderPath);
        File[] files = folder.listFiles();

        //ip 用的演算法 count

        Map<String, Map<String, Integer>> ipTLSCountMap = new HashMap<>(); // 使用Map存放IP與TLS以及對應的數量

        if (files != null) {
            for (File file : files) {
                if (file.isFile() && file.getName().endsWith(".log")) {
                    processLogFile(file, ipTLSCountMap);
                }
            }
        } else {
            System.err.println("指定的文件夾不存在或為空。");
        }

        // 輸出IP和對應的TLS類型
        System.out.println("IP和對應的TLS類型:");
        for (Map.Entry<String, Map<String, Integer>> entry : ipTLSCountMap.entrySet()) {
            System.out.println("IP: " + entry.getKey());
            System.out.println("TLS類型及數量: " + entry.getValue());
            System.out.println();
        }
    }

    private static void processLogFile(File file, Map<String, Map<String, Integer>> ipTLSCountMap) {
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;

            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(" ");

                // 提取IP地址
                String ip = parts[0].split(":")[0];

                // 提取TLS類型
                String tlsType = parts[parts.length - 1];
                if (!tlsType.equals("(NONE)")) {
                    // 將TLS類型及對應的數量添加到相應的IP鍵下
                    Map<String, Integer> tlsCountMap = ipTLSCountMap.computeIfAbsent(ip, k -> new HashMap<>());
                    tlsCountMap.put(tlsType, tlsCountMap.getOrDefault(tlsType, 0) + 1);
                }
            }
        } catch (IOException e) {
            System.err.println("處理日誌文件時出錯: " + e.getMessage());
        }
    }

//    public static void test(boolean flag) {
//        if (!flag) return;
//        try {
//            BufferedReader reader = new BufferedReader(new FileReader(fileName));
//            String line;
//            Map<String, Set<String>> ipTLSMap = new HashMap<>(); // 使用Map存放IP與TLS的對應關係
//
//            while ((line = reader.readLine()) != null) {
//                String[] parts = line.split(" ");
//
//                // 提取IP地址
//                String ip = parts[0].split(":")[0];
//
//                // 提取TLS類型
//                String tlsType = parts[parts.length - 1];
//                if (!tlsType.equals("(NONE)")) {
//                    // 將TLS類型添加到相應的IP鍵下
//                    ipTLSMap.computeIfAbsent(ip, k -> new HashSet<>()).add(tlsType);
//                }
//            }
//
//            // 輸出IP和對應的TLS類型
//            System.out.println("IP和對應的TLS類型:");
//            for (Map.Entry<String, Set<String>> entry : ipTLSMap.entrySet()) {
//                System.out.println("IP: " + entry.getKey());
//                System.out.println("TLS類型: " + entry.getValue());
//                System.out.println();
//            }
//
//            reader.close();
//        } catch (IOException e) {
//            System.err.println("讀取日誌文件時出錯: " + e.getMessage());
//        }
//    }
}
