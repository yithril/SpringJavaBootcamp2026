package org.example;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        //Read from the file!
        try{
            FileReader fileReader = new FileReader("src/main/resources/yeats-poem.txt");
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            String input;

            while((input = bufferedReader.readLine()) != null){
                System.out.println(input);
            }

            bufferedReader.close();
        }
        catch(IOException ex){
            System.out.println("There was a problem with the file.");
        }
    }
}