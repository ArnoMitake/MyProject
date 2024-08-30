package mycode.main.LogTools;

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

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.StopWatch;

import mycode.dao.DatabaseConnection;
import mycode.model.SoftwareInfoModel;

/**
 * 資料來源參考: 主機上安裝軟體清查
 * 請協助在34開一個Table將下列各主機軟體清單匯入，
 * 軟體清單內容有兩種格式，一種是windows，一種是Ubuntu，
 *     Table所需欄位如下：
 *         主機IP：檔名開頭就是主機IP
 *         軟體名稱：無論哪種格式檔案都抓Name欄位
 *         軟體版本：無論哪種格式檔案都抓Version欄位
 *         軟體說明：只有Ubuntu有此欄位，windows請忽略
 *      
 * 開發事項:
 * 目前開在 .34 DBexp -> SoftwareInfo
 * 用檔名區分 OS {list:windows, dpkg:ubuntu}
 * 資料用正規 \s{2,} 區分，已兩個空白切字串
 */
public class SoftwareInfoLogExample {
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
//            keyWord = prop.getProperty("safeware_keyWord");
            folderPath = prop.getProperty("safeware_folderPath");
            DB_ip = prop.getProperty("safeware_DBexp_DB_ip");
            DB_port = prop.getProperty("safeware_DBexp_DB_port");
            DB_dbname = prop.getProperty("safeware_DBexp_DB_dbname");
            DB_user = prop.getProperty("safeware_DBexp_DB_user");
            DB_num = prop.getProperty("safeware_DBexp_DB_num");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws FileNotFoundException, IOException {
        DatabaseConnection dao = new DatabaseConnection(DB_ip, DB_port, DB_dbname, DB_user, DB_num);
        File file = new File(folderPath);
        List<File> filePaths = new ArrayList<>();
        List<SoftwareInfoModel> softwareInfoModels = new ArrayList<>();

        StopWatch sw = new StopWatch();
        sw.start();
        System.out.println(" SoftwareInfoLogExample Start >>>>>>>>>>>>>>>> ");
        
        getLogPath(file, filePaths);        

        checkFileNameToParse(filePaths, softwareInfoModels);
        
        dao.batchInsertSoftwareInfoData(softwareInfoModels);
        
        System.out.println(" SoftwareInfoLogExample End <<<<<<<<<<<<<<<<< ");
        sw.stop();
        System.out.println("SoftwareInfoLogExample run time :" + sw.getTime() + "ms");        
    }
    
    public static void getLogPath(File folder, List<File> filePaths) {
        for(File file : folder.listFiles()) {
            filePaths.add(file);
        }
    }
    
    public static void checkFileNameToParse(List<File> filePaths, List<SoftwareInfoModel> softwareInfoModels) throws IOException {
        for (File file : filePaths) {
            System.out.println(file.getName());
            if (file.getName().contains("list")) {                
                parseTxt(file.getName().split("_list")[0], file, softwareInfoModels);
            } else if (file.getName().contains("dpkg")) {                
                parseLog(file.getName().split("_dpkg")[0], file, softwareInfoModels);
            } else {
                System.out.println("檔案無法解析: " + file.getName());
            }
        }
    }
    
    public static void parseTxt(String ip, File filePath, List<SoftwareInfoModel> softwareInfoModels) throws IOException {
        SoftwareInfoModel model = null;
        try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(filePath), StandardCharsets.UTF_16LE))) {
            int lineNumber = 2;
            br.readLine();//跳過第一行
            
            String line;
            while ((line = br.readLine()) != null) {
                try {
                    if (StringUtils.trimToNull(line) != null) {
                        String[] splits = line.split("\\s{2,}");
                        model = new SoftwareInfoModel();
                        model.setIp(ip);
                        model.setName(splits[0].trim());
                        model.setVersion(splits[1].trim());
                        softwareInfoModels.add(model);
                    } else {
                        System.out.println(String.format("[line:%s] is null", line));
                    }
                }catch (Exception e) {
                    System.out.println(String.format("第%s行 >> [line:%s]-error:%s",lineNumber,  line, e.getMessage()));
                    e.printStackTrace();
                } finally {
                    lineNumber++;
                }
            }
        }
    }

    public static void parseLog(String ip, File filePath, List<SoftwareInfoModel> softwareInfoModels) throws IOException {
        SoftwareInfoModel model = null;
        try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(filePath), StandardCharsets.UTF_8))) {
            int lineNumber = 6;
            for (int i = 0; i < 5; i++) {
                br.readLine(); // 跳過前五行
            }

            String line;
            while ((line = br.readLine()) != null) {
                try {
                    //有遇到資料之間只有一個空格，目前手動增加資料空格
                    if (StringUtils.trimToNull(line) != null) {
                        String[] splits = line.split("\\s{2,}");
                        model = new SoftwareInfoModel();
                        model.setIp(ip);
                        model.setName(splits[1].trim());
                        model.setVersion(splits[2].trim());
                        model.setDescription(splits[4].trim());
                        softwareInfoModels.add(model);
                    } else {
                        System.out.println(String.format("[line:%s] is null", line));
                    }
                }catch (Exception e) {
                    System.out.println(String.format("第%s行 >> [line:%s]-error:%s",lineNumber,  line, e.getMessage()));
                    e.printStackTrace();
                } finally {
                    lineNumber++;
                }
            }
        }
    }
}
