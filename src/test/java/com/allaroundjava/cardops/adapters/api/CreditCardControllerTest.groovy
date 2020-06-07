package com.allaroundjava.cardops.adapters.api

import com.allaroundjava.cardops.model.ActiveCreditCard
import com.allaroundjava.cardops.ports.RepayCommand
import com.allaroundjava.cardops.ports.Repaying
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import spock.lang.Specification

import static org.hamcrest.CoreMatchers.containsString
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

class CreditCardControllerTest extends Specification {
    private Repaying repaying = Mock()
    private CreditCardController repaymentController = new CreditCardController(repaying)
    private MockMvc mockMvc

    void setup() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(repaymentController).build()
    }

    def "when Card not exists then Bad Request"() {
        when: "non existent card"
        Long cardId = 1
        repaying.repay(_ as RepayCommand) >> Optional.empty()
        then: "Repaying a card through controller request"
        mockMvc.perform(post("/creditcards/$cardId/repayments").content('{"amount" : 500}').contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(HttpStatus.BAD_REQUEST.value()))
    }

    def "When Overpaying then Bad Request"() {

    }

    def "Successfull Repayment"() {
        when: "Repaying a card"
        Long cardId = 1
        repaying.repay(_ as RepayCommand) >> Optional.of(new ActiveCreditCard(1, 100, -50))

        then: "Repaying card successfully"
        mockMvc.perform(post("/creditcards/$cardId/repayments").content('{"amount" : 50}').contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(content().string(containsString("100")))
    }
}
