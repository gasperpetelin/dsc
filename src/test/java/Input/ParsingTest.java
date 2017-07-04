package Input;

import JsonParsing.Parsing;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;


class ParsingTest
{
    @Test
    void problemCreationFromJson()
    {
        String prob = "{\"name\":\"problemName2\",\"data\":[1.3,3.6,9.0]}";
        Problem p = Parsing.getProblem(prob);


        double[] ls = new double[] {1.3, 3.6, 9.0};

        assertEquals("problemName2", p.getName());
        assertArrayEquals(ls, p.getData());
    }

    @Test
    void algorithmCreationFromJson()
    {
        String algo = "{\n" +
                "\t\"name\": \"algorithmName1\",\n" +
                "\t\"problems\": [{\n" +
                "\t\t\t\"name\": \"problemName1\",\n" +
                "\t\t\t\"data\": [1.3, 3.6, 9.0]\n" +
                "\t\t},\n" +
                "\t\t{\n" +
                "\t\t\t\"name\": \"problemName2\",\n" +
                "\t\t\t\"data\": [1.3, 3.6, 8.0]\n" +
                "\t\t}\n" +
                "\t]\n" +
                "}";

        Algorithm a = Parsing.getAlgorithm(algo);

        assertEquals(a.getName(), "algorithmName1");
        assertEquals(a.getProblems().size(), 2);
    }

    @Test
    void inputCreationFromJson()
    {
        String input = "            {\n" +
                "            \t\"alpha\": 0.5,\n" +
                "            \t\"data\": [{\n" +
                "            \t\t\t\"name\": \"algorithmName1\",\n" +
                "            \t\t\t\"problems\": [{\n" +
                "            \t\t\t\t\t\"name\": \"problemName1\",\n" +
                "            \t\t\t\t\t\"data\": [11.0, 2.0, 13.0, 4.0, 15.0]\n" +
                "            \t\t\t\t},\n" +
                "            \t\t\t\t{\n" +
                "            \t\t\t\t\t\"name\": \"problemName2\",\n" +
                "            \t\t\t\t\t\"data\": [1.0, 7.0, 8.0, 9.0, 4.0]\n" +
                "            \t\t\t\t}\n" +
                "            \t\t\t]\n" +
                "            \t\t},\n" +
                "            \t\t{\n" +
                "            \t\t\t\"name\": \"algorithmName2\",\n" +
                "            \t\t\t\"problems\": [{\n" +
                "            \t\t\t\t\t\"name\": \"problemName1\",\n" +
                "            \t\t\t\t\t\"data\": [1.0, 2.0, 3.0, 4.0, 5.0]\n" +
                "            \t\t\t\t},\n" +
                "            \t\t\t\t{\n" +
                "            \t\t\t\t\t\"name\": \"problemName2\",\n" +
                "            \t\t\t\t\t\"data\": [6.0, 7.0, 7.0, 9.0, 11.0]\n" +
                "            \t\t\t\t}\n" +
                "            \t\t\t]\n" +
                "            \t\t}\n" +
                "            \t]\n" +
                "            }";

        Request r = Parsing.getRequest(input);

        assertEquals(0.5, r.getAlpha());
        assertEquals(2, r.getAlgorithms().size());
    }



}