package com.baeldung.security;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.server.ServerRequest;
import reactor.core.publisher.Mono;

@RestController
public class PaymentTransactionController {


    private QueryModelFactory<PaymentTransactionCollectionQuery> queryModelFactory = new QueryModelFactory<>(PaymentTransactionCollectionQuery.class);
    private PaymentTransactionService paymentTransactionService;

    public PaymentTransactionController(PaymentTransactionService paymentTransactionService) {
        this.paymentTransactionService = paymentTransactionService;
    }


    @GetMapping("/paymentTransactions/{id}")
    public Mono<ResponseEntity<PaymentTransaction>> getPaymentTransactionItem(@PathVariable String id) {
        Mono<Response<PaymentTransaction>> playerMono = paymentTransactionService.getItem(new PaymentTransactionItemQuery(id));
        return playerMono.map(r ->

                r.getStatusCode() == 200 ?
                        new ResponseEntity<PaymentTransaction>(r.getBody(), HttpStatus.valueOf(r.getStatusCode())) :
                        new ResponseEntity<PaymentTransaction>(HttpStatus.valueOf(r.getStatusCode())));
    }


    @GetMapping("/paymentTransactions")
    public Mono<ResponseEntity<PaymentTransactionCollection>> getPaymentTransactionCollection(ServerHttpRequest httpRequest) {

        PaymentTransactionCollectionQuery collectionQuery = queryModelFactory.createQueryModel(httpRequest);
        Mono<Response<PaymentTransactionCollection>> playerMono = paymentTransactionService.getCollection(collectionQuery);
        return playerMono.map(r ->

                r.getStatusCode() == 200 ?
                        new ResponseEntity<PaymentTransactionCollection>(r.getBody(), HttpStatus.valueOf(r.getStatusCode())) :
                        new ResponseEntity<PaymentTransactionCollection>(HttpStatus.valueOf(r.getStatusCode())));
    }

}
