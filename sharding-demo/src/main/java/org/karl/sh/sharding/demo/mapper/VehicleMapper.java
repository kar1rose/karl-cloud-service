package org.karl.sh.sharding.demo.mapper;

import org.apache.ibatis.annotations.Param;

import java.util.Map;

/**
 * 车辆mapper
 *
 * @author Lawrence.Luo
 * @date 2021/9/6 18:02
 */
public interface VehicleMapper {

    Map<String, Object> selectByVehicleId(@Param("id") Integer id);
}
