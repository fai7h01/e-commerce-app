package company.checkout;

import company.Customer;
import company.balance.CustomerBalance;
import company.balance.GiftCardBalance;

import java.util.UUID;

import static company.Main.findCustomerBalance;
import static company.Main.findGiftCardBalance;

public class MixPaymentCheckoutServiceImpl implements CheckoutService {
    @Override
    public boolean checkout(Customer customer, Double totalAmount) {
        GiftCardBalance giftCardBalance = findGiftCardBalance(customer.getId());
        final double giftBalance = giftCardBalance.getBalance() - totalAmount;
        if (giftBalance > 0){
            giftCardBalance.setBalance(giftBalance);
        }else {
            CustomerBalance customerBalance = findCustomerBalance(customer.getId());
            final double mixBalance = customerBalance.getBalance() + giftCardBalance.getBalance() - totalAmount;
            if (mixBalance > 0){
                giftCardBalance.setBalance(0d);
                customerBalance.setBalance(mixBalance);
                return true;
            }
        }
        return false;
    }

}
