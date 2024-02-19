package com.idts.accountapi.controller.advice;

import com.idts.accountapi.customHandler.AccountNotFoundException;
import com.idts.accountapi.customHandler.NotEnoughFundsInAccountException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@Controller
public class NotEnoughFundsAdvice {
    @ResponseBody
    @ExceptionHandler(NotEnoughFundsInAccountException.class)
    @ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
    private String notEnoughFundsHandler(AccountNotFoundException exception) {
        return exception.getMessage();
    }
}
