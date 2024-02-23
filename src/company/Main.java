package company;

import company.balance.Balance;
import company.balance.CustomerBalance;
import company.balance.GiftCardBalance;
import company.category.Category;
import company.discount.Discount;
import company.order.Order;
import company.order.OrderService;
import company.order.OrderServiceImpl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.UUID;

import static company.DataGenerator.*;
import static company.StaticConstants.*;

public class Main {
    public static void main(String[] args) {

        createCustomer();
        createCategory();
        createProduct();
        createDiscount();
        createBalance();


        // Asking user to choose customer
        Scanner scanner = new Scanner(System.in);

        System.out.println("Select Customer");
        for (int i = 0; i < CUSTOMER_LIST.size(); i++) {
            System.out.println("Type: " + i + " for customer: " + CUSTOMER_LIST.get(i).getUsername());
        }

        Customer customer = CUSTOMER_LIST.get(scanner.nextInt());

        Cart cart = new Cart(customer);

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
                    for(Discount discount : DISCOUNT_LIST){
                        System.out.println("Discount Name: " + discount.getName() + "discount threshold amount: " + discount.getThresholdAmount());
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
                    System.out.println("Type 1 for customer balance: " + customerBalance.getBalance());
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
                    Map<Product, Integer> productMap = new HashMap<>();
                    cart.setProductMap(productMap);
                    while(true){
                        System.out.println("Which product you want to add to your cart. For exit product selection type: exit");
                        for (Product product : PRODUCT_LIST) {
                            try {
                                System.out.println("Product id: " + product.getId() + " price:" + product.getUnitPrice()+
                                                   "product category: " + product.getCategoryName() +
                                                    " stock: " + product.getStock() + " product delivery due: " + product.getDeliveryDueDate());
                            } catch (Exception e) {
                                System.out.println(e.getMessage());
                            }
                        }
                        String productId = scanner.next();

                        try {
                            Product product = findProductById(productId);
                            if (!putItemToCartIfStockAvailable(cart, product)){
                                System.out.println("Stock is insufficient, Please try again!");
                                continue;
                            }
                        } catch (Exception e) {
                            System.out.println("Product does not exists. Please try again.");
                            continue;
                        }

                        System.out.println("Do you want to add more product? Type Y for adding more, N for exit");
                        String decision = scanner.next();
                        if (!decision.equals("Y")){
                            break;
                        }
                    }
                    System.out.println("seems there are discount options. Do you want to see and apply to your cart if it is applicable. For no discount type no");
                    for (Discount discount : DISCOUNT_LIST) {
                        System.out.println("discount id: " + discount.getId() + " discount name: " + discount.getName());
                    }
                    String discountId = scanner.next();
                    if (!discountId.equals("no")){
                        try {
                            Discount discount = findDiscountById(discountId);
                            if (discount.decideDiscountIsApplicableToCart(cart)){
                                cart.setDiscountId(discount.getId());
                            }
                        } catch (Exception e) {
                            System.out.println(e.getMessage());
                        }
                    }

                    OrderService orderService = new OrderServiceImpl();
                    String result = orderService.placeOrder(cart);
                    if (result.equals("Order has been placed successfully")){
                        System.out.println("Order is successful");
                        cart.setProductMap(new HashMap<>());
                        cart.setDiscountId(null);
                    }else{
                        System.out.println(result);
                    }
                    break;
                case 6: // see cart
                    System.out.println("Your cart");
                    if (!cart.getProductMap().keySet().isEmpty()){
                        for (Product product : cart.getProductMap().keySet()) {
                            System.out.println("Product name: " + product.getName() + " count: " + cart.getProductMap().get(product));
                        }
                    }else{
                        System.out.println("Your cart is empty.");
                    }
                    break;
                case 7: // see order details
                    printOrdersByCustomerId(customer.getId());
                    break;
                case 8: // see your addresses
                    printAddressByCustomerId(customer);
                    break;
                case 9: // close app
                    System.exit(1);
                    break;
            }

        }



    }

    private static void printAddressByCustomerId(Customer customer){
        for (Address address : customer.getAddressList()) {
            System.out.println(" Street Name: " + address.getStreetName() +
                    " Street Number: " + address.getStreetNumber() + "ZipCode:  "
                    + address.getZipcode() + " State: " + address.getState());
        }
    }

    private static void printOrdersByCustomerId(UUID customerId){
        for (Order order : ORDER_LIST) {
            if (order.getCustomerId().toString().equals(customerId.toString())){
                System.out.println("Order status: "  + order.getOrderStatus() + " order amount: " + order.getPaidAmount() + " order date: " + order.getOrderDate());
            }
        }
    }

    public static Discount findDiscountById(String discountId) throws Exception {
        for (Discount discount : DISCOUNT_LIST) {
            if (discountId.equals(discount.getId().toString())){
                return discount;
            }
        }
        throw new Exception("Discount not found");
    }

    private static boolean putItemToCartIfStockAvailable(Cart cart, Product product){
        System.out.println("Please provide product count:");
        Scanner scanner = new Scanner(System.in);
        int count = scanner.nextInt();

        Integer cartCount = cart.getProductMap().get(product);

        if (cartCount != null && product.getRemainingStock() > cartCount + count){
            cart.getProductMap().put(product, cartCount + count);
            return true;
        }else if (product.getRemainingStock() >= count){
            cart.getProductMap().put(product, count);
            return true;
        }
        return false;

    }

    private static Product findProductById(String productId) throws Exception {
        for (Product product : PRODUCT_LIST) {
            if (productId.equals(product.getId().toString())){
                return product;
            }
        }
        throw new Exception("Product could not found");
    }

    public static CustomerBalance findCustomerBalance(UUID customerId){
        for (Balance balance : CUSTOMER_BALANCE_LIST) {
            if (balance.getCustomerId().toString().equals(customerId.toString())){
                return (CustomerBalance) balance;
            }
        }
        CustomerBalance customerBalance = new CustomerBalance(customerId, 0d);
        CUSTOMER_BALANCE_LIST.add(customerBalance);

        return customerBalance;
    }

    public static GiftCardBalance findGiftCardBalance(UUID customerId){
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
