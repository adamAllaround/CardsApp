package com.allaroundjava.cardops.adapters.api;

import com.allaroundjava.cardops.common.command.Result;
import com.allaroundjava.cardops.domain.ports.WithdrawCommand;
import com.allaroundjava.cardops.domain.ports.WithdrawingService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/creditcards")
@RequiredArgsConstructor
@Log4j2
class WithdrawalsController {
    private final WithdrawingService withdrawingService;

    @PostMapping("/{cardNumber}/withdrawals")
    ResponseEntity<WithdrawalResponse> withdraw(@PathVariable String cardNumber, @RequestBody WithdrawalRequest request) {
        log.debug(String.format("Received request to withdraw %.2f from card %s", request.amount, cardNumber));
        Result result = withdrawingService.withdraw(new WithdrawCommand(cardNumber, request.getAmount()));
        if (result == Result.FAILURE) {
            log.debug(String.format("Withdrawal attempt failed for card %s", cardNumber));
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        return ResponseEntity.ok(new WithdrawalResponse(cardNumber, request.amount));
    }
}

@Getter
@Setter
class WithdrawalRequest {
    BigDecimal amount;

    public WithdrawalRequest() {}
}

@Getter
class WithdrawalResponse {
    private String cardNumber;
    private BigDecimal amount;

    WithdrawalResponse(String cardNumber, BigDecimal amount) {

        this.cardNumber = cardNumber;
        this.amount = amount;
    }
}