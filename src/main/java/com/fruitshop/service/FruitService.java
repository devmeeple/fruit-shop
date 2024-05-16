package com.fruitshop.service;

import com.fruitshop.domain.fruit.Fruit;
import com.fruitshop.domain.fruit.FruitRepository;
import com.fruitshop.domain.fruit.PriceComparison;
import com.fruitshop.web.dto.FruitAddRequestDto;
import com.fruitshop.web.dto.FruitListResponseDto;
import com.fruitshop.web.dto.FruitSalesAmountResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

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
     * 과일이름을 기준으로 판매금액, 미판매금액을 조회한다
     */
    public FruitSalesAmountResponseDto findSalesAmountByName(String name) {
        Long salesAmount = fruitRepository.salesAmountByName(name).orElse(0L);
        Long notSalesAmount = fruitRepository.notSalesAmountByName(name).orElse(0L);

        return FruitSalesAmountResponseDto.builder()
                .salesAmount(salesAmount)
                .notSalesAmount(notSalesAmount)
                .build();
    }

    /**
     * 과일이름을 기준으로 상품개수를 조회한다
     */
    public Long countByName(String name) {
        return fruitRepository.countByName(name);
    }

    /**
     * 판매되지 않은 특정 금액이상 또는 특정금액 이하의 상품목록을 조회한다
     * 리팩터링이 필요해보임 만족스럽지 않음
     */
    public List<FruitListResponseDto> findAllByComparisonAndPrice(PriceComparison comparison, long price) {
        List<Fruit> fruits;

        if (comparison == PriceComparison.GTE) {
            fruits = fruitRepository.findAllByPriceGreaterThanEqual(price);
        } else {
            fruits = fruitRepository.findAllByPriceLessThanEqual(price);
        }

        return fruits.stream()
                .map(Fruit::toDto)
                .collect(Collectors.toList());
    }
}

