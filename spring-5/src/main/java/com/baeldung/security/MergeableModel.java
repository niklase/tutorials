package com.baeldung.security;

public interface MergeableModel<T> {
    public T mergeWith(T mergeableModel);
}
