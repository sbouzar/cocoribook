
package models.elements;

public class DeliveryAddress {
    private int id;
    private String lastName;
    private String firstName;
    private String addressLine1;
    private String addressLine2;
    private String zipcode;
    private String city;
    private String digicode;
    private String phone;
    private int active;

    public DeliveryAddress() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getActive() {
        return active;
    }

    public void setActive(int active) {
        this.active = active;
    }

    
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getAddressLine1() {
        return addressLine1;
    }

    public void setAddressLine1(String addressLine1) {
        this.addressLine1 = addressLine1;
    }

    public String getAddressLine2() {
        return addressLine2;
    }

    public void setAddressLine2(String addressLine2) {
        this.addressLine2 = addressLine2;
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDigicode() {
        return digicode;
    }

    public void setDigicode(String digicode) {
        this.digicode = digicode;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public String toString() {
        return "{" 
                + "  \"lastName\": " + lastName + ","
                + "  \"firstName\": " + firstName + ","
                + "  \"addressLine1\": " + addressLine1 + ","
                + "  \"addressLine2\": " + addressLine2 + ","
                + "  \"zipcode\": " + zipcode + ","
                + "  \"city\": " + city + ","
                + "  \"digicode\": " + digicode + ","
                + "  \"phone\": " + phone + 
                "}";
    }

    
    
    
    
}
