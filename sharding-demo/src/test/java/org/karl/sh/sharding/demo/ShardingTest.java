package org.karl.sh.sharding.demo;

import com.zaxxer.hikari.HikariDataSource;
import org.apache.shardingsphere.driver.api.ShardingSphereDataSourceFactory;
import org.apache.shardingsphere.sharding.api.config.ShardingRuleConfiguration;
import org.apache.shardingsphere.sharding.api.config.rule.ShardingTableRuleConfiguration;
import org.junit.Test;

import javax.sql.DataSource;
import java.sql.*;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * 测试类
 *
 * @author Lawrence.Luo
 * @date 2021/9/6 13:59
 */
public class ShardingTest {

    /*private static final String logic_table_1 = "vehicle_info";
    private static final String table_1 = "db${1}.truck_info";
    private static final String logic_table_2 = "vehicle_rental_info";
    private static final String table_2 = "db${2}.truck_rental_info";*/


    private static final String logic_table_1 = "t_user";
    private static final String table_1 = "db${1}.user";
    private static final String logic_table_2 = "p_order";
    private static final String table_2 = "db${2}.purchase_order";

    private static DataSource dataSource;

    static {
        Map<String, DataSource> dataSourceMap = new HashMap<>(16);
        HikariDataSource dataSource1 = new HikariDataSource();
        dataSource1.setDriverClassName("com.mysql.cj.jdbc.Driver");
        dataSource1.setJdbcUrl("jdbc:mysql://139.224.83.117:3306/db1?useSSL=false&useUnicode=true&characterEncoding=UTF-8&serverTimezone=GMT%2B8");
        dataSource1.setUsername("root");
        dataSource1.setPassword("P@ssw0rd@2021");
        dataSourceMap.put("db1", dataSource1);

        HikariDataSource dataSource2 = new HikariDataSource();
        dataSource2.setDriverClassName("com.mysql.cj.jdbc.Driver");
        dataSource2.setJdbcUrl("jdbc:mysql://139.224.83.117:3306/db2?useSSL=false&useUnicode=true&characterEncoding=UTF-8&serverTimezone=GMT%2B8");
        dataSource2.setUsername("root");
        dataSource2.setPassword("P@ssw0rd@2021");
        dataSourceMap.put("db2", dataSource2);

        // 配置 t_order 表规则
        ShardingTableRuleConfiguration orderTableRuleConfig = new ShardingTableRuleConfiguration("t_user", "db${1}.user");
        // 配置分库策略
//        orderTableRuleConfig.setDatabaseShardingStrategy(new StandardShardingStrategyConfiguration("user_id", "dbShardingAlgorithm"));
        // 配置分表策略
//        orderTableRuleConfig.setTableShardingStrategy(new StandardShardingStrategyConfiguration("order_id", "tableShardingAlgorithm"));

        ShardingTableRuleConfiguration userTableRuleConfig = new ShardingTableRuleConfiguration("p_order", "db${2}.purchase_order");

        // 配置分片规则
        ShardingRuleConfiguration shardingRuleConfig = new ShardingRuleConfiguration();
        shardingRuleConfig.getTables().add(orderTableRuleConfig);
        shardingRuleConfig.getTables().add(userTableRuleConfig);
//        shardingRuleConfig.getBindingTableGroups().add("" + logic_table_1 + "," + logic_table_2);
        // 配置分库算法
        /*Properties dbShardingAlgorithmrProps = new Properties();
        dbShardingAlgorithmrProps.setProperty("algorithm-expression", "ds${user_id % 2}");
        shardingRuleConfig.getShardingAlgorithms().put("dbShardingAlgorithm", new ShardingSphereAlgorithmConfiguration("INLINE", dbShardingAlgorithmrProps));*/
        // 配置分表算法
        /*Properties tableShardingAlgorithmrProps = new Properties();
        tableShardingAlgorithmrProps.setProperty("algorithm-expression", "t_order${order_id % 2}");
        shardingRuleConfig.getShardingAlgorithms().put("tableShardingAlgorithm", new ShardingSphereAlgorithmConfiguration("INLINE", tableShardingAlgorithmrProps));*/


        /* 其他配置 */
        Properties properties = new Properties();
        properties.setProperty("sql-show", "true");
        // 创建 ShardingSphereDataSource
        try {
            dataSource = ShardingSphereDataSourceFactory.createDataSource(dataSourceMap, Collections.singleton(shardingRuleConfig), properties);
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
    }

    @Test
    public void shardingTests() {
//        String sql = "select vi.id,vi.car_num,vi.car_type from vehicle_info vi where vi.id = ?";
//        String sql = "select vri.organization_name from vehicle_rental_info vri where vri.truck_info_id = ?";
//        String sql = "select vi.truck_info_id,vi.car_num,vi.car_type,vri.organization_name from vehicle_info vi join vehicle_rental_info vri on vi.truck_info_id = vri.truck_info_id where vi.truck_info_id = ?";
        String sql = "select * from t_user t join p_order p on t.id = p.user_id ";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
//            ps.setInt(1, 3);
            try (ResultSet rs = ps.executeQuery()) {
                System.out.println("============================================================================");
                ResultSetMetaData metaData = rs.getMetaData();
                int count = metaData.getColumnCount();
                for (int i = 1; i <= count; i++) {
                    System.out.print(metaData.getColumnName(i));
                    if (i != count) {
                        System.out.print("|");
                    }
                }
                System.out.println();
                while (rs.next()) {
                    for (int i = 1; i <= count; i++) {
                        System.out.print(rs.getObject(i));
                        if (i != count) {
                            System.out.print("|");
                        }
                    }
                    System.out.println();
                }
            }
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
    }

    @Test
    public void cast() {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());

    }

}
