package com.crackbyte.neo4jbasicexample.exception;

public class ActorNotFoundException extends RuntimeException {
    public ActorNotFoundException(String title) {
        super(String.format("Could not find actor with name %s", title));

    }
}
