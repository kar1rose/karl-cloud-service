package org.karl.sh.warehouse.service;

import com.alibaba.druid.filter.config.ConfigTools;

/**
 * @author KARL ROSE
 * @date 2020/8/12 15:24
 **/
public class Test {
    public static void main(String[] args) {

        try {
            ConfigTools.main(new String[]{"P@ssw0rd@2020"});
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
