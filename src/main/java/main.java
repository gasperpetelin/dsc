import ResponseHandler.ResponseHandler;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

public class main
{
    public static String readFile(int fileNumber) throws IOException
    {
        return new String(Files.readAllBytes(Paths.get("C:\\Users\\Gasper\\Desktop\\jsonfiles\\" + fileNumber + ".json")), StandardCharsets.UTF_8);
    }

    public static void main(String []args)
    {
        //int[] arr = {1, 2, 3, 16, 18, 23, 24};
        //int[] arr = {55, 56, 57};
        int[] arr = {60};
        for(Integer i:arr)
        {
            System.out.println(i);
            String s = null;
            try {
                s = ResponseHandler.calculateRank(readFile(i));
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println(s);
            String s1 = ResponseHandler.calculatePValue(s);
            System.out.println(s1);
            String s2 = ResponseHandler.postHoc("{\n" +
                    "\t\"algorithm_means\": [{\n" +
                    "\t\t\"name\": \"BSif\",\n" +
                    "\t\t\"mean\": 6.295454545454546\n" +
                    "\t}, {\n" +
                    "\t\t\"name\": \"BSqi\",\n" +
                    "\t\t\"mean\": 5.318181818181818\n" +
                    "\t}, {\n" +
                    "\t\t\"name\": \"BSrr\",\n" +
                    "\t\t\"mean\": 5.818181818181818\n" +
                    "\t}, {\n" +
                    "\t\t\"name\": \"CMA.CSA\",\n" +
                    "\t\t\"mean\": 2.522727272727273\n" +
                    "\t}, {\n" +
                    "\t\t\"name\": \"CMA.TPA\",\n" +
                    "\t\t\"mean\": 2.9545454545454546\n" +
                    "\t}, {\n" +
                    "\t\t\"name\": \"GP1.CMAES\",\n" +
                    "\t\t\"mean\": 5.2727272727272725\n" +
                    "\t}, {\n" +
                    "\t\t\"name\": \"RAND.2xDefault\",\n" +
                    "\t\t\"mean\": 8.022727272727273\n" +
                    "\t}, {\n" +
                    "\t\t\"name\": \"RF5.CMAES\",\n" +
                    "\t\t\"mean\": 8.659090909090908\n" +
                    "\t}, {\n" +
                    "\t\t\"name\": \"Sifeg\",\n" +
                    "\t\t\"mean\": 5.045454545454546\n" +
                    "\t}, {\n" +
                    "\t\t\"name\": \"Srr\",\n" +
                    "\t\t\"mean\": 5.090909090909091\n" +
                    "\t}],\n" +
                    "\t\"k\": 10,\n" +
                    "\t\"n\": 22,\n" +
                    "\t\"base_algorithm\": \"CMA.CSA\",\n" +
                    "\t\"method\": \"F\"\n" +
                    "}");
            System.out.println(s2);
        }


    }
}