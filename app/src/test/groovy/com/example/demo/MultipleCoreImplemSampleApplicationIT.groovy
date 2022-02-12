package com.example.demo

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.ApplicationContext
import spock.lang.Specification

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class MultipleCoreImplemSampleApplicationIT extends Specification {

    @Autowired(required = false)
    private ApplicationContext context

    def "when application runs the context is loaded"() {
        expect: "the context is created"
        context
    }
}
