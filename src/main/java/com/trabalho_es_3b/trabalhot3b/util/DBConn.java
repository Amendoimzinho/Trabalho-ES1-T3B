package com.trabalho_es_3b.trabalhot3b.util;

import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

@Component
public class DBConn {

    private final DataSource dataSource;

    public DBConn(DataSource dataSource) { this.dataSource = dataSource; }

    public Connection getConnection() throws SQLException { return dataSource.getConnection(); }
}

