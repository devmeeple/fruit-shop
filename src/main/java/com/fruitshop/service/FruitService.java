package com.fruitshop.service;

import com.fruitshop.domain.fruit.FruitRepository;
import com.fruitshop.web.dto.FruitAddRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
}
