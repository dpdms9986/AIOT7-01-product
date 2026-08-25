package DTO;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;

// 상품 정보 객체 정의하기
// 상품번호, 상품명, 유통기한, 등록날짜, 수정일

@Data
@AllArgsConstructor

public class Product {

    // 상품의 번호(몇번째로 들어왔는지 혹은 상품코드)
    private int no;
    // 상품명
    private String name;
    // 유통기한
    private String exdate;
    // 등록날짜
    private Date createdAt;
    // 수정일
    private Date updatedAt;
    
    
    // 기본생성자
    public Product() {
        this(" [이름없음] ", " 0000/00/00 " );

    }

    public Product(String name, String exdate){
        this.name = name;
        this.exdate = exdate;
    }
}