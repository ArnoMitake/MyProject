package Example;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import dao.DatabaseConnection;
import model.TvpModel;

/**
 * 測試 Tvp 的範例
 */
public class MyTvpTest {
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
            DB_ip = prop.getProperty("db.smdr.test.ip");
            DB_port = prop.getProperty("db.smdr.test.port");
            DB_dbname = prop.getProperty("db.smdr.test.dbname");
            DB_user = prop.getProperty("db.smdr.test.user");
            DB_num = prop.getProperty("db.smdr.test.num");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public static void main(String[] args) {
        DatabaseConnection dao = new DatabaseConnection(DB_ip, DB_port, DB_dbname, DB_user, DB_num);
        int result = -1;
        TvpModel model = null;
		List<TvpModel> modelList = new ArrayList<>();
		for (int i = 0; i < 5; i++) {
            model = new TvpModel();
            model.setData(i + "");
            modelList.add(model);
		}

        result = dao.batchInsertTvpTest(modelList);

        System.out.println("tvp新增結果: " + result);
     
    }
}
