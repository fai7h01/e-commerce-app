package company;

import company.balance.Balance;
import company.balance.CustomerBalance;
import company.balance.GiftCardBalance;
import company.category.Category;
import company.category.Electronic;
import company.category.Furniture;
import company.category.SkinCare;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import static company.StaticConstants.*;
public class DataGenerator {

    public static void createCustomer(){

        Address address1Customer1 = new Address("7925","Jones Branch Dr","Suite 3300","22102","VA");
        Address address2Customer1 = new Address("825","GeorgeTown Pky","Suite 5355","22036","VA");
        Address address1Customer2 = new Address("5924","Lee Hwy","House","22044","VA");

        List<Address> customer1AddressList = new ArrayList<>();
        customer1AddressList.add(address1Customer1);
        customer1AddressList.add(address2Customer1);

        Customer customer1 = new Customer(UUID.randomUUID(),"luka","luka@gmail.com",customer1AddressList);
        Customer customer2 = new Customer(UUID.randomUUID(),"mike","mike@gmail.com");

        CUSTOMER_LIST.add(customer1);
        CUSTOMER_LIST.add(customer2);

    }

    public static void createCategory(){

        Category category1 = new Electronic(UUID.randomUUID(), "Electronic");
        Category category2 = new Furniture(UUID.randomUUID(), "Furniture");
        Category category3 = new SkinCare(UUID.randomUUID(), "SkinCare");

        CATEGORY_LIST.add(category1);
        CATEGORY_LIST.add(category2);
        CATEGORY_LIST.add(category3);

    }

    public static void createProduct(){

        Product product1 = new Product(UUID.randomUUID(),"PS5",400d,5, 5, CATEGORY_LIST.get(0).getId());
        Product product2 = new Product(UUID.randomUUID(), "Chair", 25.5, 15, 15, CATEGORY_LIST.get(1).getId());
        Product product3 = new Product(UUID.randomUUID(), "Day Cream", 14.5, 50, 50, CATEGORY_LIST.get(2).getId());
        Product product4 = new Product(UUID.randomUUID(),"Meat",12.5,40,40,UUID.randomUUID());

        PRODUCT_LIST.add(product1);
        PRODUCT_LIST.add(product2);
        PRODUCT_LIST.add(product3);
        PRODUCT_LIST.add(product4);

    }

    public static void createBalance(){

        Balance customerBalance = new CustomerBalance(CUSTOMER_LIST.get(0).getId(), 500d);
        Balance giftCardBalance = new GiftCardBalance(CUSTOMER_LIST.get(1).getId(), 350d);

        CUSTOMER_BALANCE_LIST.add(customerBalance);
        GIFT_CARD_BALANCE_LIST.add(giftCardBalance);

    }


}
