package com.allaroundjava.cardops.adapters.api;

import com.allaroundjava.cardops.common.command.Result;
import com.allaroundjava.cardops.domain.ports.WithdrawCommand;
import com.allaroundjava.cardops.domain.ports.WithdrawingService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/withdrawals")
@RequiredArgsConstructor
class WithdrawalsController {
    private final WithdrawingService withdrawingService;
//    private final WithdrawalsRepository withdrawalsRepository;
//
//    @GetMapping("/{cardId}")
//    ResponseEntity<Collection<Withdrawal>> listWithdrawals(@PathVariable Long cardId) {
//        return ResponseEntity.ok(withdrawalsRepository.findAllByCardId(cardId)
//                .stream()
//                .map(Withdrawal::new)
//                .collect(Collectors.toList()));
//    }

    @PostMapping("/{cardNumber}")
    ResponseEntity<WithdrawalResponse> withdraw(@PathVariable String cardNumber, @RequestBody WithdrawalRequest request) {
        Result result = withdrawingService.withdraw(new WithdrawCommand(cardNumber, request.getAmount()));
        if (result == Result.FAILURE) {
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