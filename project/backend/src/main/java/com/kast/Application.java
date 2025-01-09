package com.kast;

import io.micronaut.runtime.Micronaut;

import java.net.URISyntaxException;

public class Application {
    public static void main(String[] args) {
        Micronaut.run(Application.class, args);
    }
}