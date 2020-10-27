package top.liuyuexin.rpc.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import top.liuyuexin.rpc.enumeration.ResponseCode;

import java.io.Serializable;

/**
 * @Author LiuYueXin
 * @data 2020/9/13 22:31
 */

@Data
public class RpcResponse<T> implements Serializable {

    public RpcResponse() {}

    /**
     *响应状态码
     */
    private Integer statusCode;

    /**
     *响应状态补充信息
     */
    private String message;

    /**
     *响应数据
     */
    private T data;

    public static <T> RpcResponse<T> success(T data){
        RpcResponse<T> response = new RpcResponse<>();
        response.setStatusCode(ResponseCode.SUCCESS.getCode());
        response.setData(data);
        return response;
    }

    public static <T> RpcResponse<T> fail(ResponseCode code){
        RpcResponse<T> response = new RpcResponse<>();
        response.setStatusCode(code.getCode());
        response.setMessage(code.getMessage());
        return response;
    }
}
