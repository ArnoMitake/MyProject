package mycode.dao;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

public class DatabaseConnectionFactory {
    public HikariConfig hikariConfig = null;
    public HikariDataSource hikariDataSource = null;
    public String ip;
    public String port;
    public String dbName;
    public String user;
    public String passWord;
    
    public DatabaseConnectionFactory(String ip, String port, String dbName, String user, String passWord) {
        this.ip = ip;
        this.port = port;
        this.dbName = dbName;
        this.user = user;
        this.passWord = passWord;
        DatabaseConnectionConfig();
    }

    private void DatabaseConnectionConfig() {
        if (hikariConfig == null) {
            hikariConfig = new HikariConfig();
        }
        StringBuilder jdbcUrl = new StringBuilder();
        jdbcUrl.append("jdbc:sqlserver://").append(ip).append(":").append(port).append(";");
        jdbcUrl.append("databaseName=").append(dbName).append(";");
        jdbcUrl.append("applicationName=SmSourLogExample;sendStringParametersAsUnicode=false;ColumnEncryptionSetting=Enabled;");

        hikariConfig.setJdbcUrl(jdbcUrl.toString());
        hikariConfig.setUsername(this.user);
        hikariConfig.setPassword(this.passWord);

        hikariDataSource = new HikariDataSource(hikariConfig);

    }

}
