package company.balance;

import java.util.UUID;

public class GiftCardBalance extends Balance{

    public GiftCardBalance(UUID customerId, Double balance) {
        super(customerId, balance);
    }

    @Override
    public Double addBalance(Double amount) {
        double promotionAmount = amount * 0.1;
        setBalance(getBalance() + amount + promotionAmount);
        return getBalance();
    }
}
