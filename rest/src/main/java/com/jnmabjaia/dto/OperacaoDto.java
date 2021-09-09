package com.jnmabjaia.dto;

import lombok.Data;

import java.io.Serializable;

//@Data //Lombok para Getters e Setters
public class OperacaoDto implements Serializable {
    private String a;
    private String b;

    public OperacaoDto(String a, String b){
        this.a=a;
        this.b=b;
    }

    public String getA() {
        return a;
    }

    public String getB() {
        return b;
    }

    public void setA(String a) {
        this.a = a;
    }

    public void setB(String b) {
        this.b = b;
    }

}
