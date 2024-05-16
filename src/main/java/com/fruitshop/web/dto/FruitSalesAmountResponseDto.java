package com.fruitshop.web.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class FruitSalesAmountResponseDto {

    private long salesAmount;
    private long notSalesAmount;

    @Builder
    public FruitSalesAmountResponseDto(long salesAmount, long notSalesAmount) {
        this.salesAmount = salesAmount;
        this.notSalesAmount = notSalesAmount;
    }
}
