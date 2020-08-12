package org.karl.service.provider.sys.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;
import java.util.Date;


/**
 * @author KARL.ROSE
 * @date 2020/8/12 下午3:31
 **/
@JsonIgnoreProperties(ignoreUnknown = true)
public final class SysUser implements  Serializable {

    private static final long serialVersionUID = 1L;
    /**
     * 用户ID
     */
    private String userId;

    /**
     * 账号
     */
    private String username;

    /**
     * 昵称
     */
    private String nickname;

    /**
     * 密码
     */
    private String password;

    /**
     * 邮箱
     */

    private String email;

    /**
     * 用户状态（0：正常）
     */
    private Integer state;

    /**
     * IP锁定地址
     */

    private String ipAddress;

    /**
     * 电话
     */
    private String phone;

    /**
     * 备注
     */
    private String remark;
    /**
     * FID拼接串
     */
    private String fids;

    /**
     * 所属部门ID
     */
    private String fkDepartId;

    private String departName;

    /**
     * 所属品牌ID
     */
    private String fkBrandId;
    private String brandName;

    /**
     * 是否删除
     */
    private Integer isDelete;
    /**
     * 是否具有超级权限
     */
    private Integer isSuper;

    /**
     * 创建人
     */

    private String createUser;

    /**
     * 创建时间
     */
    private Date createDatetime;
    private String createDatetimeStr;

    /**
     * 更新人
     */
    private String updateUser;

    /**
     * 更新时间
     */
    private Date updateDatetime;

    private String roleId;
    private String roleCode;

    private String roleName;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getFids() {
        return fids;
    }

    public void setFids(String fids) {
        this.fids = fids;
    }

    public String getFkDepartId() {
        return fkDepartId;
    }

    public void setFkDepartId(String fkDepartId) {
        this.fkDepartId = fkDepartId;
    }

    public String getDepartName() {
        return departName;
    }

    public void setDepartName(String departName) {
        this.departName = departName;
    }

    public String getFkBrandId() {
        return fkBrandId;
    }

    public void setFkBrandId(String fkBrandId) {
        this.fkBrandId = fkBrandId;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public Integer getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(Integer isDelete) {
        this.isDelete = isDelete;
    }

    public Integer getIsSuper() {
        return isSuper;
    }

    public void setIsSuper(Integer isSuper) {
        this.isSuper = isSuper;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    public Date getCreateDatetime() {
        return createDatetime;
    }

    public void setCreateDatetime(Date createDatetime) {
        this.createDatetime = createDatetime;
    }

    public String getCreateDatetimeStr() {
        return createDatetimeStr;
    }

    public void setCreateDatetimeStr(String createDatetimeStr) {
        this.createDatetimeStr = createDatetimeStr;
    }

    public String getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser;
    }

    public Date getUpdateDatetime() {
        return updateDatetime;
    }

    public void setUpdateDatetime(Date updateDatetime) {
        this.updateDatetime = updateDatetime;
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public String getRoleCode() {
        return roleCode;
    }

    public void setRoleCode(String roleCode) {
        this.roleCode = roleCode;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public SysUser() {
    }

    public SysUser(String userId, String username, String nickname, String password, String email, Integer state, String ipAddress, String phone, String remark, String fids, String fkDepartId, String departName, String fkBrandId, String brandName, Integer isDelete, Integer isSuper, String createUser, Date createDatetime, String createDatetimeStr, String updateUser, Date updateDatetime) {
        this.userId = userId;
        this.username = username;
        this.nickname = nickname;
        this.password = password;
        this.email = email;
        this.state = state;
        this.ipAddress = ipAddress;
        this.phone = phone;
        this.remark = remark;
        this.fids = fids;
        this.fkDepartId = fkDepartId;
        this.departName = departName;
        this.fkBrandId = fkBrandId;
        this.brandName = brandName;
        this.isDelete = isDelete;
        this.isSuper = isSuper;
        this.createUser = createUser;
        this.createDatetime = createDatetime;
        this.createDatetimeStr = createDatetimeStr;
        this.updateUser = updateUser;
        this.updateDatetime = updateDatetime;
    }

    public SysUser(String userId, String username, String nickname, String password) {
        this.userId = userId;
        this.username = username;
        this.nickname = nickname;
        this.password = password;
    }
}