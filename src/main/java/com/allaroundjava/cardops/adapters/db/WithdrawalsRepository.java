package com.allaroundjava.cardops.adapters.db;

import com.allaroundjava.cardops.model.CreditCardDatabaseException;
import com.allaroundjava.cardops.model.Withdrawal;
import com.allaroundjava.cardops.ports.Withdrawals;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
class WithdrawalsRepository implements Withdrawals {
    private final WithdrawalsInternalRepository withdrawalsInternalRepository;
    private final CreditCardInternalRepository creditCardInternalRepository;
    @Override
    public void publish(Withdrawal withdrawal) {
        CreditCardEntity creditCardEntity = creditCardInternalRepository
                .findById(withdrawal.getCreditCardId())
                .orElseThrow(CreditCardDatabaseException::new);

        withdrawalsInternalRepository.save(createNew(creditCardEntity, withdrawal));
    }

    private WithdrawalEntity createNew(CreditCardEntity creditCardEntity, Withdrawal withdrawal) {
        return WithdrawalEntity.create(creditCardEntity, withdrawal);
    }

    @Override
    public Collection<Withdrawal> findAllByCardId(Long cardId) {
        return withdrawalsInternalRepository.findAllByCreditCardId(cardId)
                .stream()
                .map(WithdrawalEntity::toDomainModel)
                .collect(Collectors.toList());
    }
}
