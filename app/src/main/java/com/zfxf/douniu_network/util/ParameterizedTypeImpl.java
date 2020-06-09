package com.zfxf.douniu_network.util;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * Created by Song on 2019/4/18 09:36
 * Happy Code
 */
public class ParameterizedTypeImpl implements ParameterizedType {
    private final Class raw;
    private final Type args;
    public ParameterizedTypeImpl(Class raw, Type args) {
        this.raw = raw;
        this.args = args;
    }

    /**
     * 无论<>中有几层<>嵌套，这个方法仅仅脱去最外层的<>之后剩下的内容就作为这个方法的返回值
     * 比如 Result<List<T>> 这个方法得到的是List<T> 的Type
     * @return
     */
    @Override
    public Type[] getActualTypeArguments() {
        return new Type[] {args};
    }

    /**
     * 返回的是当前这个ParameterizedType的原始类型
     * 就是 Result.class
     * @return
     */
    @Override
    public Type getRawType() {
        return raw;
    }

    /**
     * 就是调用了原始类型rawType的getDeclaringClass()方法
     * @return
     */
    @Override
    public Type getOwnerType() {return null;}
}
