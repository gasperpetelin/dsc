package JsonParsing;

import Input.Algorithm;
import Input.Problem;
import Input.Request;
import com.google.gson.Gson;

public class Parsing
{
    static public Problem getProblem(String json)
    {
        return new Gson().fromJson(json, Problem.class);
    }

    static public Algorithm getAlgorithm(String json)
    {
        return new Gson().fromJson(json, Algorithm.class);
    }

    static public Request getRequest(String json)
    {
        return new Gson().fromJson(json, Request.class);
    }
}
