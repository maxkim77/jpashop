package jpabook.jpashop.controller;


import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberForm{
    /*이클래스는 회원가입을 위한 폼데이터를 처리하는 DTO 폼입니다.
    * 화면계층과 서비스계층 사이의 명확한 경계를 만듭니다.
    * 즉 화면에서 받은 데이터는 DTO를 통해 서비스 계층으로전달기 때문에,
    * 두 계층이 서로 의존성을 갖지않게됩니다.
    * */
    @NotEmpty(message = "회원이름은 필수입니다.")
    private String name;
    private String city;
    private String street;
    private String zipcode;
}
