package com.fruitshop.domain.fruit;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FruitRepository extends JpaRepository<Fruit, Long> {

    /**
     * 과일이름을 기준으로 상품개수를 조회한다
     */
    Long countByName(String name);

    /**
     * 판매되지 않은 특정 금액이상의 상품을 조회한다
     */
    List<Fruit> findAllByPriceGreaterThanEqual(long price);

    /**
     * 판매되지 않은 특정 금액이하의 상품을 조회한다
     */
    List<Fruit> findAllByPriceLessThanEqual(long price);
}
