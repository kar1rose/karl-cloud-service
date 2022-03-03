package org.karl.sh.sharding.demo.model;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * 车辆数据
 *
 * @author Lawrence.Luo
 * @date 2021/9/6 18:05
 */
@Data
public class VehicleInfo {

    private Integer id;

    private String carNum;

    private String carType;

    private String organizationName;

    private LocalDateTime startTime;

    private LocalDateTime endTime;

}
