package company.balance;

import java.util.UUID;

public class CustomerBalance extends Balance{

    public CustomerBalance(UUID customerId, Double balance) {
        super(customerId, balance);
    }

    @Override
    public Double addBalance(Double amount) {
        setBalance(getBalance() + amount);
        return getBalance();
    }
}
