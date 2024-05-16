package com.fruitshop.domain.fruit;

import org.springframework.data.jpa.repository.JpaRepository;

public interface FruitRepository extends JpaRepository<Fruit, Long> {

    /**
     * 과일이름을 기준으로 상품개수를 조회한다
     */
    Long countByName(String name);
}
