package com.crackbyte.neo4jbasicexample.controller;

import com.crackbyte.neo4jbasicexample.dto.MovieDTO;
import com.crackbyte.neo4jbasicexample.entity.Movie;
import com.crackbyte.neo4jbasicexample.service.MovieService;
import jakarta.inject.Inject;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Response;

@Path("movie")
@Produces("application/json")
public class MovieController {
    @Inject
    private MovieService movieService;
    @POST
    public Response addMovie(MovieDTO movieDTO){
        movieService.save(movieDTO);
        return Response.status(201).entity(null).build();
    }
}
