package org.karl.sh.boot;

import lombok.extern.slf4j.Slf4j;

import java.sql.*;
import java.util.ServiceLoader;

/**
 * @author KARL ROSE
 * @date 2020/8/5 14:31
 **/
@Slf4j
public class DbTest {

    public static void main(String[] args) throws ClassNotFoundException, SQLException {
//        spi();
        query();
    }

    public static void spi() {
        ServiceLoader<Driver> drivers = ServiceLoader.load(Driver.class);
        drivers.forEach(d -> {
            log.info(d.getClass().getName());
        });
    }

    public static void query() throws ClassNotFoundException, SQLException {
//        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection connection = DriverManager.getConnection("jdbc:mysql://172.21.13.59:3306/cuckoo_dev?characterEncoding=utf8&useUnicode=true&useSSL=false",
                "root", "P@ssw0rd@2020");
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("select * from sys_user");
        ResultSetMetaData metaData = resultSet.getMetaData();
        int col = metaData.getColumnCount();
        while (resultSet.next()) {
            for (int i = 1; i <= col; i++) {
                String colName = metaData.getColumnName(i);
                String colVal = resultSet.getString(colName);
                System.out.println("col=" + colName + ",colVal=" + colVal + " ");
            }
        }
        resultSet.close();
        statement.close();
        connection.close();
    }

    public static void dataSourceDemo(){

    }
}
