package com.fruitshop.service;

import com.fruitshop.domain.fruit.Fruit;
import com.fruitshop.domain.fruit.FruitRepository;
import com.fruitshop.domain.fruit.PriceComparison;
import com.fruitshop.web.dto.FruitAddRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Transactional(readOnly = true) // 조회시 성능이점, 쓰기는 false
@Service
public class FruitService {

    private final FruitRepository fruitRepository;

    /**
     * 상품추가
     */
    @Transactional
    public void save(FruitAddRequestDto request) {
        fruitRepository.save(request.toEntity());
    }

    /**
     * 상품의 판매상태를 수정한다
     * 영속성 컨텍스트에서 관리되는 객체를 수정하면, 트랜잭션이 종료되는 시점에 변경된 내용이 DB에 반영된다.
     */
    @Transactional
    public void findById(Long id) {
        Fruit fruit = fruitRepository.findById(id).orElseThrow(() -> new IllegalArgumentException(id + "에 해당하는 과일을 찾을 수 없습니다"));
        fruit.changeSoldStatus();
    }

    /**
     * 과일이름을 기준으로 판매된 금액, 미판매된 금액을 조회한다
     */

    /**
     * 과일이름을 기준으로 상품개수를 조회한다
     */
    public Long countByName(String name) {
        return fruitRepository.countByName(name);
    }

    /**
     * 판매되지 않은 특정 금액이상 또는 특정금액 이하의 상품목록을 조회한다
     */
    public List<Fruit> findAllByComparisonAndPrice(PriceComparison comparison, long price) {
        if (comparison == PriceComparison.GTE) {
            return fruitRepository.findAllByPriceGreaterThanEqual(price);
        }

        return fruitRepository.findAllByPriceLessThanEqual(price);
    }
}

