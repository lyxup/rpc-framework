package top.liuyuexin.rpc.exception;

/**
 * @Author LiuYueXin
 * @data 2020/10/27 14:31
 *
 * 序列化异常
 */

public class SerializeException extends RuntimeException{

    public SerializeException(String msg){
        super(msg);
    }
}
