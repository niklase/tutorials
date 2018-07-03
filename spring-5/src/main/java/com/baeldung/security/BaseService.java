package com.baeldung.security;

import org.springframework.beans.factory.annotation.Autowired;
import reactor.core.publisher.Mono;

import java.util.List;

public abstract class BaseService<ItemModel extends MergeableModel, ItemQuery, CollectionModel extends MergeableModel, CollectionQuery> {

    private ResponseFilter<ItemModel> itemResponseFilter;
    private ResponseFilter<CollectionModel> collectionResponseFilter;
    private ServiceAccessController serviceAccessController;
    private TestRepository repository;


    public BaseService(@Autowired List<PerRoleFilter<ItemModel>> itemFilters,
                       @Autowired List<PerRoleFilter<CollectionModel>> collectionFilters,
                       @Autowired ServiceAccessController serviceAccessController,
                       @Autowired TestRepository repository) {
        itemResponseFilter = new ResponseFilter(itemFilters);
        collectionResponseFilter = new ResponseFilter(collectionFilters);
        this.serviceAccessController = serviceAccessController;
        this.repository = repository;
    }

    public Mono<Response<ItemModel>> getItem(ItemQuery itemQuery) {

        Mono<Boolean> hasQueryPermissionMono = serviceAccessController.authorizeQuery(itemQuery);

        return hasQueryPermissionMono.flatMap(hasQueryPermission ->
                !hasQueryPermission ?
                        createItemResponseForbidden():
                        filterItemResponse(readItemUnprotected(itemQuery)));

    }

    public Mono<Response<CollectionModel>> getCollection(CollectionQuery collectionQuery) {

        Mono<Boolean> hasQueryPermissionMono = serviceAccessController.authorizeQuery(collectionQuery);

        return hasQueryPermissionMono.flatMap(hasQueryPermission ->
                !hasQueryPermission ?
                        createCollectionResponseForbidden():
                        filterCollectionResponse(readCollectionUnprotected(collectionQuery)));

    }


    private Mono<Response<ItemModel>> createItemResponseForbidden() {
        return Mono.just(new Response(null, "Query permission error...", 403));
    }

    private Mono<Response<CollectionModel>> createCollectionResponseForbidden() {
        return Mono.just(new Response(null, "Query permission error...", 403));
    }


    private Mono<Response<ItemModel>> filterItemResponse(Mono<Response<ItemModel>> businessResponse ) {

        Mono<ItemModel> filteredBodyMono = businessResponse.flatMap(response -> itemResponseFilter.filterByAuthorization(response.getBody()));
        Mono<Response<ItemModel>> result =  filteredBodyMono.map(r -> new Response(r, 200));
        return result;
    }

    private Mono<Response<CollectionModel>> filterCollectionResponse(Mono<Response<CollectionModel>> businessResponse ) {

        Mono<CollectionModel> filteredBodyMono = businessResponse.flatMap(response -> collectionResponseFilter.filterByAuthorization(response.getBody()));
        Mono<Response<CollectionModel>> result =  filteredBodyMono.map(r -> new Response(r, 200));
        return result;
    }

    public TestRepository getRepository() {
        return repository;
    }

    abstract protected Mono<Response<ItemModel>> readItemUnprotected(ItemQuery itemQuery);
    abstract protected Mono<Response<CollectionModel>> readCollectionUnprotected(CollectionQuery collectionQuery);
}
