package com.victor.cursospring.dto;

import java.io.Serializable;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

public class EmailDTO implements Serializable {
    private static final long serialVersionUID = 1L;
    
    @NotEmpty(message="Preenchimento obrigatório")
    @Email(message="Email obrigatório")
    private String email;
    
    public EmailDTO() {}

    /**
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email the email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }
    
}
