package com.victor.cursospring.resources.exception;

import java.util.ArrayList;
import java.util.List;

public class ValidationError extends StandardError {
    private static final long serialVersionUID = 1L;
    
    private List<FieldMessage> errors = new ArrayList<>();

    public ValidationError(Integer status, String msg, Long timeStamp) {
        super(status, msg, timeStamp);
        // TODO Auto-generated constructor stub
    }

    /**
     * @return the list
     */
    public List<FieldMessage> getErrors() {
        return errors;
    }

    public void addError(String fieldName, String msg) {
        this.errors.add(new FieldMessage(fieldName, msg));
    }
    
}
