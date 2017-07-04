package DSC2;

import Input.Request;
import JsonParsing.Parsing;
import Output.Response;
import org.junit.jupiter.api.Test;
import sun.font.TrueTypeFont;

import java.security.cert.TrustAnchor;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;


class DSC2AlgorithmTest
{
    String request = "            {\n" +
            "            \t\"ranked_matrix\": [{\n" +
            "            \t\t\t\"problemName\": \"problemName1\",\n" +
            "            \t\t\t\"result\": [{\n" +
            "            \t\t\t\t\t\"algorithmName\": \"algorithmName1\",\n" +
            "            \t\t\t\t\t\"rank\": 1.3\n" +
            "            \t\t\t\t},\n" +
            "            \t\t\t\t{\n" +
            "            \t\t\t\t\t\"algorithmName\": \"algorithmName2\",\n" +
            "            \t\t\t\t\t\"rank\": 1.5\n" +
            "            \t\t\t\t},\n" +
            "            \t\t\t\t{\n" +
            "            \t\t\t\t\t\"algorithmName\": \"algorithmName3\",\n" +
            "            \t\t\t\t\t\"rank\": 1\n" +
            "            \t\t\t\t}\n" +
            "            \t\t\t]\n" +
            "            \t\t},\n" +
            "            \t\t{\n" +
            "            \t\t\t\"problemName\": \"problemName2\",\n" +
            "            \t\t\t\"result\": [{\n" +
            "            \t\t\t\t\t\"algorithmName\": \"algorithmName1\",\n" +
            "            \t\t\t\t\t\"rank\": 0.9\n" +
            "            \t\t\t\t},\n" +
            "            \t\t\t\t{\n" +
            "            \t\t\t\t\t\"algorithmName\": \"algorithmName2\",\n" +
            "            \t\t\t\t\t\"rank\": 2.0\n" +
            "            \t\t\t\t},\n" +
            "            \t\t\t\t{\n" +
            "            \t\t\t\t\t\"algorithmName\": \"algorithmName3\",\n" +
            "            \t\t\t\t\t\"rank\": 1.2\n" +
            "            \t\t\t\t}\n" +
            "            \t\t\t]\n" +
            "            \t\t}\n" +
            "            \t],\n" +
            "            \t\"number_algorithms\": 3,\n" +
            "            \t\"parametric_tests\": 0,\n" +
            "            \t\"method\": {\n" +
            "            \t\t\"name\": \"methodName\",\n" +
            "            \t\t\"alpha\": 0.05\n" +
            "            \t}\n" +
            "            }";

    @Test
    void calculatePValue()
    {
        Response data = Parsing.getResponse(this.request);
        double p = new DSC2Algorithm().calculatePValue(data);
        assertEquals(p, 3);
    }

}