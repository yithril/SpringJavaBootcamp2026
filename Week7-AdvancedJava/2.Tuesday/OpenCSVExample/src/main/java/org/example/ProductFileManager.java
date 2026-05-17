package org.example;

import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.bean.StatefulBeanToCsvBuilder;

import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;

public class ProductFileManager {
    public static void writeProducts(List<Product> products){

        try(FileWriter writer =
                    new FileWriter("src/main/resources/products.csv")) {

            new StatefulBeanToCsvBuilder<Product>(writer)
                    .withApplyQuotesToAll(false)
                    .build()
                    .write(products);

        }
        catch(Exception ex){
            ex.printStackTrace();
        }
    }

    public static List<Product> getProducts(){
        List<Product> products = new ArrayList<>();
        try{
           products = new CsvToBeanBuilder<Product>(new FileReader("src/main/resources/products.csv"))
                    .withType(Product.class)
                    .build()
                    .parse();
        }
        catch(Exception ex){
            System.out.println("Something messed up.");
        }

        return products;
    }
}
