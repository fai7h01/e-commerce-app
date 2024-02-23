package company.checkout;

import company.Customer;
import company.balance.CustomerBalance;

import static company.Main.findCustomerBalance;

public class CustomerBalanceCheckoutServiceImpl implements CheckoutService {

    @Override
    public boolean checkout(Customer customer, Double totalAmount) {
        CustomerBalance customerBalance = findCustomerBalance(customer.getId());
        double finalBalance = customerBalance.getBalance() - totalAmount;
        if (finalBalance > 0){
            customerBalance.setBalance(finalBalance);
            return true;
        }
        return false;
    }
}
