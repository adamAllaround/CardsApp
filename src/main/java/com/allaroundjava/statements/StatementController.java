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
    private final OperationRepository repository;

    @GetMapping("/{cardId}")
    public ResponseEntity<List<OperationView>> getAllOperations(@PathVariable String cardId) {
        List<OperationEntity> withdrawals = repository.findByCardId(cardId);
        List<OperationView> operations = withdrawals.stream().map(OperationView::new).collect(Collectors.toList());

        return ResponseEntity.ok(operations);
    }


}

@Getter
@Setter
@NoArgsConstructor
class OperationView {
    String type;
    BigDecimal value;
    LocalDateTime when;

    OperationView(OperationEntity entity) {
        this.type = entity.getType();
        this.value = entity.getAmount();
        this.when = entity.getWhen();
    }
}