package com.fruitshop.domain.fruit;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class FruitTest {

    @DisplayName("제품이 판매되면 상태를 변경한다")
    @Test
    void changeSoldStatus() {
        // given
        Fruit fruit = Fruit.builder()
                .name("사과")
                .price(1000)
                .build();

        // when
        fruit.changeSoldStatus();

        // then
        assertThat(fruit.isSold()).isTrue();

    }
}
