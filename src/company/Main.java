package company;

import company.category.Category;

import java.util.Scanner;

import static company.DataGenerator.*;
import static company.StaticConstants.*;

public class Main {
    public static void main(String[] args) {

        createCustomer();
        createCategory();
        createProduct();
        //createDiscount();
        //createBalance();


        // Asking user to choose customer
        Scanner scanner = new Scanner(System.in);

        System.out.println("Select Customer");
        for (int i = 0; i < CUSTOMER_LIST.size(); i++) {
            System.out.println("Type: " + i + " for customer: " + CUSTOMER_LIST.get(i).getUsername());
        }

        Customer customer = CUSTOMER_LIST.get(scanner.nextInt());

        while(true){

            System.out.println("What would you like to do? Just type id for selection");

            for (int i = 0; i < prepareMenuOptions().length; i++) {
                System.out.println(i + " - " + prepareMenuOptions()[i]);
            }
            
            int menuSelection = scanner.nextInt();

            switch (menuSelection){
                case 0: // list categories
                    for (Category category : CATEGORY_LIST) {
                        System.out.println("Category code: " + category.generateCategoryCode() + " category name: " + category.getName());
                    }
                    break;
                case 1: // list products // product name product category name
                    for (Product product : PRODUCT_LIST) {
                        try {
                            System.out.println("Product name: " + product.getName() + " Product category name: " + product.getCategoryName());
                        } catch (Exception e) {
                            System.out.println("Product could not be printed because category not found for product name: " + e.getMessage().split(",")[1]);
                        }
                    }
                    break;
                case 2: // list discounts
                    break;
                case 3: // see balance
                    break;
                case 4: // add balance
                    break;
                case 5: // place an order
                    break;
                case 6: // see cart
                    break;
                case 7: // see order details
                    break;
                case 8: // see your addresses
                    break;
                case 9: // close app
                    break;
            }

        }



    }

    private static String[] prepareMenuOptions(){
        return new String[]{"List Categories","List Products","List Discount","See Balance","Add Balance",
                "Place an order","See Cart","See order details","See your address","Close App"};
    }

}
