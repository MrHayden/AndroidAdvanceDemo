package com.xxm.toolbase.utils;

import com.xxm.toolhttp.retrofit.http.BaseResponse;
import com.xxm.toolhttp.retrofit.http.ExceptionHandle;
import com.xxm.toolhttp.retrofit.http.ResponseThrowable;
import com.xxm.toolhttp.retrofit.utils.ToastUtils;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by xxm on 2017/6/19.
 * 有关Rx的工具类
 */
public class RxHttpUtils {

    /**
     * 返回数据dada为null会抛异常
     *
     * @param observable
     * @param consumer
     * @param <T>
     */
    public static <T> void sendApiRturnBean(Observable<BaseResponse<T>> observable, Consumer<T> consumer) {
        observable.compose(RxHttpUtils.schedulersTransformer())//线程调度
                .compose(RxHttpUtils.exceptionTransformerBean())// 网络错误的异常转换, 这里可以换成自己的ExceptionHandle
                .subscribe(consumer, new Consumer<ResponseThrowable>() {
                    @Override
                    public void accept(ResponseThrowable throwable) throws Exception {
                        ToastUtils.showShort(throwable.message);
                    }
                });
    }

    public static <T> void sendApiBean(Observable<BaseResponse<T>> observable, final BeanBase<T> tBeanBase) {
        observable.compose(RxHttpUtils.schedulersTransformer())//线程调度
                .compose(RxHttpUtils.exceptionTransformer())// 网络错误的异常转换, 这里可以换成自己的ExceptionHandle
                .subscribe(new Consumer<BaseResponse<T>>() {
                    @Override
                    public void accept(BaseResponse<T> response) throws Exception {
                        if (response == null) {
                            tBeanBase.onFailData(null);
                            return;
                        }
                        if (response.isOk()) {
                            tBeanBase.onSucData(response.getResult());
                        } else {
                            tBeanBase.onFailData(response);
                            if (!StringUtils.isEmpty(response.getMessage()))
                                ToastUtils.showShort(response.getMessage());
                        }
                    }
                }, new Consumer<ResponseThrowable>() {
                    @Override
                    public void accept(ResponseThrowable throwable) throws Exception {
                        ToastUtils.showShort(throwable.message);
                    }
                });
    }

    public static <T> void sendApiBean(Observable<BaseResponse<T>> observable, final BeanBase<T> tBeanBase, Consumer<ResponseThrowable> throwableConsumer) {
        observable.compose(RxHttpUtils.schedulersTransformer())//线程调度
                .compose(RxHttpUtils.exceptionTransformer())// 网络错误的异常转换, 这里可以换成自己的ExceptionHandle
                .subscribe(new Consumer<BaseResponse<T>>() {
                    @Override
                    public void accept(BaseResponse<T> response) throws Exception {
                        if (response == null) {
                            tBeanBase.onFailData(null);
                            return;
                        }
                        if (response.isOk()) {
                            tBeanBase.onSucData(response.getResult());
                        } else {
                            tBeanBase.onFailData(response);
                            if (!StringUtils.isEmpty(response.getMessage()))
                                ToastUtils.showShort(response.getMessage());
                        }
                    }
                }, throwableConsumer);
    }

    public static <T> void sendApi(Observable<BaseResponse<T>> observable, Consumer<BaseResponse<T>> consumer) {
        observable.compose(RxHttpUtils.schedulersTransformer())//线程调度
                .compose(RxHttpUtils.exceptionTransformer())// 网络错误的异常转换, 这里可以换成自己的ExceptionHandle
                .subscribe(consumer, new Consumer<ResponseThrowable>() {
                    @Override
                    public void accept(ResponseThrowable throwable) throws Exception {
                        ToastUtils.showShort(throwable.message);
                    }
                });
    }

    public static <T> void sendApi(Observable<BaseResponse<T>> observable, Consumer<BaseResponse<T>> consumer, Consumer<ResponseThrowable> throwableConsumer) {
        observable.compose(RxHttpUtils.schedulersTransformer())//线程调度
                .compose(RxHttpUtils.exceptionTransformer())// 网络错误的异常转换, 这里可以换成自己的ExceptionHandle
                .subscribe(consumer, throwableConsumer);
    }


    /**
     * 线程调度器
     */
    public static ObservableTransformer schedulersTransformer() {
        return new ObservableTransformer() {
            @Override
            public ObservableSource apply(Observable upstream) {
                return upstream.subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread());
            }
        };
    }

    public static ObservableTransformer exceptionTransformer() {

        return new ObservableTransformer() {
            @Override
            public ObservableSource apply(Observable observable) {
                return observable
                        .onErrorResumeNext(new HttpResponseFunc());
            }
        };
    }

    public static <T> ObservableTransformer exceptionTransformerBean() {

        return new ObservableTransformer() {
            @Override
            public ObservableSource apply(Observable observable) {
                return observable
                        .map(new HandleFuc<T>())  //这里可以取出BaseResponse中的Result
                        .onErrorResumeNext(new HttpResponseFunc());
            }
        };
    }

    private static class HttpResponseFunc<T> implements Function<Throwable, Observable<T>> {
        @Override
        public Observable<T> apply(Throwable t) {
            return Observable.error(ExceptionHandle.handleException(t));
        }
    }

    private static class HandleFuc<T> implements Function<BaseResponse<T>, T> {
        @Override
        public T apply(BaseResponse<T> response) {
            if (response == null) return null;
            if (!response.isOk()) {
                if (!StringUtils.isEmpty(response.getMessage()))
                    ToastUtils.showShort(response.getMessage());
                throw new RuntimeException(!"".equals(response.getCode() + "" + response.getMessage()) ? response.getMessage() : "");
            }
            if (response.getResult() == null) {
            }
            return response.getResult();
        }
    }

    public interface BeanBase<T> {
        void onSucData(T t);

        void onFailData(BaseResponse<T> response);

    }
}
