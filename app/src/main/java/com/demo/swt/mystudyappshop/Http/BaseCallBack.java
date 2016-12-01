package com.demo.swt.mystudyappshop.Http;

import com.google.gson.Gson;
import com.google.gson.internal.$Gson$Types;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

/**
 * Created by pc on 2016/12/1.
 */

public abstract class BaseCallBack<T> {

    public Type mtype;


    static Type getSuperclassTypeParameter(Class<?> subclass) {
        Type superClass = subclass.getGenericSuperclass();
      /*  if (subclass instanceof Class) {
            throw new RuntimeException("Missing type parameter");
        }*/

        ParameterizedType parameterizedType = (ParameterizedType) superClass;
        return $Gson$Types.canonicalize(parameterizedType.getActualTypeArguments()[0]);
    }

    public BaseCallBack() {
        mtype = getSuperclassTypeParameter(getClass());
    }

    public abstract void onRequestBefore(Request request);

    public abstract void onFailure(Request request, Exception e);


    public abstract void onSuccess(Response response,T t);


    public abstract void onError(Response response, int code, Exception e);


}
