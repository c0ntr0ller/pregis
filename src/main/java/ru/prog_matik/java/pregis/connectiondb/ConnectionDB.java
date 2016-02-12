package ru.prog_matik.java.pregis.connectiondb;

import org.h2.jdbcx.JdbcConnectionPool;

import java.sql.Connection;
import java.sql.SQLException;

public class ConnectionDB implements AutoCloseable {

    private JdbcConnectionPool cp = JdbcConnectionPool.create("jdbc:h2:file:/db/pregisdb", "", "");;


    public Connection getConnectionDB() throws SQLException {

        return cp.getConnection();
    }

    @Override
    public void close() throws Exception {

        if (cp != null) {
            cp.dispose();
        }

    }

}
