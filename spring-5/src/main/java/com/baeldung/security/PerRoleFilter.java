package com.baeldung.security;

public interface PerRoleFilter<T> {

    public String role();
    public T filter(T toBeFiltered, String contextOfRole);

}
