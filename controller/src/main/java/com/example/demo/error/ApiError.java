package com.example.demo.error;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.util.ServletRequestPathUtils;

@Data
class ApiError {

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSZ")
    private OffsetDateTime      timestamp;
    private HttpStatus          status;
    private String              path;
    private String              exception;
    private String              message;
    private String              detailMessage;
    @Setter(value = AccessLevel.PRIVATE)
    private List<ApiInnerError> innerErrors;

    private ApiError() {
        timestamp = OffsetDateTime.now();
    }

    ApiError(HttpStatus status, WebRequest webRequest) {
        this();
        this.status = status;
        setPath(webRequest);
    }

    ApiError(HttpStatus status, WebRequest webRequest, Throwable ex) {
        this(status, webRequest);
        this.exception = ex.getClass().getSimpleName();
        setMessage(ex);
        setDetailedMessage(ex);
    }

    ApiError(HttpStatus status, String message, WebRequest webRequest, Throwable ex) {
        this(status, webRequest);
        this.exception = ex.getClass().getSimpleName();
        this.message   = message;
    }

    private void setPath(WebRequest webRequest) {
        Optional.ofNullable(webRequest.getAttribute(ServletRequestPathUtils.PATH_ATTRIBUTE, RequestAttributes.SCOPE_REQUEST))
            .ifPresent(path -> this.path = path.toString());
    }

    private void setMessage(Throwable ex) {
        Optional.ofNullable(ex.getMessage())
            .ifPresentOrElse(message -> this.message = message, () -> this.message = "Unexpected error");
    }

    private void setDetailedMessage(Throwable ex) {
        Optional.ofNullable(ex.getLocalizedMessage())
            .ifPresent(detailMessage -> {
                if (!Objects.equals(this.message, detailMessage)) {
                    this.detailMessage = detailMessage;
                }
            });
    }

    public void addInnerError(ApiInnerError apiInnerError) {
        if (innerErrors == null) {
            innerErrors = new ArrayList<>();
        }

        innerErrors.add(apiInnerError);
    }
}
