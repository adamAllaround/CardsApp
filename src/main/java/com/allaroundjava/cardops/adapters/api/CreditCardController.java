package com.allaroundjava.cardops.adapters.api;

import com.allaroundjava.cardops.domain.ports.RepayCommand;
import com.allaroundjava.cardops.domain.ports.Repaying;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
@RequestMapping("/creditcards")
@RequiredArgsConstructor
public class CreditCardController {
    private final Repaying repaying;

    @PostMapping("/{cardId}/repayments")
    public ResponseEntity<CreditCard> repay(@PathVariable Long cardId, @RequestBody RepaymentRequest repayment) {
        return repaying.repay(new RepayCommand(cardId, repayment.getAmount()))
                .map(CreditCard::new)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.BAD_REQUEST).build());
    }
}

@Setter
@Getter
class CreditCard {
    Long id;
    BigDecimal limit;
    BigDecimal currentAmount;

    public CreditCard() {
    }

    public CreditCard(com.allaroundjava.cardops.domain.model.CreditCard creditCard) {
        this.id = creditCard.getId();
        this.limit = creditCard.getLimit();
        this.currentAmount = creditCard.getCurrentAmount();
    }
}

@Getter
@Setter
class RepaymentRequest {
    BigDecimal amount;

    public RepaymentRequest() {
    }
}
