package com.allaroundjava.statements;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/statements")
@RequiredArgsConstructor
public class StatementController {
    private final WithdrawalRepository repository;

    @GetMapping("/{cardId}")
    public ResponseEntity<List<Operation>> getAllOperations(@PathVariable String cardId) {
        List<WithdrawalEntity> withdrawals = repository.findByCardId(cardId);
        List<Operation> operations = withdrawals.stream().map(Operation::new).collect(Collectors.toList());

        return ResponseEntity.ok(operations);
    }


}

@Getter
@Setter
@NoArgsConstructor
class Operation {
    String type;
    BigDecimal value;
    LocalDateTime when;

    Operation(WithdrawalEntity entity) {
        this.type = "Withdrawal";
        this.value = entity.getAmount();
        this.when = entity.getWhen();
    }
}