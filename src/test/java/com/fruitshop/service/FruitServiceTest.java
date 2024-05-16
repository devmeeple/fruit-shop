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
import static org.assertj.core.api.Assertions.assertThatThrownBy;

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

    @DisplayName("상품의 판매상태를 수정한다")
    @Test
    void findById() {
        // given
        String name = "사과";
        long price = 3000;

        Fruit savedFruit = fruitRepository.save(Fruit.builder()
                .name(name)
                .price(price)
                .build());

        Long updateId = savedFruit.getId();

        // when
        fruitService.findById(updateId);

        // then
        List<Fruit> all = fruitRepository.findAll();
        Fruit fruit = all.get(0);
        assertThat(fruit.isSold()).isTrue();
    }

    @DisplayName("존재하지 않는 상품을 조회하면 에러가 발생한다")
    @Test
    void findByIdException() {
        // given
        String name = "사과";
        long price = 3000;

        fruitRepository.save(Fruit.builder()
                .name(name)
                .price(price)
                .build());

        Long badId = -1L;

        // when  + then
        assertThatThrownBy(() -> fruitService.findById(badId))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("과일이름을 기준으로 상품개수를 조회한다")
    @Test
    void countByName() {
        // given
        String name = "사과";
        long price = 5000;

        fruitRepository.save(Fruit.builder()
                .name(name)
                .price(price)
                .build());

        // when
        Long count = fruitService.countByName(name);

        // then
        assertThat(count).isEqualTo(1);
    }
}
