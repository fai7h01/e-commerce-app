package company;

import java.util.List;
import java.util.UUID;

public class Customer {

    private UUID id;
    private String username;
    private String email;
    private List<Address> addressList;

    public Customer(UUID id, String userName, String email) {
        this.id = id;
        this.username = userName;
        this.email = email;
    }

    public Customer(UUID id, String userName, String email, List<Address> addressList) {
        this.id = id;
        this.username = userName;
        this.email = email;
        this.addressList = addressList;
    }

    public UUID getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public List<Address> getAddressList() {
        return addressList;
    }
}
