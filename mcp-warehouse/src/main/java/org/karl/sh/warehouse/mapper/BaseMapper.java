package org.karl.sh.warehouse.mapper;

import java.io.Serializable;
import java.util.List;


/**
 * @param <Model> The Model Class 这里是泛型不是Model类
 * @param <PK>    The Primary Key Class 如果是无主键，则可以用Model来跳过，如果是多主键则是Key类
 * @author rose
 * @date 2019/6/25 18:56
 */
public interface BaseMapper<Model, PK extends Serializable> {
    /**
     * 根据主键删除
     *
     * @param id 主键
     * @return 更新记录数
     * @author rose
     * @date 2019/6/25 18:57
     **/
    int deleteByPrimaryKey(PK id);

    /**
     * 插入数据
     *
     * @param record 实体类
     * @return 更新记录数
     * @author rose
     * @date 2019/6/25 18:57
     **/
    int insert(Model record);

    /**
     * 根据实体存在的列插入
     *
     * @param record 插入有值的字段
     * @return 更新记录数
     * @author rose
     * @date 2019/6/25 18:57
     **/
    int insertSelective(Model record);

    /**
     * 根据主键查询
     *
     * @param id 主键
     * @return 查询到的实体类
     * @author rose
     * @date 2019/6/25 18:58
     **/
    Model selectByPrimaryKey(PK id);

    /**
     * 更新实体 条件中有值的更新
     *
     * @param record 包含主键的实体信息与待更新内容
     * @return 更新记录数
     * @author rose
     * @date 2019/6/25 18:58
     **/
    int updateByPrimaryKeySelective(Model record);

    /**
     * 根据主键更新 所有属性更新
     *
     * @param record 实体类
     * @return 更新记录数
     * @author rose
     * @date 2019/6/25 18:58
     **/
    int updateByPrimaryKey(Model record);


    /**
     * 分页查询
     *
     * @param record 查询实体类条件
     * @return 实体类列表
     * @author rose
     * @date 2019/6/27 14:54
     **/
    List<Model> selectList(Model record);


    /**
     * 批量插入信息
     *
     * @param list 集合
     * @return 插入用户数
     * @author KARL.ROSE
     * @date 2019/9/3 3:19 下午
     **/
    Integer insertBatch(List<Model> list);

}