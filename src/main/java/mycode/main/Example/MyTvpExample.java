package mycode.main.Example;

import java.util.ArrayList;
import java.util.List;

import mycode.dao.DatabaseConnection;
import mycode.dao.DatabaseConnectionFactory;
import mycode.dao.Impl.DatabaseConnectionImpl;
import mycode.model.PropertiesModel;
import mycode.model.TvpModel;
import mycode.utils.PropertiesUtil;

/**
 * 測試 Tvp 的範例
 */
public class MyTvpExample {
    private static DatabaseConnectionImpl dao;
    private static PropertiesModel propertiesModel;

    static {
        propertiesModel = new PropertiesModel();
        PropertiesUtil.getInstance().getDBSMDRProperties(propertiesModel);        
        System.out.println(String.format("[PropertiesModel:%s]", propertiesModel));
        dao = new DatabaseConnection();
        dao.setDatabaseConnectionFactory(
                new DatabaseConnectionFactory(
                        propertiesModel.getDb_ip(), propertiesModel.getDb_port(), propertiesModel.getDb_dbname(),
                        propertiesModel.getDb_user(), propertiesModel.getDb_num()));
    }
    
    public static void main(String[] args) {
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
