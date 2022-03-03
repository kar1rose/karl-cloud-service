package org.karl.sh.sharding.demo.controller;

import org.karl.sh.sharding.demo.mapper.VehicleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * 车辆controller层
 *
 * @author Lawrence.Luo
 * @date 2021/9/6 18:08
 */
@RestController
@RequestMapping("/vehicle")
public class VehicleController {

    @Autowired
    private VehicleMapper vehicleMapper;

    @GetMapping("/{id}")
    public Map<String, Object> getById(@PathVariable Integer id) {
        return vehicleMapper.selectByVehicleId(id);
    }
}
