package com.banking.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BadRequestException extends Exception{
    private String errorMessage;
    private HttpStatus statusCode;
}
