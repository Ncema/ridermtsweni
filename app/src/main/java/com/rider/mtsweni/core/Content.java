package com.rider.mtsweni.core;

public class Content {

    private final Status status;
    private final String msg;

    public Content(Status status, String msg) {
        this.status = status;
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }

    public static final Content LOADED;
    public static final Content LOADING;
    public static final Content FAILURE;

    static {
        LOADED = new Content(Status.SUCCESS, "success");
        LOADING = new Content(Status.RUNNING, "loading");
        FAILURE = new Content(Status.FAILED, "failure");
    }

    public enum Status {
        RUNNING,
        SUCCESS,
        FAILED
    }
}
