package org.karl.sh.purchase.mapper;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.util.Bytes;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author KARL ROSE
 * @date 2021/1/5 16:41
 **/
public class UserRepository {

    private final static String tableName = "order";

    private final static byte[] ORDER_ID = Bytes.toBytes("order_no");
    private final static byte[] TIME = Bytes.toBytes("time");

    private static ExecutorService executor = null;
    private static Connection conn = null;
    private static Configuration conf = null;

    static {
        try {
            conf = HBaseConfiguration.create();
            conf.set("hbase.zookeeper.quorum", "139.224.83.117");
            conf.set("hbase.zookeeper.property.clientPort", "2181");
            conf.set("hbase.defaults.for.version.skip", "true");
            executor = Executors.newFixedThreadPool(20);
            conn = ConnectionFactory.createConnection(conf, executor);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Connection getConn() {
        return conn;
    }

    public static void queryData(String row) {
        try (Table table = getConn().getTable(TableName.valueOf(tableName))) {
            Get get = new Get(Bytes.toBytes(row));
            Result rst = table.get(get);
            byte[] value = rst.getRow();
            if (value != null && value.length > 0) {
                System.out.println(new String(value));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void main(String[] args) throws IOException {
        queryData("row1");
    }
}
