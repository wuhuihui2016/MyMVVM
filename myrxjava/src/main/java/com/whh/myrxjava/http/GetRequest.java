package com.whh.myrxjava.http;

import com.whh.myrxjava.bean.Gongzh;
import com.whh.myrxjava.bean.HotWord;

import io.reactivex.Observable;
import retrofit2.http.GET;

/**
 * author:wuhuihui 2021.07.14
 */
public interface GetRequest {

    @GET("wxarticle/chapters/json")
    Observable<Gongzh> getCall();

    @GET("hotkey/json")
    Observable<HotWord> getHotWord();
}
