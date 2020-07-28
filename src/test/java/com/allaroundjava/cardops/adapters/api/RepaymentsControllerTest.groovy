package com.allaroundjava.cardops.adapters.api

import com.allaroundjava.cardops.common.command.Result
import com.allaroundjava.cardops.domain.model.ActiveCreditCard
import com.allaroundjava.cardops.domain.model.CardNumber
import com.allaroundjava.cardops.domain.ports.RepayCommand
import com.allaroundjava.cardops.domain.ports.RepaymentService
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import spock.lang.Specification

import static org.hamcrest.CoreMatchers.containsString
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

class RepaymentsControllerTest extends Specification {
    private RepaymentService repaying = Mock()
    private RepaymentsController repaymentController = new RepaymentsController(repaying)
    private MockMvc mockMvc

    void setup() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(repaymentController).build()
    }

    def "when failed to repay then bad request"() {
        when: "non existent card"
        String cardId = "12df"
        repaying.repay(_ as RepayCommand) >> Result.FAILURE
        then: "Repaying a card through controller request"
        mockMvc.perform(post("/creditcards/$cardId/repayments").content('{"amount" : 500}').contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(HttpStatus.BAD_REQUEST.value()))
    }

    def "Successfull Repayment"() {
        when: "Repaying a card"
        String cardId = "4rt5"
        repaying.repay(_ as RepayCommand) >> Result.SUCCESS

        then: "Repaying card successfully"
        mockMvc.perform(post("/creditcards/$cardId/repayments").content('{"amount" : 50}').contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(content().string(containsString("50")))
    }
}
