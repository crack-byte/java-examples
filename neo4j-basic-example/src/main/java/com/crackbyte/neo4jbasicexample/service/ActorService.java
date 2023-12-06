package com.crackbyte.neo4jbasicexample.service;

import com.crackbyte.neo4jbasicexample.config.Neo4jConfig;
import com.crackbyte.neo4jbasicexample.entity.Actor;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.neo4j.ogm.session.Session;
import org.neo4j.ogm.session.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.Optional;
import java.util.Set;


@ApplicationScoped
public class ActorService {
    private static final Logger log = LoggerFactory.getLogger(ActorService.class);
    private SessionFactory sessionFactory;
    @Inject
    private Neo4jConfig neo4jConfig;

    @PostConstruct
    public void init() {
        this.sessionFactory = neo4jConfig.getSessionFactory();
    }

    public Actor findActorByName(String name) {
        Session session = sessionFactory.openSession();
        return session.queryForObject(Actor.class, "MATCH (a:Actor {name:$name}) return a", Map.of("name", name));
    }

    public Optional<Iterable<Actor>> findActorsByName(Set<String> names) {
        Session session = sessionFactory.openSession();
        return Optional.ofNullable(session.query(Actor.class, "MATCH (n:Actor) WHERE n.name in {names} RETURN n", Map.of("names", names)));
    }

    public void save(Actor actor) {
        Session session = sessionFactory.openSession();
        session.save(actor);
    }

}
