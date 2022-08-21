package jpabook.jpashop.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Embeddable;

@Embeddable //내장형으로 사용하겠다.
@Getter
@Setter
public class Address {
    private String city;
    private String street;
    private String zipcode;
}
