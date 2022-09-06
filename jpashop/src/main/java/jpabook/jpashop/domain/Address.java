package jpabook.jpashop.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Embeddable;

@Embeddable //내장형으로 사용하겠다.
@Getter
public class Address {
    private String city;
    private String street;
    private String zipcode;

    //값 타입은 변경 불가능한 클래스로 만듬
    //임베디드 타입의 기본생성자는 public 또는 protected를 사용
    protected Address(){
    }
    public Address(String city, String street, String zipcode) {
        this.city = city;
        this.street = street;
        this.zipcode = zipcode;
    }
}
