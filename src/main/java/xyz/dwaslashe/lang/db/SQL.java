package xyz.dwaslashe.lang.db;

import lombok.SneakyThrows;

import java.sql.Connection;
import java.sql.DriverManager;

public class SQL {

    private Connection connection;

    public Connection getConnection() {
        return connection;
    }

    @SneakyThrows
    public void connect(String host, String table, String username, String password, int port, boolean ssl){
        if(!isClosed()) return;
        Class.forName("java.sql.DriverManager");
        connection = DriverManager.getConnection("jdbc:mysql://" + host + ":" + port + "/" + table + "?useSSL=" + ssl, username, password);
    }

    @SneakyThrows
    public boolean isClosed(){
        return connection == null || connection.isClosed();
    }

    @SneakyThrows
    public void shutdown(){
        if(isClosed()) return;
        connection.close();
    }
}
