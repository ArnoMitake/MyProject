package mycode.model;

public class PropertiesModel extends BaseModel {
    private String keyWord;
    private String folderPath;
    private String db_ip;
    private String db_port;
    private String db_dbname;
    private String db_user;
    private String db_num;

    public String getKeyWord() {
        return keyWord;
    }

    public void setKeyWord(String keyWord) {
        this.keyWord = keyWord;
    }

    public String getFolderPath() {
        return folderPath;
    }

    public void setFolderPath(String folderPath) {
        this.folderPath = folderPath;
    }

    public String getDb_ip() {
        return db_ip;
    }

    public void setDb_ip(String db_ip) {
        this.db_ip = db_ip;
    }

    public String getDb_port() {
        return db_port;
    }

    public void setDb_port(String db_port) {
        this.db_port = db_port;
    }

    public String getDb_dbname() {
        return db_dbname;
    }

    public void setDb_dbname(String db_dbname) {
        this.db_dbname = db_dbname;
    }

    public String getDb_user() {
        return db_user;
    }

    public void setDb_user(String db_user) {
        this.db_user = db_user;
    }

    public String getDb_num() {
        return db_num;
    }

    public void setDb_num(String db_num) {
        this.db_num = db_num;
    }
}
