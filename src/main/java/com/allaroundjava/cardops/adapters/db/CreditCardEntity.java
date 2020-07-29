package com.allaroundjava.cardops.adapters.db;

import com.allaroundjava.cardops.domain.model.ActiveCreditCard;
import com.allaroundjava.cardops.domain.model.CreditCard;
import com.allaroundjava.cardops.domain.model.InactiveCreditCard;
import com.allaroundjava.cardops.domain.ports.CreditCardSnapshot;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "CREDIT_CARD")
@Getter
@Setter
class CreditCardEntity {
    enum CreditCardState {
        ACTIVE, INACTIVE
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    String id;

    @CreationTimestamp
    LocalDateTime createdAt;

    @Enumerated(EnumType.STRING)
    CreditCardState state;

    BigDecimal limitAmount;

    BigDecimal currentAmount;

    CreditCard toDomainModel() {
        switch (state) {
            case ACTIVE:
                return toActiveCard();
            case INACTIVE:
                return toInactiveCard();
            default:
                throw new CreditCardDatabaseException();
        }
    }

    private CreditCard toActiveCard() {
        return new ActiveCreditCard(id, limitAmount, currentAmount);
    }

    private CreditCard toInactiveCard() {
        return new InactiveCreditCard(id);
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
