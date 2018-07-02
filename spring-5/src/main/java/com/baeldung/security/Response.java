package com.baeldung.security;

public class Response<T> {

    private int statusCode;
    private T body;
    private Object errorBody;

    public Response(T body, int statusCode) {
        this.body = body;
        this.statusCode = statusCode;
    }

    public Response(Class<T> clazz, Object errorBody, int statusCode) {

        this.body = null;
        this.errorBody = errorBody;
        this.statusCode = statusCode;
    }



    public T getBody() {
        return body;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public Object getErrorBody() {
        return errorBody;
    }
}
