package com.allaroundjava.cardops.adapters.api;

import com.allaroundjava.cardops.common.command.Result;
import com.allaroundjava.cardops.domain.model.CardNumber;
import com.allaroundjava.cardops.domain.ports.CreditCardSnapshot;
import com.allaroundjava.cardops.domain.ports.RepayCommand;
import com.allaroundjava.cardops.domain.ports.RepaymentService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.Value;
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
public class RepaymentsController {
    private final RepaymentService repaymentService;

    @PostMapping("/{cardId}/repayments")
    public ResponseEntity<RepaymentResponse> repay(@PathVariable String cardId, @RequestBody RepaymentRequest repayment) {
        Result result = repaymentService.repay(new RepayCommand(CardNumber.from(cardId), repayment.getAmount()));
        RepaymentResponse response = new RepaymentResponse(cardId, repayment.getAmount());
        if(result == Result.FAILURE) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        return ResponseEntity.ok(response);
    }
}

@Setter
@Getter
class CreditCard {
    CardNumber id;
    BigDecimal limit;
    BigDecimal currentAmount;

    public CreditCard() {
    }

    public CreditCard(CreditCardSnapshot creditCard) {
        this.id = creditCard.getId();
        this.limit = creditCard.getLimit();
        this.currentAmount = creditCard.getCurrentAmount();
    }
}

@Value
class RepaymentRequest {
    BigDecimal amount;
}

@Value
class RepaymentResponse {
    String cardNumber;
    BigDecimal amount;
}
