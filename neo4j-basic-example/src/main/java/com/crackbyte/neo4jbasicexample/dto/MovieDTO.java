package com.crackbyte.neo4jbasicexample.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
public class MovieDTO {
    private String name;
    private String releaseYear;
    private Set<String> actors = new HashSet<>();

}
