package com.fruitshop.domain.fruit;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Fruit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "fruit_id")
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private LocalDate warehousingDate;

    @Column(nullable = false)
    private long price;

    @Column(nullable = false)
    private boolean isSold;

    @Builder
    public Fruit(String name, long price) {
        this.name = name;
        this.warehousingDate = LocalDate.now();
        this.price = price;
        this.isSold = false; // 기본값 설정
    }

    // 상품이 판매되었다 //
    public void changeSoldStatus() {
        this.isSold = true;
    }
}
