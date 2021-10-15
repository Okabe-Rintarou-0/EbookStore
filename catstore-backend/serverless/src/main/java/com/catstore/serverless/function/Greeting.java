package com.catstore.serverless.function;

import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class Greeting implements Function<String, String> {
    @Override
    public String apply(String name) {
        return "Hello, " + name + "!";
    }
}
