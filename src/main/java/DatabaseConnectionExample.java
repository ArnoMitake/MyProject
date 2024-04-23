import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import model.ChtSMSourLogModel;
import model.TaiSMSourLogModel;

public class DatabaseConnectionExample {
    public static void doDatabaseConnectionExample(String ip, String port, String dbname, String user, String num, List<ChtSMSourLogModel> models) {

        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(
                "jdbc:sqlserver://"+ ip + ":" + port + ";databaseName=" + dbname + ";applicationName=SmSourLogExample;sendStringParametersAsUnicode=false;ColumnEncryptionSetting=Enabled;");

        config.setUsername(user);
        config.setPassword(num);

        HikariDataSource dataSource = new HikariDataSource(config);

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
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
                if (resultSet != null)
                    resultSet.close();
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

    public static void doTaiDatabaseConnectionExample(String ip, String port, String dbname, String user, String num, List<TaiSMSourLogModel> models) {

        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(
                "jdbc:sqlserver://"+ ip + ":" + port + ";databaseName=" + dbname + ";applicationName=SmSourLogExample;sendStringParametersAsUnicode=false;ColumnEncryptionSetting=Enabled;");

        config.setUsername(user);
        config.setPassword(num);

        HikariDataSource dataSource = new HikariDataSource(config);

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
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
                if (resultSet != null)
                    resultSet.close();
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
}
