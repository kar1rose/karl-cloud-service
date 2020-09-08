package org.karl.sh.core.templates;


import java.io.Serializable;

/**
 * 模板接口类
 *
 * @author KARL ROSE
 * @date 2019/9/10 10:59
 **/
public interface BaseService<Model, PK extends Serializable> {
    /**
     * 查询列表信息
     *
     * @param model    查询信息
     * @param orderBy  排序字段
     * @param pageNum  页码
     * @param pageSize 单页数量
     * @param paging   是否分页
     * @return 列表
     * @author KARL.ROSE
     * @date 2019/9/9 2:18 下午
     **/
//    PageInfo<Model> getPageList(Model model, Integer pageNum, Integer pageSize, String orderBy, Boolean paging);

    /**
     * 保存信息
     *
     * @param model 要保存的信息
     * @throws BaseException        自定义异常
     * @throws InterruptedException 主键生成异常
     * @author KARL.ROSE
     * @date 2019/9/9 2:55 下午
     **/
    void save(Model model) throws BaseException, InterruptedException;

    /**
     * 更新信息
     *
     * @param model 更新信息
     * @throws BaseException 自定义异常
     * @author KARL.ROSE
     * @date 2019/9/9 3:18 下午
     **/
    void update(Model model) throws BaseException;

    /**
     * 批量删除
     *
     * @param userId 当前用户ID
     * @param ids    待删除的主键数组
     * @throws BaseException 自定义异常
     * @author KARL.ROSE
     * @date 2019/9/9 3:18 下午
     **/
    void delete(String userId, String[] ids) throws BaseException;


    /**
     * 根据主键获取详情
     *
     * @param id 主键
     * @return 业务详情
     * @author KARL.ROSE
     * @date 2019/9/9 3:25 下午
     **/
    Model getById(PK id);

}
