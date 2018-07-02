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
    public void whenNoCredentials_thenRedirectToLogin() {
        this.rest.get().uri("/").exchange().expectStatus().is3xxRedirection();
    }

    @Test
    @WithMockUser
    public void whenHasCredentials_thenSeesGreeting() {
        this.rest.get().uri("/").exchange().expectStatus().isOk().expectBody(String.class).isEqualTo("Hello, user");
    }

    @Test
    @WithMockUser(username="1234567", roles={"PLAYER"})
    public void whenHasCredentials_player() {
        this.rest.get().uri("/paymentTransactions/1234567").exchange().expectStatus().isOk().expectBody(String.class).isEqualTo(json("{'id':'1234567','amount':10980,'internalComment':null}"));
    }

    @Test
    @WithMockUser(username="1234567", roles={"PLAYER"})
    public void whenHasCredentials_playerAndQueryMismatch() {
        this.rest.get().uri("/paymentTransactions/777").exchange().expectStatus().is4xxClientError();
    }

    @Test
    @WithMockUser(username="MrSupportUser", roles={"SUPPORT"})
    public void whenHasCredentials_support() {
        this.rest.get().uri("/paymentTransactions/1234567").exchange().expectStatus().isOk().expectBody(String.class).isEqualTo(json("{'id':'1234567','amount':10980,'internalComment':'This transaction looks fishy'}"));
    }

    @Test
    @WithMockUser(username="1234567", roles={"PLAYER", "SUPPORT"})
    public void whenHasCredentials_playerAndSupport() {
        this.rest.get().uri("/paymentTransactions/1234567").exchange().expectStatus().isOk().expectBody(String.class).isEqualTo(json("{'id':'1234567','amount':10980,'internalComment':'This transaction looks fishy'}"));
    }



    private String json(String json){
        return json.replace('\'', '\"');
    }


    @Test
    public void MergeMap(){
        Map<Long, Map<String, Double>> map = new HashMap<>();
        Map<String, Double> map1 = new HashMap<>();
        Map<String, Double> map2 = new HashMap<>();

        map1.put("1key1", 1.0);
        map1.put("1key2", 2.0);
        map1.put("1key3", 3.0);

        map2.put("2key1", 4.0);
        map2.put("2key2", 5.0);
        map2.put("2key3", 6.0);

        map.merge(222L, map1, (m1, m2) -> {m1.putAll(m2);return m1;});
        map.merge(222L, map2, (m1, m2) -> {m1.putAll(m2);return m1;});
        System.out.println(map);
    }
}
