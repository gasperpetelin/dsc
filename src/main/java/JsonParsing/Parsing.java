package JsonParsing;

import Input.Algorithm;
import Input.Problem;
import Input.Request;
import Output.Response;
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

    static public Response getResponse(String json)
    {
        return new Gson().fromJson(json, Response.class);
    }
}
