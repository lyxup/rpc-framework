package top.liuyuexin.rpc.api;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @Author LiuYueXin
 * @data 2020/9/13 20:39
 *
 * 测试调用的api实体
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HelloObject implements Serializable {
    private Integer id;
    private String message;
}
