package com.kugou.server.http;



import com.kugou.server.convert.KugouConvert;

import java.util.Map;

import io.reactivex.Observable;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.CallAdapter;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.QueryMap;
import retrofit2.http.Url;

public class OkHttp<T extends OkHttp.Request> {
    private T request;

    public OkHttp(T request) {
        this.request = request;
    }

    public Observable<String> get(@Url String url) {
        return request.get(url);
    }

    public Observable<String> get(@Url String url, @QueryMap Map<String, Object> parameter) {
        return request.get(url, parameter);
    }

    public Observable<String> post(@Url String url) {
        return request.post(url);
    }

    public Observable<String> post(@Url String url, @QueryMap Map<String, Object> parameter) {
        return request.post(url, parameter);
    }

    public Observable<String> post(@Url String url, @Body Object json) {
        return request.post(url, json);
    }

    public interface Request {
        @GET
        Observable<String> get(@Url String url);

        @GET
        Observable<String> get(@Url String url, @QueryMap Map<String, Object> parameter);

        @POST
        Observable<String> post(@Url String url);

        @POST
        Observable<String> post(@Url String url, @QueryMap Map<String, Object> parameter);

        @POST
        Observable<String> post(@Url String url, @Body Object json);
    }

    public static class Builder {
        private OkHttpClient okHttpClient;
        private String baseUrl;
        private Converter.Factory convertFactory = KugouConvert.Companion.create();
        private CallAdapter.Factory callAdapterFactory = RxJava2CallAdapterFactory.create();

        public Builder setOkHttpClient(OkHttpClient okHttpClient) {
            this.okHttpClient = okHttpClient;
            return this;
        }

        public Builder setBaseUrl( String baseUrl) {
            this.baseUrl = baseUrl;
            return this;
        }

        public Builder setConvertFactory( Converter.Factory convertFactory) {
            this.convertFactory = convertFactory;
            return this;
        }

        public Builder setCallAdapterFactory( CallAdapter.Factory callAdapterFactory) {
            this.callAdapterFactory = callAdapterFactory;
            return this;
        }

        public <T extends Request> OkHttp<T> builder( Class<T> request) {
            if (okHttpClient == null) {
                okHttpClient = new OkHttpClient.Builder()
                        .addInterceptor(new HttpLoggingInterceptor())
                        .build();
            }
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .client(okHttpClient)
                    .addConverterFactory(convertFactory)
                    .addCallAdapterFactory(callAdapterFactory)
                    .build();
            return new OkHttp<>(retrofit.create(request));
        }
    }


}
