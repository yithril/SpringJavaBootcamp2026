package org.example;

import java.util.List;

public class EmployeeManager {
    public static void main(String[] args) {
        List<Employee> companyList = FileManager.getEmployees();

        //"%.2f%n"
        for(Employee e : companyList){
            System.out.printf("Id: %d Name: %s Hours Worked: %.2f Pay Rate: %f %n",
                    e.getId(), e.getName(), e.getHoursWorked(), e.getPayRate());
        }
    }
}
