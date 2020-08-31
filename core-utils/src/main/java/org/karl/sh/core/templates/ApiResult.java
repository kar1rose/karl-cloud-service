package org.karl.sh.core.templates;


import java.io.Serializable;

/**
 * @author KARL ROSE
 * @date 2019/12/20 17:12
 **/
public class ApiResult<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    private String code;

    private String msg;

    private T data;

    public ApiResult() {
    }

    public ApiResult(String code) {
        this.code = code;
    }

    public ApiResult(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public ApiResult(String code, T data) {
        this.code = code;
        this.data = data;
    }

    public static <T> ApiResult<T> success() {
        return new ApiResult<>(API_RESULT_CODE.SUCCESS.getCode());
    }

    public static <T> ApiResult<T> success(T data) {
        return new ApiResult<>(API_RESULT_CODE.SUCCESS.getCode(), data);
    }

    public static <T> ApiResult<T> error(String msg) {
        return new ApiResult<>(API_RESULT_CODE.ERROR.getCode(), msg);
    }

    public static <T> ApiResult<T> error(String code, String msg) {
        return new ApiResult<>(code, msg);
    }

    public static <T> ApiResult<T> error(API_RESULT_CODE result) {
        return new ApiResult<>(result.getCode(), result.getMsg());
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
