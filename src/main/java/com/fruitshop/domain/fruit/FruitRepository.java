package com.fruitshop.domain.fruit;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface FruitRepository extends JpaRepository<Fruit, Long> {

    /**
     * 4.3
     * 과일이름을 기준으로 판매금액을 조회한다
     * 과일이름을 기준으로 미판매금액을 조회한다
     */
    @Query("SELECT SUM(f.price) FROM Fruit f WHERE f.name = :name AND f.isSold = true")
    Optional<Long> salesAmountByName(String name);

    @Query("SELECT SUM(f.price) FROM Fruit f WHERE f.name = :name AND f.isSold = false")
    Optional<Long> notSalesAmountByName(String name);

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
