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
import mycode.model.ChtSMSourLogModel;
import mycode.model.PropertiesModel;
import mycode.utils.PropertiesUtil;

public class SmSourLogExample {
    private static DatabaseConnectionImpl dao;
    private static PropertiesModel propertiesModel;

    static {
        propertiesModel = new PropertiesModel();
        PropertiesUtil.getInstance().getSmSourLogProperties(propertiesModel);
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
        List<ChtSMSourLogModel> models = new ArrayList<>();
        
        parseFolder(new File(propertiesModel.getFolderPath()).listFiles(), models, Pattern.compile("SMSourCHT(\\d{4})"));

//        dao.doSmSourLogDatabaseConnection(models);
        
        System.out.println(" SmSourLogExample End <<<<<<<<<<<<<<<<< ");
        sw.stop();
        System.out.println("SmSourLogExample run time :" + sw.getTime() + "ms");
    }
    
    //遞迴
    public static void parseFolder(File[] files, List<ChtSMSourLogModel> models, Pattern pattern) {
        for (File file : files) {
            //是目錄且名稱包含"." 
            if (file.exists() && file.isDirectory() && file.getName().contains(".")) {
                parseFolder(file.listFiles(), models, pattern);
            //是檔案
            } else if (file.exists() && file.isFile()) {
                Matcher matcher = pattern.matcher(file.getName());
                if (matcher.find()) {
                    try {
                        parseLog(file.getParentFile().getName(), file.getPath(), matcher.group(1), models);
                    }catch (Exception e) {
                        e.printStackTrace();
                    }                    
                }
            }
        }
    }

    public static void parseLog(String fileName, String filePath, String partKey, List<ChtSMSourLogModel> models) throws IOException {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(filePath), StandardCharsets.UTF_8))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.contains(propertiesModel.getKeyWord())) {
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
