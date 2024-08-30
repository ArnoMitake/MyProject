package mycode.dao;

import java.sql.Connection;
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

import mycode.model.ChtSMSourLogModel;
import mycode.model.SoftwareInfoModel;
import mycode.model.TaiSMSourLogModel;
import mycode.model.TvpModel;

public class DatabaseConnection extends JdbcTemplate {
    private HikariConfig hikariConfig = null;
    private HikariDataSource hikariDataSource = null;
    private String ip;
    private String port;
    private String dbName;
    private String user;
    private String passWord;

	public void doSmSourLogDatabaseConnection(List<ChtSMSourLogModel> chtSMSourLogModels) {
		int[] result = null;
		StringBuilder sql = new StringBuilder();

		sql.append(" INSERT INTO LOG.dbo.Cht_SMSourLog (Date, Time, Ip, PartKey, Mid, SessionId) ");
		sql.append(" VALUES (?, ? ,? ,? ,? ,?) ");

		try (Connection conn = (DataSourceUtils.doGetConnection(this.hikariDataSource))
				.unwrap(SQLServerConnection.class);
				SQLServerPreparedStatement pStmt = (SQLServerPreparedStatement) conn.prepareStatement(sql.toString())) {

			for (ChtSMSourLogModel model : chtSMSourLogModels) {
				pStmt.setString(1, model.getDate());
				pStmt.setString(2, model.getTime());
				pStmt.setString(3, model.getIp());
				pStmt.setString(4, model.getPartKey());
				pStmt.setString(5, model.getMid());
				pStmt.setString(6, model.getSessionId());
				pStmt.addBatch();
			}
			result = pStmt.executeBatch();

			pStmt.clearParameters();
		} catch (SQLException e) {
			throw new RuntimeException("doSmSourLogDatabaseConnection fail", e);
		}
		System.out.println("count: " + Arrays.stream(result).sum());
	}

    public void doTaiDatabaseConnection(List<TaiSMSourLogModel> models) {
        int[] result = null;
        StringBuilder sql = new StringBuilder();

        sql.append(" INSERT INTO LOG.dbo.Tai_SMSourLog (Date, Time, ThreadID, SerialNo, Mid, ExecutionTime) ");
        sql.append(" VALUES (?, ? ,? ,? ,? ,?) ");

        try (Connection conn = (DataSourceUtils.doGetConnection(this.hikariDataSource))
                .unwrap(SQLServerConnection.class);
             SQLServerPreparedStatement pStmt = (SQLServerPreparedStatement) conn.prepareStatement(sql.toString())) {
            
            for (TaiSMSourLogModel model : models) {
                pStmt.setString(1, model.getDate());
                pStmt.setString(2, model.getTime());
                pStmt.setString(3, model.getThreadID());
                pStmt.setString(4, model.getSerialNo());
                pStmt.setString(5, model.getMid());
                pStmt.setString(6, model.getExecutionTime());
                pStmt.addBatch();
            }

            result = pStmt.executeBatch();
            
            pStmt.clearParameters();            
        } catch (SQLException e) {
            throw new RuntimeException("doTaiDatabaseConnection fail", e);
        }
        System.out.println("count: " + Arrays.stream(result).sum());
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

    public void batchInsertSoftwareInfoData(List<SoftwareInfoModel> softwareInfoModels) {
        int[] result = null;
        StringBuilder sql = new StringBuilder();

        sql.append(" INSERT INTO DBexp.dbo.SoftwareInfo(Ip, Name, Version, Description) ");
        sql.append(" VALUES (?, ? ,? ,?) ");

        try (Connection conn = (DataSourceUtils.doGetConnection(this.hikariDataSource))
                .unwrap(SQLServerConnection.class);
             SQLServerPreparedStatement pStmt = (SQLServerPreparedStatement) conn.prepareStatement(sql.toString())) {

            for (SoftwareInfoModel model : softwareInfoModels) {
                pStmt.setString(1, model.getIp());
                pStmt.setString(2, model.getName());
                pStmt.setString(3, model.getVersion());
                pStmt.setString(4, model.getDescription());
                pStmt.addBatch();
            }

            result = pStmt.executeBatch();

            pStmt.clearParameters();
        } catch (SQLException e) {
            throw new RuntimeException("batchInsertSoftwareInfoData fail", e);
        }
        System.out.println("count: " + Arrays.stream(result).sum());
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
