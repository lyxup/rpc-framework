package top.liuyuexin.rpc.api;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @Author LiuYueXin
 * @data 2020/9/13 20:39
 */

@Data
@AllArgsConstructor
public class HelloObject implements Serializable {

    private Integer id;
    private String message;
}
