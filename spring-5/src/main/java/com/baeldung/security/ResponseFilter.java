package com.baeldung.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.userdetails.User;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ResponseFilter<T extends MergeableModel> {

    private List<PerRoleFilter<T>> filters;

    public ResponseFilter(List<PerRoleFilter<T>> filters) {
        this.filters = filters;
    }


    public Mono<T> filterByAuthorization(T mergeable){
        Mono<T> result = null;
        if (mergeable == null) {
            result = Mono.empty();
        } else {
            result = Mono.just(mergeable)
                    .flatMap(p -> ReactiveSecurityContextHolder
                            .getContext()
                            .map(context ->
                                    filterByAuthorization(mergeable, context)
                            ));
        }
        return result;
    }

    public T filterByAuthorization(T mergeable, SecurityContext securityContext) {
        T result = null;

        User user = (User) securityContext.getAuthentication().getPrincipal();
        for (PerRoleFilter<T> filter: getFilters(securityContext.getAuthentication().getAuthorities())){
            T filtered = filter.filter(mergeable, user.getUsername());
            if (result == null) {
                result = filtered;
            } else {
                result = (T) result.mergeWith(filtered);
            }
        }
        return result;
    }


    private List<PerRoleFilter<T>> getFilters(Collection<? extends GrantedAuthority> grantedAuthorities) {

        List filtersOfRole = new ArrayList();

        for (GrantedAuthority grantedAuthority: grantedAuthorities){
            for (PerRoleFilter responseFilter: filters) {
                if (responseFilter.role().equals(grantedAuthority.getAuthority())) {
                    filtersOfRole.add(responseFilter);
                }
            }
        }
        return filtersOfRole;
    }
}
