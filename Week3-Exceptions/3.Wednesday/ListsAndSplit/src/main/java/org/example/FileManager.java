package org.example;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FileManager {
    public static List<Employee> getEmployees(){
        try{
            FileReader fileReader = new FileReader("src/main/resources/employees.csv");
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            String input;

            List<Employee> employeeList = new ArrayList<>();

            while((input = bufferedReader.readLine()) != null){
                String[] csvRow = input.split("\\|");
                int employeeId = Integer.parseInt(csvRow[0]);
                String name = csvRow[1];
                double hoursWorked = Double.parseDouble(csvRow[2]);
                double payRate = Double.parseDouble(csvRow[3]);

                Employee newEmployee = new Employee(employeeId, name, hoursWorked, payRate);

                employeeList.add(newEmployee);
            }

            bufferedReader.close();
            return employeeList;
        }
        catch(IOException ex){
            System.out.println("There was a problem with the file");
        }

        return new ArrayList<>();
    }
}
