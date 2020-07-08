package com.allaroundjava.cardops.adapters.api;

import com.allaroundjava.cardops.model.ports.WithdrawCommand;
import com.allaroundjava.cardops.model.ports.Withdrawals;
import com.allaroundjava.cardops.model.ports.Withdrawing;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/withdrawals")
@RequiredArgsConstructor
class WithdrawalsController {
    private final Withdrawing withdrawing;
    private final Withdrawals withdrawals;

    @GetMapping("/{cardId}")
    ResponseEntity<Collection<Withdrawal>> listWithdrawals(@PathVariable Long cardId) {
        return ResponseEntity.ok(withdrawals.findAllByCardId(cardId)
                .stream()
                .map(Withdrawal::new)
                .collect(Collectors.toList()));
    }

    @PostMapping("/{cardId}")
    ResponseEntity<Withdrawal> withdraw(@PathVariable Long cardId, @RequestBody Withdrawal request) {
        Optional<com.allaroundjava.cardops.model.domain.Withdrawal> result = withdrawing.withdraw(new WithdrawCommand(cardId, request.getAmount()));
        return result.map(Withdrawal::new)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.BAD_REQUEST).build());
    }

}

@Getter
@Setter
class Withdrawal {
    Long id;
    BigDecimal amount;
    Instant when;

    public Withdrawal() {}

    public Withdrawal(com.allaroundjava.cardops.model.domain.Withdrawal withdrawal) {
        this.id = withdrawal.getId();
        this.amount = withdrawal.getAmount();
        this.when = withdrawal.getWhen();
    }
}