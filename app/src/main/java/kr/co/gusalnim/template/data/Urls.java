package kr.co.gusalnim.template.data;

import kr.co.gusalnim.template.BuildConfig;

public class Urls {
    public static final String API;
    public static final String THUMB;

    static {
        if (BuildConfig.BUILD_TYPE.equals("release")) {
            THUMB = "https://img-safe.seoulgas.co.kr/thumb/";
            API = "https://msc.seoulgas.co.kr/app/";
        } else {
            THUMB = "http://msc-dev.seoulgas.co.kr/thumb/";
            API = "https://msc-dev.seoulgas.co.kr/app/";
        }
    }
}
