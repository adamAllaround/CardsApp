package com.allaroundjava.cardops.adapters.api;

import com.allaroundjava.cardops.domain.ports.WithdrawCommand;
import com.allaroundjava.cardops.domain.ports.WithdrawalsRepository;
import com.allaroundjava.cardops.domain.ports.WithdrawingService;
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
    private final WithdrawingService withdrawingService;
    private final WithdrawalsRepository withdrawalsRepository;

    @GetMapping("/{cardId}")
    ResponseEntity<Collection<Withdrawal>> listWithdrawals(@PathVariable Long cardId) {
        return ResponseEntity.ok(withdrawalsRepository.findAllByCardId(cardId)
                .stream()
                .map(Withdrawal::new)
                .collect(Collectors.toList()));
    }

    @PostMapping("/{cardId}")
    ResponseEntity<Withdrawal> withdraw(@PathVariable Long cardId, @RequestBody Withdrawal request) {
        Optional<com.allaroundjava.cardops.domain.model.Withdrawal> result = withdrawingService.withdraw(new WithdrawCommand(cardId, request.getAmount()));
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

    public Withdrawal(com.allaroundjava.cardops.domain.model.Withdrawal withdrawal) {
        this.id = withdrawal.getId();
        this.amount = withdrawal.getAmount();
        this.when = withdrawal.getWhen();
    }
}