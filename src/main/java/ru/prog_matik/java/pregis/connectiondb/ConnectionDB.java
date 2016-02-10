package ru.prog_matik.java.pregis.connectiondb;

import org.h2.jdbcx.JdbcConnectionPool;

import java.sql.Connection;
import java.sql.SQLException;

public class ConnectionDB implements AutoCloseable {

    Connection connectionDB;
    JdbcConnectionPool cp;


    public Connection getConnectionDB() {

        cp = JdbcConnectionPool.create("jdbc:h2:file:/db/pregisdb", "", "");
//        Connection connectionDB = null;
        try {
            connectionDB = cp.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connectionDB;
    }

    @Override
    public void close() throws Exception {

        connectionDB.close();
        cp.dispose();
    }
}
