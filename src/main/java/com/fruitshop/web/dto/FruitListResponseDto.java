package com.fruitshop.web.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class FruitListResponseDto {

    private String name;
    private LocalDate warehousingDate;
    private long price;

    @Builder
    public FruitListResponseDto(String name, LocalDate warehousingDate, long price) {
        this.name = name;
        this.warehousingDate = warehousingDate;
        this.price = price;
    }
}
