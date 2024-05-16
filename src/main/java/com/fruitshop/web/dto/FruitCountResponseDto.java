package com.fruitshop.web.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class FruitCountResponseDto {
    private long count;

    @Builder
    public FruitCountResponseDto(long count) {
        this.count = count;
    }
}
