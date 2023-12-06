package com.crackbyte.neo4jbasicexample.entity;

import com.crackbyte.neo4jbasicexample.dto.MovieDTO;
import lombok.Getter;
import lombok.Setter;
import org.neo4j.ogm.annotation.*;

import java.util.HashSet;
import java.util.Set;
@Getter
@Setter
@NodeEntity
public class Movie {

    @Id
    @GeneratedValue
    Long id;

    @Property(name="title")
    private String name;

    @Property(name="releasedIn")
    private String releaseYear;

    @Relationship(type = "HAS_ACTOR")
    private Set<Actor> actors = new HashSet<>();

    public Movie() {
    }

    public Movie(MovieDTO movieDTO) {
        this.name = movieDTO.getName();
        this.releaseYear = movieDTO.getReleaseYear();
    }

    public Movie(String name) {
        this.name = name;
    }

    public Movie(String name, String releaseYear) {
        this.name = name;
        this.releaseYear = releaseYear;
    }

    public void hasActor(Actor actor) {
        actors.add(actor);
        actor.getMovies().add(this);
    }

    public void addActors(Set<Actor> actors) {
        for (Actor actor : actors) {
            this.actors.add(actor);
            actor.getMovies().add(this);
        }
    }
}
