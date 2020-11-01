package top.liuyuexin.rpc.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Author LiuYueXin
 * @data 2020/10/30 14:48
 *
 * 表示一个服务提供类，用于远程接口的实现类
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Service {
    String name() default "";
}
