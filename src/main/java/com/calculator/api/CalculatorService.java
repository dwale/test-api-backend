package com.calculator.api;

import com.calculator.api.bean.Result;

/**
 * Created by sq_09 on 1/17/2018.
 */
public class CalculatorService {

    public Result add (Double value1, Double value2) {
        Double answer = value1 + value2;

        Result result = new Result();
        result.setAnswer(answer);
        return result;
    }

    public Result subtract (Double value1, Double value2) {
        Double answer = value1 - value2;

        Result result = new Result();
        result.setAnswer(answer);
        return result;
    }

    public Result multiply (Double value1, Double value2) {
        Double answer = value1 * value2;

        Result result = new Result();
        result.setAnswer(answer);
        return result;
    }

    public Result divide (Double value1, Double value2) {
        Double answer = value1 / value2;

        Result result = new Result();
        result.setAnswer(answer);
        return result;
    }

    public Result square (Double value1) {
        Double answer = value1 * value1;

        Result result = new Result();
        result.setAnswer(answer);
        return result;
    }

    public Result squareroot (Double value1) {
        Double answer = Math.sqrt(value1);

        Result result = new Result();
        result.setAnswer(answer);
        return result;
    }

}
