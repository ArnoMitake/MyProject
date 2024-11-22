package mycode.utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.text.MessageFormat;
import java.util.Arrays;
import java.util.Properties;

import mycode.model.PropertiesModel;

public class PropertiesUtil {
    private static PropertiesUtil propertiesUtil;
    private Properties prop;

    public PropertiesUtil() {
    }

    public static synchronized PropertiesUtil getInstance() {
        return propertiesUtil == null ? new PropertiesUtil() : propertiesUtil;
    }
    
    public void getSmSourLogProperties(PropertiesModel propertiesModel) {
        try {
            getProperties();
            propertiesModel.setKeyWord(prop.getProperty("smsourlog.keyword"));
            propertiesModel.setFolderPath(prop.getProperty("smsourlog.folderpath"));
        }catch (FileNotFoundException fe) {
            fe.printStackTrace();
        }
    }

    public void getTAISourLogProperties(PropertiesModel propertiesModel) {
        try {
            getProperties();
            propertiesModel.setKeyWord(prop.getProperty("taisourlog.keyword"));
            propertiesModel.setFolderPath(prop.getProperty("taisourlog.folderpath"));
        }catch (FileNotFoundException fe) {
            fe.printStackTrace();
        }
    }

    public void getSoftwareInfoLogProperties(PropertiesModel propertiesModel) {
        try {
            getProperties();
            propertiesModel.setFolderPath(prop.getProperty("software.folderpath"));
            propertiesModel.setFolderPaths(Arrays.asList(prop.getProperty("software.folderpaths").split(",")));
        }catch (FileNotFoundException fe) {
            fe.printStackTrace();
        }
    }

    public void getCookieLogProperties(PropertiesModel propertiesModel) {
        try {
            getProperties();
            propertiesModel.setKeyWord(prop.getProperty("cookielog.keyword"));
            propertiesModel.setFolderPath(prop.getProperty("cookielog.folderpath"));
//            propertiesModel.setFolderPaths(Arrays.asList(prop.getProperty("cookielog.folderpaths").split(",")));
        }catch (FileNotFoundException fe) {
            fe.printStackTrace();
        }
    }

    public void getDBLOGProperties(PropertiesModel propertiesModel) {
        try {
            getProperties();
            propertiesModel.setDb_ip(prop.getProperty("log.db.ip"));
            propertiesModel.setDb_port(prop.getProperty("log.db.port"));
            propertiesModel.setDb_dbname(prop.getProperty("log.db.dbname"));
            propertiesModel.setDb_user(prop.getProperty("log.db.user"));
            propertiesModel.setDb_num(prop.getProperty("log.db.num"));
        }catch (FileNotFoundException fe) {
            fe.printStackTrace();
        }
    }

    public void getDBexpProperties(PropertiesModel propertiesModel) {
        try {
            getProperties();
            propertiesModel.setDb_ip(prop.getProperty("dbexp.db.ip"));
            propertiesModel.setDb_port(prop.getProperty("dbexp.db.port"));
            propertiesModel.setDb_dbname(prop.getProperty("dbexp.db.dbname"));
            propertiesModel.setDb_user(prop.getProperty("dbexp.db.user"));
            propertiesModel.setDb_num(prop.getProperty("dbexp.db.num"));
        }catch (FileNotFoundException fe) {
            fe.printStackTrace();
        }
    }

    public void getDBSMDRProperties(PropertiesModel propertiesModel) {
        try {
            getProperties();
            propertiesModel.setDb_ip(prop.getProperty("smdr.db.ip"));
            propertiesModel.setDb_port(prop.getProperty("smdr.db.port"));
            propertiesModel.setDb_dbname(prop.getProperty("smdr.db.dbname"));
            propertiesModel.setDb_user(prop.getProperty("smdr.db.user"));
            propertiesModel.setDb_num(prop.getProperty("smdr.db.num"));
        }catch (FileNotFoundException fe) {
            fe.printStackTrace();
        }
    }

    //todo 目前沒用到
    public void getDBMSSProperties(PropertiesModel propertiesModel) {
        try {
            getProperties();
            propertiesModel.setDb_ip(prop.getProperty("mss.db.ip"));
            propertiesModel.setDb_port(prop.getProperty("mss.db.port"));
            propertiesModel.setDb_dbname(prop.getProperty("mss.db.dbname"));
            propertiesModel.setDb_user(prop.getProperty("mss.db.user"));
            propertiesModel.setDb_num(prop.getProperty("mss.db.num"));
        }catch (FileNotFoundException fe) {
            fe.printStackTrace();
        }
    }
    
    
    private void getProperties() throws FileNotFoundException {
        if (prop == null) {
            prop = new Properties();
            try (FileInputStream input = new FileInputStream("conf/AppSettings.properties");
                 InputStreamReader reader = new InputStreamReader(input, StandardCharsets.UTF_8)) {
                prop.load(reader);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println(MessageFormat.format("prop 已存在 [prop:{0}]", prop));
        }
    }
}
