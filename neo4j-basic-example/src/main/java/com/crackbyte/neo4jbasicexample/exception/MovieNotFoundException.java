package com.crackbyte.neo4jbasicexample.exception;

public class MovieNotFoundException extends RuntimeException {
    public MovieNotFoundException(String title) {
        super(String.format("Could not find movie with title %s", title));

    }
}
