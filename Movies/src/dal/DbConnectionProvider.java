/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dal;

import com.microsoft.sqlserver.jdbc.SQLServerDataSource;
import com.microsoft.sqlserver.jdbc.SQLServerException;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author chris
 */
public class DbConnectionProvider {

    private static final String PROP_FILE = "data/databaseConnectionInfo.properties";
    private SQLServerDataSource ds;

    public DbConnectionProvider() {

        try {
            Properties databaseProperties = new Properties();
            databaseProperties.load(new FileInputStream(PROP_FILE));
            ds = new SQLServerDataSource();
            ds.setServerName(databaseProperties.getProperty("Server"));
            ds.setDatabaseName(databaseProperties.getProperty("Database"));
            ds.setUser(databaseProperties.getProperty("User"));
            ds.setPassword(databaseProperties.getProperty("Password"));
        } catch (IOException ex) {
            Logger.getLogger(DbConnectionProvider.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public Connection getConnection() {

        try {
            return ds.getConnection();
        } catch (SQLServerException ex) {
            Logger.getLogger(DbConnectionProvider.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

}
