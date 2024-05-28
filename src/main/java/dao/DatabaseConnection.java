package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.List;

import com.microsoft.sqlserver.jdbc.SQLServerConnection;
import com.microsoft.sqlserver.jdbc.SQLServerDataTable;
import com.microsoft.sqlserver.jdbc.SQLServerPreparedStatement;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceUtils;

import javax.sql.DataSource;

import model.ChtSMSourLogModel;
import model.TaiSMSourLogModel;
import model.TvpModel;

public class DatabaseConnection extends JdbcTemplate {

    private HikariConfig hikariConfig = null;
    private HikariDataSource hikariDataSource = null;
    private String ip;
    private String port;
    private String dbName;
    private String user;
    private String passWord;

    public static void doDatabaseConnection(String ip, String port, String dbname, String user, String num, List<ChtSMSourLogModel> models) {

        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(
                "jdbc:sqlserver://"+ ip + ":" + port + ";databaseName=" + dbname + ";applicationName=SmSourLogExample;sendStringParametersAsUnicode=false;ColumnEncryptionSetting=Enabled;");

        config.setUsername(user);
        config.setPassword(num);

        HikariDataSource dataSource = new HikariDataSource(config);

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = dataSource.getConnection();
            preparedStatement = connection.prepareStatement(
                    "INSERT INTO LOG.dbo.Cht_SMSourLog (Date, Time, Ip, PartKey, Mid, SessionId)" +
                            " VALUES (?, ? ,? ,? ,? ,?)");

            for (ChtSMSourLogModel model : models) {
                preparedStatement.setString(1, model.getDate());
                preparedStatement.setString(2, model.getTime());
                preparedStatement.setString(3, model.getIp());
                preparedStatement.setString(4, model.getPartKey());
                preparedStatement.setString(5, model.getMid());
                preparedStatement.setString(6, model.getSessionId());
                preparedStatement.addBatch();
            }

            int[] batchResult = preparedStatement.executeBatch();
            System.out.println("count: " + Arrays.stream(batchResult).sum());
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (preparedStatement != null)
                    preparedStatement.close();
                if (connection != null)
                    connection.close();
                dataSource.close();
            } catch (SQLException e) {
                System.out.println(e);
                e.printStackTrace();
            }
        }
    }

    public static void doTaiDatabaseConnection(String ip, String port, String dbname, String user, String num, List<TaiSMSourLogModel> models) {

        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(
                "jdbc:sqlserver://"+ ip + ":" + port + ";databaseName=" + dbname + ";applicationName=SmSourLogExample;sendStringParametersAsUnicode=false;ColumnEncryptionSetting=Enabled;");

        config.setUsername(user);
        config.setPassword(num);

        HikariDataSource dataSource = new HikariDataSource(config);

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = dataSource.getConnection();
            preparedStatement = connection.prepareStatement(
                    "INSERT INTO LOG.dbo.Tai_SMSourLog (Date, Time, ThreadID, SerialNo, Mid, ExecutionTime)" +
                            " VALUES (?, ? ,? ,? ,? ,?)");

            for (TaiSMSourLogModel model : models) {
                preparedStatement.setString(1, model.getDate());
                preparedStatement.setString(2, model.getTime());
                preparedStatement.setString(3, model.getThreadID());
                preparedStatement.setString(4, model.getSerialNo());
                preparedStatement.setString(5, model.getMid());
                preparedStatement.setString(6, model.getExecutionTime());
                preparedStatement.addBatch();
            }

            int[] batchResult = preparedStatement.executeBatch();
            System.out.println("count: " + Arrays.stream(batchResult).sum());
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {                
                if (preparedStatement != null)
                    preparedStatement.close();
                if (connection != null)
                    connection.close();
                dataSource.close();
            } catch (SQLException e) {
                System.out.println(e);
                e.printStackTrace();
            }
        }
    }
    
    public int batchInsertTvpTest(List<TvpModel> tvpModels) {
        int result = -1;
        DataSource ds = null;
        Connection conn = null;
        Connection nativeConn = null;
        SQLServerDataTable sourceDataTable = null;
        SQLServerPreparedStatement pStmt = null;
                
        StringBuilder sql = new StringBuilder();
        //資料會被撈走
        sql.append(" INSERT INTO SMDR.dbo.SM0001(Data, Stamp) ");
        sql.append(" SELECT Data, Stamp FROM ? ");
        
        try {
            ds = this.hikariDataSource;
            conn = DataSourceUtils.doGetConnection(ds);
            nativeConn = conn.unwrap(SQLServerConnection.class);
            
            if (tvpModels != null && !tvpModels.isEmpty()) {
                pStmt = (SQLServerPreparedStatement) nativeConn.prepareStatement(sql.toString());
                
                sourceDataTable = new SQLServerDataTable();
                sourceDataTable.addColumnMetadata("Data", java.sql.Types.CHAR);
                sourceDataTable.addColumnMetadata("Stamp", java.sql.Types.TIMESTAMP);

                for (TvpModel model : tvpModels) {
                    sourceDataTable.addRow(model.getData(), new Timestamp(System.currentTimeMillis()));
                }

                pStmt.setStructured(1, "dbo.SM0001Type", sourceDataTable);

                result = pStmt.executeUpdate();

                pStmt.clearParameters();
                sourceDataTable.clear();
            }            
        } catch (SQLException e) {
            throw new RuntimeException("batchInsertTvpTest fail", e);
        } finally {
            DataSourceUtils.releaseConnection(conn, ds);
        }        
        return result;
    }


    public DatabaseConnection(String ip, String port, String dbName, String user, String passWord) {
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
