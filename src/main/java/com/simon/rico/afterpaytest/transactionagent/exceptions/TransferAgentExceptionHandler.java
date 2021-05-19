package com.simon.rico.afterpaytest.transactionagent.exceptions;


import static org.springframework.http.HttpStatus.BAD_REQUEST;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.simon.rico.afterpaytest.transactionagent.dto.CommonResponseDto;

@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class TransferAgentExceptionHandler extends ResponseEntityExceptionHandler {




    @ExceptionHandler({TransferException.class})
    protected ResponseEntity<Object> handleTransferException(
            TransferException ex, WebRequest request) {
        String error = ex.getMessage();
        return buildResponseEntity(new CommonResponseDto(BAD_REQUEST.value(), error));
    }




    private ResponseEntity<Object> buildResponseEntity(CommonResponseDto commonResponse) {
        return new ResponseEntity<>(commonResponse, HttpStatus.valueOf(commonResponse.getStatus()));
    }

}