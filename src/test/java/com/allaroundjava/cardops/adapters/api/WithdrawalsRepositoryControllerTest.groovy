package com.allaroundjava.cardops.adapters.api

import com.allaroundjava.cardops.domain.ports.WithdrawCommand
import com.allaroundjava.cardops.domain.ports.WithdrawalsRepository
import com.allaroundjava.cardops.domain.ports.WithdrawingService
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import spock.lang.Specification

import java.time.Instant

import static org.hamcrest.CoreMatchers.containsString
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

class WithdrawalsRepositoryControllerTest extends Specification {
    public static final long CARD_ID = 1L
    private MockMvc mockMvc
    private WithdrawingService withdrawing = Mock()
    private WithdrawalsRepository withdrawals = Mock()
    private WithdrawalsController withdrawalsController = new WithdrawalsController(withdrawing, withdrawals)

    void setup() {
        mockMvc = new MockMvcBuilders().standaloneSetup(withdrawalsController).build()
    }

    def "Listing withdrawals for Nonexistent card"() {
        when: "Card does not exist"
        withdrawals.findAllByCardId(CARD_ID) >> []
        then: "calling controller on that card is ok"
        mockMvc.perform(get("/withdrawals/$CARD_ID")).andExpect(status().isOk())
    }

    def "Listing valid withdrawals"() {
        when: "Card has withdrawals"
        withdrawals.findAllByCardId(CARD_ID) >> [new Withdrawal(1,1,300, Instant.now()),
                                                 new Withdrawal(2,1,100,Instant.now())]

        then: "Withdrawals are listed"
        mockMvc.perform(get("/withdrawals/${CARD_ID}"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("300")))
    }

    def "Withdrawing invalid amount form a card"() {
        when : "Withdrawing didn't go well"
        withdrawing.withdraw(_ as WithdrawCommand) >> Optional.empty()

        then: "Bad Request"
        mockMvc.perform(post("/withdrawals/$CARD_ID").content('{"amount" : 200}').contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(HttpStatus.BAD_REQUEST.value()))
    }

    def "Withdrawing succeccsully"() {
        when: "Withdrawing successfully"
        withdrawing.withdraw(_ as WithdrawCommand) >> Optional.of(new com.allaroundjava.cardops.domain.model.Withdrawal(1,1,200,Instant.now()))

        then: "OK"
        mockMvc.perform(post("/withdrawals/$CARD_ID").content('{"amount" : 200}').contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(content().string(containsString("200")))
    }

}
