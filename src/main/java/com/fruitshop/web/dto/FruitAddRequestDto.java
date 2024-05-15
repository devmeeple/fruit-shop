package com.fruitshop.web.dto;

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
    public FruitAddRequestDto(String name, LocalDate warehousingDate, long price) {
        this.name = name;
        this.warehousingDate = warehousingDate;
        this.price = price;
    }
}
