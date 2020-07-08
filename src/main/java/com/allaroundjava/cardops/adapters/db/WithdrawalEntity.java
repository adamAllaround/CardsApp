package com.allaroundjava.cardops.adapters.db;

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

@Entity
@Immutable
@Setter
@Getter
@Table(name = "WITHDRAWAL")
class WithdrawalEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

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
        return new Withdrawal(this.id, this.creditCard.id, this.amount, this.when);
    }
}
