package LogTools;

import org.apache.commons.lang3.time.StopWatch;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import dao.DatabaseConnection;
import model.ChtSMSourLogModel;

public class SmSourLogExample {
    private static String keyWord;
    private static String folderPath;
    private static String DB_ip;
    private static String DB_port;
    private static String DB_dbname;
    private static String DB_user;
    private static String DB_num;

    static {
        Properties prop = new Properties();
        try (FileInputStream input = new FileInputStream("conf/AppSettings.properties");
             InputStreamReader reader = new InputStreamReader(input, StandardCharsets.UTF_8)) {
            prop.load(reader);
            keyWord = prop.getProperty("keyWord");
            folderPath = prop.getProperty("folderPath");
            DB_ip = prop.getProperty("DB_ip");
            DB_port = prop.getProperty("DB_port");
            DB_dbname = prop.getProperty("DB_dbname");
            DB_user = prop.getProperty("DB_user");
            DB_num = prop.getProperty("DB_num");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws FileNotFoundException {
        DatabaseConnection conn = new DatabaseConnection(DB_ip, DB_port, DB_dbname, DB_user, DB_num);
        StopWatch sw = new StopWatch();
        sw.start();
        System.out.println(" SmSourLogExample Start >>>>>>>>>>>>>>>> ");
        List<ChtSMSourLogModel> models = new ArrayList<>();
//        parseFolder(new File(folderPath), models);
        conn.doDatabaseConnection(DB_ip, DB_port, DB_dbname, DB_user, DB_num, models);
        System.out.println(" SmSourLogExample End <<<<<<<<<<<<<<<<< ");
        sw.stop();
        System.out.println("SmSourLogExample run time :" + sw.getTime() + "ms");
    }

    public static void parseFolder(File folder, List<ChtSMSourLogModel> models) {
        Pattern pattern = Pattern.compile("SMSourCHT(\\d{4})");
        try {
            for (File parent : folder.listFiles()) {
                if (parent.exists() && parent.getName().contains(".")) {
                    for (File child : parent.listFiles()) {
                        Matcher matcher = pattern.matcher(child.getName());
                        if (matcher.find()) {
                            parseLog(parent.getName(), child.getPath(), matcher.group(1), models);
                        }
                    }
                }
            }
        } catch (IOException e) {
            System.out.println(e);
            e.printStackTrace();
        }
    }

    public static void parseLog(String fileName, String filePath, String partKey, List<ChtSMSourLogModel> models) throws IOException {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(filePath), StandardCharsets.UTF_8))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.contains(keyWord)) {
                    ChtSMSourLogModel model = new ChtSMSourLogModel();
                    String[] parts = line.split("\\|");
                    String date = parts[0].split(" ")[0];
                    String time = parts[0].split(" ")[1];
                    String mid = parts[0].split("=")[1];
                    String sessionId = parts[1].split("=")[1];
                    model.setDate(date);
                    model.setTime(time);
                    model.setIp(fileName);
                    model.setPartKey(partKey);
                    model.setMid(mid);
                    model.setSessionId(sessionId);
                    models.add(model);
                }
            }
        }
    }
}
