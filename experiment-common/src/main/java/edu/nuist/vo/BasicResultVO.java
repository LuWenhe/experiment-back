package edu.nuist.vo;

import edu.nuist.enums.StatusEnum;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class BasicResultVO <T> {

    // 响应状态
    private String status;

    // 响应编码
    private String msg;

    // 返回数据
    private T data;

    private String token;

    public BasicResultVO(StatusEnum status) {
        this(status, null);
    }

    public BasicResultVO(StatusEnum status, String msg, String token) {
        this(status, msg, null, token);
    }

    public BasicResultVO(StatusEnum status, T data) {
        this(status, status.getMsg(), data);
    }

    public BasicResultVO(StatusEnum status, String msg, T data) {
        this.status = status.getCode();
        this.msg = msg;
        this.data = data;
    }

    public BasicResultVO(StatusEnum status, String msg, T data, String token) {
        this.status = status.getCode();;
        this.msg = msg;
        this.data = data;
        this.token = token;
    }

    /**
     * 默认成功响应
     */
    public static BasicResultVO<Void> success() {
        return new BasicResultVO<>(StatusEnum.SUCCESS_200);
    }

    public static <T> BasicResultVO<T> success(String msg) {
        return new BasicResultVO<>(StatusEnum.SUCCESS_200, msg, null);
    }

    public static <T> BasicResultVO<T> success(String msg, String token) {
        return new BasicResultVO<>(StatusEnum.SUCCESS_200, msg, token);
    }

    public static <T> BasicResultVO<T> success(String msg, T data, String token) {
        return new BasicResultVO<>(StatusEnum.SUCCESS_200, msg, data, token);
    }

    /**
     * 带数据的成功响应
     */
    public static <T> BasicResultVO<T> success(T data) {
        return new BasicResultVO<>(StatusEnum.SUCCESS_200, data);
    }

    public static <T> BasicResultVO<T> success(String msg, T data) {
        return new BasicResultVO<>(StatusEnum.SUCCESS_200, data);
    }

    /**
     * 默认失败响应
     */
    public static <T> BasicResultVO<T> fail() {
        return new BasicResultVO<>(StatusEnum.ERROR_500,
                StatusEnum.ERROR_500.getMsg(), null);
    }

    /**
     * 自定义错误信息的失败响应
     */
    public static <T> BasicResultVO<T> fail(String msg) {
        return fail(StatusEnum.ERROR_500, msg);
    }

    /**
     * 自定义状态的失败响应
     */
    public static <T> BasicResultVO<T> fail(StatusEnum status) {
        return fail(status, status.getMsg());
    }

    /**
     * 自定义状态和信息的失败响应
     */
    public static <T> BasicResultVO<T> fail(StatusEnum status, String msg) {
        return new BasicResultVO<>(status, msg, null);
    }

}
