package com.example.demo.controller


import com.example.demo.dto.in.ShoeFilter
import com.example.demo.dto.out.stock.StockState
import org.hamcrest.Matcher
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.result.MockMvcResultHandlers
import spock.lang.Specification

import static org.hamcrest.Matchers.hasSize
import static org.hamcrest.Matchers.is
import static org.hamcrest.Matchers.oneOf
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class StockControllerIT extends Specification {

    @Autowired
    private MockMvc mockMvc

    def "should return OK response with stock"() {
        expect:
        mockMvc.perform(
            get("/stock")
                .header("version", 3)
        )
            .andDo(MockMvcResultHandlers.print())
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath('$').isMap())
            .andExpect(jsonPath('$.*', hasSize(2)))
            .andExpect(jsonPath('$.state').value(containsEnumValue(StockState.values())))
            .andExpect(jsonPath('$.shoes').isArray())
            .andExpect(jsonPath('$.shoes[0].*', hasSize(3)))
            .andExpect(jsonPath('$.shoes[0].quantity').isNumber())
            .andExpect(jsonPath('$.shoes[0].color').value(containsEnumValue(ShoeFilter.Color.values())))
            .andExpect(jsonPath('$.shoes[0].size').isNumber())
    }

    def "should return NOT FOUND response with empty body"(int version) {
        expect:
        mockMvc.perform(
            get("/stock")
                .header("version", version)
        )
            .andDo(MockMvcResultHandlers.print())
            .andExpect(status().isNotFound())
            .andExpect(jsonPath('$').isMap())
            .andExpect(jsonPath('$.status').value("NOT_FOUND"))
            .andExpect(jsonPath('$.path').value("/stock"))
            .andExpect(jsonPath('$.exception').value("ApiVersionNotFoundException"))

        where:
        version | _
        0 | _
        1 | _
        2 | _
    }

    private <E extends Enum<E>> Matcher<Object> containsEnumValue(E[] enumValues) {
        is(oneOf((enumValues as List).collect { it.toString() }.toArray()))
    }

}
