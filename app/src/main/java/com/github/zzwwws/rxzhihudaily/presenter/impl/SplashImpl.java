package com.github.zzwwws.rxzhihudaily.presenter.impl;

import android.util.Log;

import com.github.zzwwws.rxzhihudaily.model.entities.StartImage;
import com.github.zzwwws.rxzhihudaily.presenter.infr.SplashImageView;
import com.github.zzwwws.rxzhihudaily.presenter.usercase.SplashCase;

import rx.Subscriber;

/**
 * Created by zzw on 16/2/22.
 */
public class SplashImpl implements SplashPresenter<SplashImageView> {
    Subscriber<StartImage> startImageSubscriber;
    SplashImageView splashImageView;
    SplashCase splashCase;

    @Override
    public void getSplashImage(String density) {
        startImageSubscriber = new Subscriber<StartImage>() {//定义了一个监听者，里面定义了一堆回调，回调时的参数类型泛型定义了
            @Override
            public void onCompleted() {
                Log.d("completed","完成了");
            }

            @Override
            public void onError(Throwable e) {
                Log.d("error",e.getMessage());
            }

            @Override
            public void onNext(StartImage startImage) {
                splashImageView.showStartImage(startImage);
            }
        };
        splashCase.subscribe(startImageSubscriber, density);//把这个监听者绑定在splashCase上，同时带了个参数过去
        splashImageView.showStartImage(new StartImage());
    }

    @Override
    public void attachView(SplashImageView view) {
        splashImageView = view;
        splashCase = new SplashCase();
    }

    @Override
    public void detachView() {
        splashCase.unSubscribe();
    }
}
