package com.crackbyte.neo4jbasicexample.service;

import com.crackbyte.neo4jbasicexample.config.Neo4jConfig;
import com.crackbyte.neo4jbasicexample.dto.MovieDTO;
import com.crackbyte.neo4jbasicexample.entity.Actor;
import com.crackbyte.neo4jbasicexample.entity.Movie;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.neo4j.ogm.session.Session;
import org.neo4j.ogm.session.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;


@ApplicationScoped
public class MovieService {
    private static final Logger log = LoggerFactory.getLogger(MovieService.class);
    private SessionFactory sessionFactory;
    @Inject
    private Neo4jConfig neo4jConfig;
    @Inject
    private ActorService actorService;

    @PostConstruct
    public void init() {
        this.sessionFactory = neo4jConfig.getSessionFactory();
    }

    public Optional<Movie> findMovieByTitle(String title) {
        Session session = sessionFactory.openSession();
        return Optional.ofNullable(
                session.queryForObject(Movie.class, "MATCH (m:Movie {title:$title}) return m", Map.of("title", title)));
    }

    public void save(MovieDTO movieDTO) {

        Session session = sessionFactory.openSession();
        Optional<Movie> movie = findMovieByTitle(movieDTO.getName());
        movie.ifPresentOrElse(oldMovie -> {
            throw new RuntimeException("Movie Already Exists");
        }, () -> {
            Movie newMovie = new Movie(movieDTO);
            Set<String> actors = movieDTO.getActors();
            if (actors != null && !actors.isEmpty()) {
                Set<Actor> actorSet = actors.stream().map(a -> {
                    Actor actor = actorService.findActorByName(a);
                    return Objects.requireNonNullElseGet(actor, () -> {
                        log.info("not found:{}", a);
                        return new Actor(a);
                    });
                }).collect(Collectors.toSet());
                log.info("Size:{}", actorSet);
                newMovie.addActors(actorSet);
            }
            session.save(newMovie);
        });

    }

    public List<Movie> allMovies() {
        Session session = sessionFactory.openSession();
        return new ArrayList<>(session.loadAll(Movie.class));
    }


}
