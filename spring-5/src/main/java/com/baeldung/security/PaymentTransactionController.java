package com.baeldung.security;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.security.Principal;

@RestController
public class PaymentTransactionController {

    private PaymentTransactionService greetService;

    public PaymentTransactionController(PaymentTransactionService greetService) {
        this.greetService = greetService;
    }

    @GetMapping("/")
    public Mono<String> greet(Mono<Principal> principal) {
        return principal
                .map(Principal::getName)
                .map(name -> String.format("Hello, %s", name));
    }

    @GetMapping("/admin")
    public Mono<String> greetAdmin(Mono<Principal> principal) {
        return principal
                .map(Principal::getName)
                .map(name -> String.format("Admin access: %s", name));
    }

    @GetMapping("/paymentTransactions/{id}")
    public Mono<PaymentTransaction> getPaymentTransactionCollection(@PathVariable String id) {
        Mono<PaymentTransaction> playerMono = greetService.getItem(new PaymentTransactionCollectionQuery(id));
        return playerMono;
    }

}
