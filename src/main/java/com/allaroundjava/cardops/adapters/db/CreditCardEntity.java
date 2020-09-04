package com.allaroundjava.cardops.adapters.db;

import com.allaroundjava.cardops.domain.model.CreditCard;
import com.allaroundjava.cardops.domain.ports.CreditCardSnapshot;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Document(collection = "creditcard")
@Getter
@Setter
class CreditCardEntity {
    enum CreditCardState {
        ACTIVE, INACTIVE
    }

    @Id
    private String id;

    private LocalDateTime createdAt;

    private CreditCardState state;

    private BigDecimal limitAmount;

    private BigDecimal currentAmount;

    CreditCard toDomainModel() {
        switch (state) {
            case ACTIVE:
                return CreditCard.active(id, limitAmount, currentAmount);
            case INACTIVE:
                return CreditCard.inactive(id);
            default:
                throw new CreditCardDatabaseException();
        }
    }

    private static CreditCardState getState(CreditCardSnapshot creditCard) {
        if(creditCard.isActive()) {
            return CreditCardState.ACTIVE;
        } else {
            return CreditCardState.INACTIVE;
        }
    }

    static CreditCardEntity create(CreditCardSnapshot creditCard) {
        CreditCardEntity entityInstance = new CreditCardEntity();
        entityInstance.setState(getState(creditCard));
        entityInstance.setCurrentAmount(creditCard.getCurrentAmount());
        entityInstance.setLimitAmount(creditCard.getLimit());
        return entityInstance;
    }

    CreditCardEntity update(CreditCardSnapshot creditCard) {
        this.state = getState(creditCard);
        this.setCurrentAmount(creditCard.getCurrentAmount());
        this.setLimitAmount(creditCard.getLimit());
        return this;
    }
}
