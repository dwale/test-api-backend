package com.calculator.api.resource;

import com.calculator.api.Response;
import com.calculator.api.bean.Result;
import com.calculator.api.CalculatorService;
import com.codahale.metrics.annotation.Timed;
import io.swagger.annotations.*;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;


@Path("/calculator")
@Api(value = "/calculator")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CalculatorResource {
    CalculatorService calculatorService = new CalculatorService();

    public CalculatorResource() {
    }

    @GET
    @Path("/login")
    @ApiOperation(value = "Logs in a user using credentials provided by end user",
            notes = "Logs in a user using credentials", response = String.class)
    public Response login() {

        Response response = new Response();
        response.setResult("I am that nigga you heard");
        return response;
    }


    @GET
    @Timed
    @Path("/add/{value1}/{value2}")
    public Result add(@PathParam("value1") Double value1, @PathParam("value2") Double value2) {
        return calculatorService.add(value1,value2);
    }

    @GET
    @Timed
    @Path("/subtract/{value1}/{value2}")
    public Result subtract(@PathParam("value1") Double value1, @PathParam("value2") Double value2) {
        return calculatorService.subtract(value1,value2);
    }

    @GET
    @Timed
    @Path("/multiply/{value1}/{value2}")
    public Result multiply(@PathParam("value1") Double value1, @PathParam("value2") Double value2) {
        return calculatorService.multiply(value1,value2);
    }

    @GET
    @Timed
    @Path("/divide/{value1}/{value2}")
    public Result divide(@PathParam("value1") Double value1, @PathParam("value2") Double value2) {
        return calculatorService.divide(value1,value2);
    }

    @GET
    @Timed
    @Path("/square/{value1}")
    public Result square(@PathParam("value1") Double value1) {

        return calculatorService.square(value1);
    }

    @GET
    @Timed
    @Path("/squareroot/{value1}")
    public Result squareroot(@PathParam("value1") Double value1) {

        return calculatorService.squareroot(value1);
    }

}
