package com.fruitshop.domain.fruit;

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

    // 각 테스트가 끝나고 롤백을 해준다. AfterEach 저장소. deleteAll 필요 없음.
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

    @DisplayName("판매금액을 조회한다")
    @Test
    void salesAmountByName() {
        // given
        String name = "사과";
        long price = 5000;

        Fruit fruit = fruitRepository.save(Fruit.builder()
                .name(name)
                .price(price)
                .build());

        fruit.changeSoldStatus();

        // when
        Long salesAmount = fruitRepository.salesAmountByName(name).orElse(0L);

        // then
        assertThat(salesAmount).isEqualTo(5000);
    }

    @DisplayName("미판매금액을 조회한다")
    @Test
    void notSalesAmountByName() {
        // given
        String name = "사과";
        long price = 5000;

        fruitRepository.save(Fruit.builder()
                .name(name)
                .price(price)
                .build());

        // when
        Long notSalesAmount = fruitRepository.notSalesAmountByName(name).orElse(0L);

        // then
        assertThat(notSalesAmount).isEqualTo(5000);
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
        Long count = fruitRepository.countByName(name);

        // then
        assertThat(count).isEqualTo(1);
    }

    @DisplayName("판매되지 않은 특정 금액이상(GTE)의 상품을 조회한다")
    @Test
    void findAllByPriceGreaterThanEqual() {
        // given
        String name = "사과";
        long basePrice = 3000;
        long greaterThanEqualPrice = 5000;
        long lessThanEqualPrice = 2000;

        fruitRepository.save(Fruit.builder()
                .name(name)
                .price(greaterThanEqualPrice)
                .build());

        fruitRepository.save(Fruit.builder()
                .name(name)
                .price(lessThanEqualPrice)
                .build());

        // when
        List<Fruit> fruitList = fruitRepository.findAllByPriceGreaterThanEqual(basePrice);

        // then
        assertThat(fruitList.size()).isEqualTo(1);
        assertThat(fruitList.get(0).getName()).isEqualTo("사과");
        assertThat(fruitList.get(0).getPrice()).isEqualTo(5000);
    }

    @DisplayName("판매되지 않은 특정 금액이하(LTE)의 상품을 조회한다")
    @Test
    void findAllByPriceLessThanEqual() {
        // given
        String name = "사과";
        long basePrice = 3000;
        long gtePrice = 5000;
        long ltePrice = 2000;

        fruitRepository.save(Fruit.builder()
                .name(name)
                .price(gtePrice)
                .build());

        fruitRepository.save(Fruit.builder()
                .name(name)
                .price(ltePrice)
                .build());

        // when
        List<Fruit> fruitList = fruitRepository.findAllByPriceLessThanEqual(basePrice);

        // then
        assertThat(fruitList.size()).isEqualTo(1);
    }
}
