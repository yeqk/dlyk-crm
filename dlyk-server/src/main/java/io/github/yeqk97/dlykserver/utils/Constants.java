package io.github.yeqk97.dlykserver.utils;

import java.util.concurrent.TimeUnit;

public class Constants {

    public static final String REDIS_JWT_KEY = "dlyk:jwt:user:login:";

    public static final long REMEMBER_ME_EXPIRE_TIME = TimeUnit.DAYS.toSeconds(7);

    public static final long DEFAULT_EXPIRE_TIME = TimeUnit.MINUTES.toSeconds(30);

    public static final String LOGIN_URI = "/api/login";

    public static final String LOGOUT_URI = "/api/logout";

    public static final int PAGE_SIZE = 10;
}
