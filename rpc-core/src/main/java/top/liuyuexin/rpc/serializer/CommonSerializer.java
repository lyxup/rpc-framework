package top.liuyuexin.rpc.serializer;

/**
 * @Author LiuYueXin
 * @data 2020/9/18 17:24
 *
 * 通用的序列化反序列化接口
 */
public interface CommonSerializer {

    byte[] serialize(Object obj);

    Object deserialize(byte[] bytes, Class<?> clazz);

    int getCode();

    Integer KRYO_SERIALIZER = 0;
    Integer JSON_SERIALIZER = 1;
    Integer HESSIAN_SERALIZER = 2;
    Integer PROTOBUF_SERIALIZER = 3;

    static CommonSerializer getByCode(int code) {

        switch (code) {
            case 0:
                return new KryoSerializer();
            case 1:
                return new JsonSerializer();
            case 2:
                return new HessianSerializer();
            case 3:
                return new ProtobufSerializer();
            default:
                return null;
        }
    }
}
