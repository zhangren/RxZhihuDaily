package com.github.zzwwws.rxzhihudaily.presenter.usercase;

import android.support.annotation.CheckResult;

import com.github.zzwwws.rxzhihudaily.model.entities.StartImage;

import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;
import rx.subscriptions.Subscriptions;


public abstract class UserCase<T, R> {

    private Subscription subscription = Subscriptions.empty();

    @SuppressWarnings("unchecked")
    public void subscribe(Observer<T> useCaseSubscriber, R params) {
        this.subscription = this.interactor(params)//通过参数拿到封装成了可observable的StartImage的对象
//                .onBackpressureBuffer()//防止拿到的数据太快拿不及处理会抛异常，所以调一下这个说一句；
//                .take(1)//拿其实一条
//                .filter(new Func1<T, Boolean>() {
//                    @Override
//                    public Boolean call(T t) {
//                        return !subscription.isUnsubscribed();//如果还在订阅中
//                    }
//                })
                .subscribeOn(Schedulers.io())//订阅发生在io线程
                .observeOn(AndroidSchedulers.mainThread())//观察发生在主线程
                .subscribe(useCaseSubscriber);//订阅之。简言之就是通过接口响应的StartImage对象封装成observable中，并绑定即可。
        //去掉网络请求改写之！
//        Observer<StartImage> imageObserver=(Observer<StartImage>)useCaseSubscriber;//这个方法是大家共用的，不能
//        StartImage startImage=new StartImage();
//        startImage.setImg("http://i.meizitu.net/2013/08/30-130G4231U8.jpg");
//        this.subscription = Observable.just(startImage)
//                .subscribe(imageObserver);//只把这个startImage对象发给监听者
    }

    public void unSubscribe() {
        if (!subscription.isUnsubscribed()) {
            subscription.unsubscribe();
        }
    }

    @CheckResult
    protected abstract Observable<T> interactor(R params);//返回一个Observable封装体，里面是T类型，可理解为通过参数去拿到知乎的响应json串
}
