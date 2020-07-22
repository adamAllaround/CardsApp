package com.allaroundjava.cardops.adapters.db;

import com.allaroundjava.cardops.domain.model.CardNumber;
import com.allaroundjava.cardops.domain.model.Withdrawal;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Immutable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Entity
@Immutable
@Setter
@Getter
@Table(name = "WITHDRAWAL")
class WithdrawalEntity {

    @Id
    private UUID id;

    private BigDecimal amount;
    private Instant when;
    @ManyToOne
    private CreditCardEntity creditCard;

    static WithdrawalEntity create(CreditCardEntity creditCardEntity, Withdrawal withdrawal) {
        WithdrawalEntity entity = new WithdrawalEntity();
        entity.amount = withdrawal.getAmount();
        entity.when = withdrawal.getWhen();
        entity.creditCard = creditCardEntity;
        return entity;
    }

    public Withdrawal toDomainModel() {
        return new Withdrawal(creditCard.id , this.creditCard.id, this.amount, this.when);
    }
}
