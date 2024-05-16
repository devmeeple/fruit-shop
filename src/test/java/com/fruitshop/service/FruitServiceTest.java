package com.fruitshop.service;

import com.fruitshop.domain.fruit.Fruit;
import com.fruitshop.domain.fruit.FruitRepository;
import com.fruitshop.web.dto.FruitAddRequestDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Transactional
@SpringBootTest
class FruitServiceTest {

    @Autowired
    FruitService fruitService;

    @Autowired
    FruitRepository fruitRepository;

    @DisplayName("상품을 추가한다")
    @Test
    void save() {
        // given
        FruitAddRequestDto request = FruitAddRequestDto.builder()
                .name("사과")
                .price(2000)
                .build();

        // when
        fruitService.save(request);

        // then
        List<Fruit> all = fruitRepository.findAll();
        Fruit fruit = all.get(0);

        assertThat(fruit.getName()).isEqualTo("사과");
        assertThat(fruit.getPrice()).isEqualTo(2000);
    }
}
