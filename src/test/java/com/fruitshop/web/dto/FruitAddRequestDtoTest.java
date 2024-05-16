package com.fruitshop.web.dto;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class FruitAddRequestDtoTest {

    @DisplayName("롬복 기능 테스트")
    @Test
    void checkLombok() {
        // given
        String name = "사과";
        long price = 5000;

        // when
        FruitAddRequestDto requestDto = FruitAddRequestDto.builder()
                .name(name)
                .price(price)
                .build();

        // then
        assertThat(requestDto.getName()).isEqualTo("사과");
        assertThat(requestDto.getPrice()).isEqualTo(5000);
    }

}
