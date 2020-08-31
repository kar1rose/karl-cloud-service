package org.karl.sh.core.templates;

/**
 * http 返回code枚举
 *
 * @author KARL.ROSE
 * @date 2019/12/26 5:46 下午
 **/
public enum API_RESULT_CODE {
    /**
     * SUCCESS 处理成功
     * ERROR 处理失败 具体信息查看MSG
     * INVALID_TOKEN TOKEN 无效 重新登录
     * NO_AUTHENTICATION 用户无权限
     **/
    SUCCESS("000000", ""),
    ERROR("000001", ""),
    INVALID_TOKEN("000002", "INVALID TOKEN"),
    NO_AUTHENTICATION("000003", "NO_AUTHENTICATION"),
    HTTP_METHOD_NOT_SUPPORTED("000004", "HTTP_METHOD_NOT_SUPPORTED");

    private final String code;
    private final String msg;

    API_RESULT_CODE(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }
    public String getMsg() {
        return msg;
    }
}
