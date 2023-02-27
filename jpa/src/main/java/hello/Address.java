package hello;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.util.Objects;

@Embeddable //값타입 명시
public class Address {

    private String city;
    private String street;
    
    @Column(name = "zip_code")  //칼럼명 같이 옵션값 사용가능
    private String zipCode;

    public Address() {
    }

    public Address(String city, String street, String zipCode) {
        this.city = city;
        this.street = street;
        this.zipCode = zipCode;
    }

    public String getCity() {
        return city;
    }

    public String getStreet() {
        return street;
    }

    public String getZipCode() {
        return zipCode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Address address = (Address) o;
        return Objects.equals(city, address.city)
                && Objects.equals(street, address.street)
                && Objects.equals(zipCode, address.zipCode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(city, street, zipCode);
    }

    //    public void setCity(String city) {
//        this.city = city;
//    }

//    public void setStreet(String street) {
//        this.street = street;
//    }

//    public void setZipCode(String zipCode) {
//        this.zipCode = zipCode;
//    }
}
