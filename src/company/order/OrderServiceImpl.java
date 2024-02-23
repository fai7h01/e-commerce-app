package company.order;

import company.Cart;
import company.checkout.CheckoutService;
import company.checkout.CustomerBalanceCheckoutServiceImpl;
import company.checkout.MixPaymentCheckoutServiceImpl;
import company.discount.Discount;

import java.time.LocalDateTime;
import java.util.Scanner;
import java.util.UUID;

import static company.Main.findDiscountById;
import static company.StaticConstants.ORDER_LIST;

public class OrderServiceImpl implements OrderService {

    @Override
    public String placeOrder(Cart cart) {
        double amountAfterDiscount = cart.calculateTotalAmount();

        if (cart.getDiscountId() != null){
            try {
                Discount discount = findDiscountById(cart.getDiscountId().toString());
                amountAfterDiscount = discount.calculateCartAmountAfterDiscountApplied(amountAfterDiscount);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
        Scanner scanner = new Scanner(System.in);
        System.out.println("which payment option you would like to choose, Type 1 : customer balance, Type 2 : Mix (gift card + customer balance)");
        int paymentType = scanner.nextInt();
        boolean checkoutResult = false;
        switch (paymentType){
            case 1:
                CheckoutService customerBalanceCheckoutService = new CustomerBalanceCheckoutServiceImpl();
                checkoutResult = customerBalanceCheckoutService.checkout(cart.getCustomer(), amountAfterDiscount);
                break;
            case 2:
                CheckoutService mixPaymentCheckoutService = new MixPaymentCheckoutServiceImpl();
                checkoutResult = mixPaymentCheckoutService.checkout(cart.getCustomer(), amountAfterDiscount);
                break;
        }

        if (checkoutResult){
            Order order = new Order(UUID.randomUUID(), LocalDateTime.now(),cart.calculateTotalAmount(),amountAfterDiscount,
                    cart.calculateTotalAmount()-amountAfterDiscount, cart.getCustomer().getId(),"Placed",cart.getProductMap().keySet());
            ORDER_LIST.add(order);
            return "Order has been placed successfully";
        }else{
            return "Balance is insufficient. Please add money to your one of balances and try again";
        }
    }
}
