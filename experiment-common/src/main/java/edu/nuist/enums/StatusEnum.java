package edu.nuist.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum StatusEnum {

    /**
     * 请求成功
     */
    SUCCESS_200("200", "请求成功"),

    /**
     * 请求错误
     */
    ERROR_400("400", "请求错误"),
    ERROR_401("401", "权限不足"),
    ERROR_500("500", "服务器未知错误");

    /**
     * 响应状态
     */
    private final String code;
    /**
     * 响应编码
     */
    private final String msg;

}
