package com.example.demo.error;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
class ApiValidationError extends ApiInnerError {

    private String object;
    private String field;
    private String message;
    private Object rejectedValue;

    ApiValidationError(String object, String message) {
        this.object = object;
        this.message = message;
    }
}
