package com.fruitshop.domain.fruit;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class FruitRepositoryTest {

    @Autowired
    FruitRepository fruitRepository;

    @AfterEach
    void tearDown() {
        fruitRepository.deleteAll();
    }

    @DisplayName("상품을 추가한다")
    @Test
    void save() {
        // given
        String name = "사과";
        long price = 5000;

        fruitRepository.save(Fruit.builder()
                .name(name)
                .price(price)
                .build());

        // when
        List<Fruit> fruitList = fruitRepository.findAll();

        // then
        Fruit fruit = fruitList.get(0);

        assertThat(fruit.getName()).isEqualTo(name);
        assertThat(fruit.getPrice()).isEqualTo(price);
        assertThat(fruit.isSold()).isFalse();
    }
}
