package company;

import company.balance.Balance;
import company.balance.CustomerBalance;
import company.balance.GiftCardBalance;
import company.category.Category;
import company.discount.Discount;

import java.util.Scanner;
import java.util.UUID;

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
                    for (Discount discount : DISCOUNT_LIST) {
                        System.out.println("Discount name: " + discount.getName() + " discount threshold amount: " + discount.getThresholdAmount());
                    }
                    break;
                case 3: // see balance
                    CustomerBalance cBalance = findCustomerBalance(customer.getId());
                    GiftCardBalance gBalance = findGiftCardBalance(customer.getId());
                    double totalBalance = cBalance.getBalance() + gBalance.getBalance();
                    System.out.println("Total Balance: " + totalBalance);
                    System.out.println("Customer balance: " + cBalance.getBalance());
                    System.out.println("Gift card balance: " + gBalance.getBalance());
                    break;
                case 4: // add balance
                    CustomerBalance customerBalance = findCustomerBalance(customer.getId());
                    GiftCardBalance giftCardBalance = findGiftCardBalance(customer.getId());
                    System.out.println("Which account would you like to add?");
                    System.out.println("Type 1 for customer balance: " + customerBalance.getCustomerId());
                    System.out.println("Type 2 for gift card balance: " + giftCardBalance.getBalance());
                    int balanceAccountSelection = scanner.nextInt();
                    System.out.println("How much would you like to add?");
                    double amount = scanner.nextDouble();
                    switch (balanceAccountSelection){
                        case 1:
                            customerBalance.addBalance(amount);
                            System.out.println("New customer balance: " + customerBalance.getBalance());
                            break;
                        case 2:
                            giftCardBalance.addBalance(amount);
                            System.out.println("New gift card balance: " + giftCardBalance.getBalance());
                            break;
                    }

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

    private static CustomerBalance findCustomerBalance(UUID customerId){
        for (Balance balance : CUSTOMER_BALANCE_LIST) {
            if (balance.getCustomerId().toString().equals(customerId.toString())){
                return (CustomerBalance) balance;
            }
        }
        CustomerBalance customerBalance = new CustomerBalance(customerId, 0d);
        CUSTOMER_BALANCE_LIST.add(customerBalance);

        return customerBalance;
    }

    private static GiftCardBalance findGiftCardBalance(UUID customerId){
        for (Balance balance : GIFT_CARD_BALANCE_LIST) {
            if (balance.getCustomerId().toString().equals(customerId.toString())){
                return (GiftCardBalance) balance;
            }
        }

        GiftCardBalance giftCardBalance = new GiftCardBalance(customerId, 0d);
        GIFT_CARD_BALANCE_LIST.add(giftCardBalance);

        return giftCardBalance;
    }

    private static String[] prepareMenuOptions(){
        return new String[]{"List Categories","List Products","List Discount","See Balance","Add Balance",
                "Place an order","See Cart","See order details","See your address","Close App"};
    }

}
