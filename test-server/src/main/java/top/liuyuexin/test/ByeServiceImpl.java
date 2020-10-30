package top.liuyuexin.test;

import top.liuyuexin.rpc.annotation.Service;
import top.liuyuexin.rpc.api.ByeService;

/**
 * @Author LiuYueXin
 * @data 2020/10/30 15:03
 */
@Service
public class ByeServiceImpl implements ByeService {

    @Override
    public String bye(String name) {
        return "bye, " + name;
    }
}
