package org.karl.sh.purchase.config;

import org.karl.sh.core.utils.SnowFlake;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**
 * 分布式主键获取
 *
 * @author KARL.ROSE
 * @date 2021/4/1 15:53
 */
@Component
@ConfigurationProperties(prefix = "snowflake.key")
public class KeyGenerator {

    private Long dataId;

    private Long machineId;

    @Bean
    public SnowFlake snowFlake() {
        return new SnowFlake(dataId, machineId);
    }

    public void setDataId(Long dataId) {
        this.dataId = dataId;
    }

    public void setMachineId(Long machineId) {
        this.machineId = machineId;
    }
}
