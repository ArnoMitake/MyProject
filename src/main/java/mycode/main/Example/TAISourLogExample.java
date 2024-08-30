package mycode.main.Example;

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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import mycode.dao.DatabaseConnection;
import mycode.dao.DatabaseConnectionFactory;
import mycode.dao.Impl.DatabaseConnectionImpl;
import mycode.model.PropertiesModel;
import mycode.model.TaiSMSourLogModel;
import mycode.utils.PropertiesUtil;

public class TAISourLogExample {
    private static String regex = "SMSourTAI(\\d{4})";
    private static DatabaseConnectionImpl dao;
    private static PropertiesModel propertiesModel;

    static {
        propertiesModel = new PropertiesModel();
        PropertiesUtil.getInstance().getTAISourLogProperties(propertiesModel);
        PropertiesUtil.getInstance().getDBLOGProperties(propertiesModel);
        System.out.println(String.format("[PropertiesModel:%s]", propertiesModel));
        dao = new DatabaseConnection();
        dao.setDatabaseConnectionFactory(
                new DatabaseConnectionFactory(
                        propertiesModel.getDb_ip(), propertiesModel.getDb_port(), propertiesModel.getDb_dbname(),
                        propertiesModel.getDb_user(), propertiesModel.getDb_num()));
    }

    public static void main(String[] args) throws FileNotFoundException {
        StopWatch sw = new StopWatch();
        sw.start();
        System.out.println(" SmSourLogExample Start >>>>>>>>>>>>>>>> ");
        List<TaiSMSourLogModel> models = new ArrayList<>();
        
        parseFolder(new File(propertiesModel.getFolderPath()), models);
        
        dao.doTaiDatabaseConnection(models);
        
        System.out.println(" SmSourLogExample End <<<<<<<<<<<<<<<<< ");
        sw.stop();
        System.out.println("SmSourLogExample run time :" + sw.getTime() + "ms");
    }

    public static void parseFolder(File folder, List<TaiSMSourLogModel> models) {
        Pattern pattern = Pattern.compile(regex);//匹配檔案名稱
        try {
            for (File parent : folder.listFiles()) {
                if (parent.exists() && parent.getName().contains(".")) {//找帶有.的資料夾
                    for (File child : parent.listFiles()) {
                        Matcher matcher = pattern.matcher(child.getName());
                        if (matcher.find()) {
                            parseLog(parent.getName(), child.getPath(), matcher.group(1), models);
                            System.out.println("Folder : " + parent.getName() + " LogName: " + child.getName() + ": 總數: " + models.size());
                        }
                    }
                }
            }
        } catch (IOException e) {
            System.out.println(e);
            e.printStackTrace();
        }
    }

    public static void parseLog(String fileName, String filePath, String partKey, List<TaiSMSourLogModel> models) throws IOException {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(filePath), StandardCharsets.UTF_8))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.contains(propertiesModel.getKeyWord())) {
                    TaiSMSourLogModel model = new TaiSMSourLogModel();
                    String[] parts = line.split("\\|");
                    String date = parts[0].split(" ")[0];
                    String time = parts[0].split(" ")[1].split("\\.")[0];
                    String threadID = parts[0].split("#")[1].split("]")[0];
                    String serialNo = parts[0].split("=")[1];
                    String mid = parts[1].split("=")[1].split("\\uFF0C")[0];// \uFF0C = ， 全形逗號
                    String executionTime = parts[1].split("=")[2].split(";")[0];
                    model.setDate(date);
                    model.setTime(time);
                    model.setThreadID(threadID);
                    model.setSerialNo(serialNo);
                    model.setMid(mid);
                    model.setExecutionTime(executionTime);
                    models.add(model);
                }
            }
        }
    }
}
