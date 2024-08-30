package mycode.dao.Impl;

import java.util.List;

import mycode.dao.DatabaseConnectionFactory;
import mycode.model.ChtSMSourLogModel;
import mycode.model.SoftwareInfoModel;
import mycode.model.TaiSMSourLogModel;
import mycode.model.TvpModel;

public interface DatabaseConnectionImpl {

    void doSmSourLogDatabaseConnection(List<ChtSMSourLogModel> chtSMSourLogModels);

    void doTaiDatabaseConnection(List<TaiSMSourLogModel> models);

    int batchInsertTvpTest(List<TvpModel> tvpModels);
    
    void batchInsertSoftwareInfoData(List<SoftwareInfoModel> softwareInfoModels);

    void setDatabaseConnectionFactory(DatabaseConnectionFactory databaseConnectionFactory);
    
}
