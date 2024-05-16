package com.fruitshop.web.dto;

import com.fruitshop.domain.fruit.Fruit;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
public class FruitAddRequestDto {
    private String name;
    private LocalDate warehousingDate;
    private long price;

    @Builder
    public FruitAddRequestDto(String name, long price) {
        this.name = name;
        this.warehousingDate = LocalDate.now(); // 기본값: 현재날짜
        this.price = price;
    }

    public Fruit toEntity() {
        return Fruit.builder()
                .name(name)
                .price(price)
                .build();
    }
}

