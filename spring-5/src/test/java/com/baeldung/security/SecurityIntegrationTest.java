package com.baeldung.security;

import com.baeldung.SpringSecurity5Application;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.util.HashMap;
import java.util.Map;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = SpringSecurity5Application.class)
public class SecurityIntegrationTest {

    @Autowired
    ApplicationContext context;

    private WebTestClient rest;

    @Before
    public void setup() {
        this.rest = WebTestClient.bindToApplicationContext(this.context).configureClient().build();
    }


    @Test
    @WithMockUser(username="1234567", roles={"PLAYER"})
    public void whenHasCredentials_player() {
        this.rest.get().uri("/paymentTransactions/1").exchange().expectStatus().isOk().expectBody(String.class).isEqualTo(json("{'id':'1','amount':1090,'internalComment':null,'playerId':'1234567','operatorId':'A'}"));
    }


    @Test
    @WithMockUser(username="1234567", roles={"PLAYER"})
    public void whenHasCredentials_playerAndQueryMismatch() {
        this.rest.get().uri("/paymentTransactions/777").exchange().expectStatus().is4xxClientError();
    }


    @Test
    @WithMockUser(username="MrSupportUser", roles={"SUPPORT"})
    public void whenHasCredentials_support() {
        this.rest.get().uri("/paymentTransactions/1").exchange().expectStatus().isOk().expectBody(String.class).isEqualTo(
                json("{'id':'1','amount':1090,'internalComment':'Looks suspect!','playerId':'1234567','operatorId':'A'}"));
    }




    @Test
    @WithMockUser(username="1234567", roles={"PLAYER", "SUPPORT"})
    public void whenHasCredentials_playerAndSupport() {
        this.rest.get().uri("/paymentTransactions/1").exchange()
                .expectStatus().isOk()
                .expectBody(String.class)
                .isEqualTo(
                        json("{'id':'1','amount':1090,'internalComment':'Looks suspect!','playerId':'1234567','operatorId':'A'}"));
    }


    @Test
    @WithMockUser(username="1234567", roles={"PLAYER", "SUPPORT"})
    public void collection_whenHasCredentials_playerAndSupport() {
        this.rest.get().uri("/paymentTransactions?playerId=1234567").exchange().expectStatus().isOk().expectBody(String.class).isEqualTo(json("{'value':[{'id':'1','amount':1090,'internalComment':'Looks suspect!','playerId':'1234567','operatorId':'A'}]}"));
    }


    private String json(String json){
        return json.replace('\'', '\"');
    }



}
