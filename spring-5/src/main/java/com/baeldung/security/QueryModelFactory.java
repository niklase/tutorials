package com.baeldung.security;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.springframework.http.server.reactive.ServerHttpRequest;

import java.util.Map;

public class QueryModelFactory<Model> {

    private Class modelClass;
    private ObjectMapper objectMapper;

    public static <Model> QueryModelFactory<Model> getFactory(Class<Model> clazz) {
        return (QueryModelFactory<Model>) new QueryModelFactory(clazz);
    }

    public QueryModelFactory(Class modelClass) {
        this.modelClass = modelClass;
        objectMapper = new ObjectMapper();
        Module module = new SimpleModule();

        objectMapper.configure(JsonParser.Feature.IGNORE_UNDEFINED, false);

    }

    public Model createQueryModel(ServerHttpRequest httpRequest) {
        return createModel(httpRequest.getQueryParams().toSingleValueMap());
    }

    protected Model createModel(Map<String, String> modelAsMap){
        return (Model) objectMapper.convertValue(modelAsMap, modelClass);
    }
}
