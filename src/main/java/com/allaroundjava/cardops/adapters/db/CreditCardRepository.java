package com.allaroundjava.cardops.adapters.db;

import com.allaroundjava.cardops.domain.model.CreditCard;
import com.allaroundjava.cardops.domain.ports.CreditCardSnapshot;
import com.allaroundjava.cardops.domain.ports.CreditCardsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.function.Function;

@Component
@RequiredArgsConstructor
class CreditCardRepository implements CreditCardsRepository {
    final CreditCardInternalRepository creditCardInternalRepository;

    public Optional<CreditCard> findById(Long id) {
        return creditCardInternalRepository.findById(id).map(CreditCardEntity::toDomainModel);
    }

    public void save(CreditCardSnapshot creditCard) {
        CreditCardEntity entity = creditCardInternalRepository.findById(creditCard.getId())
                .map(updateExisting(creditCard))
                .orElse(createNew(creditCard));
        creditCardInternalRepository.save(entity);
    }

    private Function<CreditCardEntity, CreditCardEntity> updateExisting(CreditCardSnapshot creditCard) {
        return creditCardEntity -> creditCardEntity.update(creditCard);
    }

    private CreditCardEntity createNew(CreditCardSnapshot creditCard) {
        return CreditCardEntity.create(creditCard);
    }
}
