package com.demo.swt.mystudyappshop.ion;


import com.demo.swt.mystudyappshop.exception.DataException;
import com.demo.swt.mystudyappshop.exception.ServerException;

/**
 * 介绍：这里写介绍
 * 作者：sweet
 * 邮箱：sunwentao@imcoming.cn
 * 时间: 2017/3/2
 */

public interface InterceptNet {

    /**
     *
     * @param resopne  接口返回的原始字符串
     * @return         返回结果见InterceptResult
     * @throws DataException
     * @throws ServerException
     */
    InterceptResult handler(String resopne) throws DataException,ServerException;
}
