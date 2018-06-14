package com.wuxiaolong.androidmvpsample.retrofit;

/**
 *
 * @ClassName:Parser

 * @PackageName:com.wuxiaolong.androidmvpsample.retrofit

 * @Create On 2018/5/18   16:19

 * @author:gongchenghao

 * @Copyrights 2018/5/18 宫成浩 All rights reserved.
 */

public interface Parser<T> {
	T parser(String json, Class<T> tClass);
}
