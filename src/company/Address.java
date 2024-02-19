package company;

public class Address {

    private String streetNumber;
    private String streetName;
    private String additionalAddressLine;
    private String zipcode;
    private String state;

    public Address(String streetNumber, String streetName, String additionalAddressLine, String zipcode, String state) {
        this.streetNumber = streetNumber;
        this.streetName = streetName;
        this.additionalAddressLine = additionalAddressLine;
        this.zipcode = zipcode;
        this.state = state;
    }

    public String getStreetNumber() {
        return streetNumber;
    }

    public String getStreetName() {
        return streetName;
    }

    public String getAdditionalAddressLine() {
        return additionalAddressLine;
    }

    public String getZipcode() {
        return zipcode;
    }

    public String getState() {
        return state;
    }
}
