package org.karl;


import java.sql.*;

/**
 * @author KARL ROSE
 * @date 2020/8/12 15:22
 **/
public class Test {

    public static void main(String[] args) throws Exception {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection connection = DriverManager.getConnection("jdbc:mysql://139.224.83.117/karl?characterEncoding=utf8&useUnicode=true&useSSL=false", "root", "P@ssw0rd@2020");
        Statement statement = connection.createStatement();
        ResultSet resultset = statement.executeQuery("select * from goods");
        ResultSetMetaData metaData = resultset.getMetaData();
        int count = metaData.getColumnCount();
        for (int i = 1; i <= count; i++) {
            String colName = metaData.getColumnName(i);
            System.out.print(colName + "      ");
        }
        System.out.println();
        System.out.println("------------------------------------");
        while (resultset.next()) {
            for (int i = 1; i <= count; i++) {
                String value = resultset.getString(metaData.getColumnName(i));
                System.out.print(value + "    ");
            }
            System.out.println();
        }
        resultset.close();
        statement.close();
        connection.close();

    }
}
