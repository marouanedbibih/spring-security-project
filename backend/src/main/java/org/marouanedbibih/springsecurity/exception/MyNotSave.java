package org.marouanedbibih.springsecurity.exception;
import java.util.List;

import org.marouanedbibih.springsecurity.handler.errors.MyErrRes;
import org.marouanedbibih.springsecurity.handler.errors.MyErrorField;



public class MyNotSave extends RuntimeException {
    private MyErrRes response;

    public MyNotSave(String message) {
        super(message);
        this.response = MyErrRes.builder().message(message).build();
    }

    public MyNotSave(MyErrRes response) {
        this.response = response;
    }

    public MyErrRes getResponse() {
        return response;
    }

    public MyNotSave(String message, String field) {
        super(message);
        List<MyErrorField> errors = List.of(MyErrorField.builder().field(field).message(message).build());
        this.response = MyErrRes.builder().errors(errors).build();
    }
}