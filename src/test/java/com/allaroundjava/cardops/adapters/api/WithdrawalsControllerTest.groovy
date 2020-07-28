package com.allaroundjava.cardops.adapters.api

import com.allaroundjava.cardops.common.command.Result
import com.allaroundjava.cardops.domain.ports.WithdrawCommand
import com.allaroundjava.cardops.domain.ports.WithdrawingService
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import spock.lang.Specification

import static org.hamcrest.CoreMatchers.containsString
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

class WithdrawalsControllerTest extends Specification {
    public static final String CARD_ID = "asd1"
    private MockMvc mockMvc
    private WithdrawingService withdrawing = Mock()
    private WithdrawalsController withdrawalsController = new WithdrawalsController(withdrawing)

    void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(withdrawalsController).build()
    }

//    def "Listing withdrawals for Nonexistent card"() {
//        when: "Card does not exist"
//        withdrawals.findAllByCardId(CARD_ID) >> []
//        then: "calling controller on that card is ok"
//        mockMvc.perform(get("/withdrawals/$CARD_ID")).andExpect(status().isOk())
//    }
//
//    def "Listing valid withdrawals"() {
//        when: "Card has withdrawals"
//        withdrawals.findAllByCardId(CARD_ID) >> [new WithdrawalRequest(1,1,300, Instant.now()),
//                                                 new WithdrawalRequest(2,1,100,Instant.now())]
//
//        then: "Withdrawals are listed"
//        mockMvc.perform(get("/withdrawals/${CARD_ID}"))
//                .andExpect(status().isOk())
//                .andExpect(content().string(containsString("300")))
//    }

    def "Withdrawing invalid amount form a card"() {
        when: "Withdrawing didn't go well"
        withdrawing.withdraw(_ as WithdrawCommand) >> Result.FAILURE

        then: "Bad Request"
        mockMvc.perform(post("/withdrawals/$CARD_ID").content('{"amount" : 200}').contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(HttpStatus.BAD_REQUEST.value()))
    }

    def "Withdrawing successfully"() {
        when: "Withdrawing successfully"
        withdrawing.withdraw(_ as WithdrawCommand) >> Result.SUCCESS

        then: "OK"
        mockMvc.perform(post("/withdrawals/$CARD_ID").content('{"amount" : 200}').contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("200"))).andReturn()

    }
}