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
        int[] arr = {1, 2, 3, 16, 18, 23, 24};

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
        }


    }
}