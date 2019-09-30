package com.example.oving3;

public class Person {
    private String navn;
    private String fodselsdato;

    public Person(String navn, String fodeselsdato) {
        this.navn = navn;
        this.fodselsdato = fodeselsdato;
    }

    public String getNavn() {
        return this.navn;
    }

    public String getFodselsdato() {
        return this.fodselsdato;
    }
}
