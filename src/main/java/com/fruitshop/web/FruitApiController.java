package com.fruitshop.web;

import com.fruitshop.domain.fruit.PriceComparison;
import com.fruitshop.service.FruitService;
import com.fruitshop.web.dto.FruitAddRequestDto;
import com.fruitshop.web.dto.FruitCountResponseDto;
import com.fruitshop.web.dto.FruitListResponseDto;
import com.fruitshop.web.dto.FruitSalesAmountResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class FruitApiController {

    private final FruitService fruitService;

    /**
     * 상품(과일) 추가
     * 요청: { name: String, warehousingDate: LocalDate, price: long }
     * 응답: X
     */

    @PostMapping("/api/v1/fruit")
    public void add(@RequestBody FruitAddRequestDto request) {
        fruitService.save(request);
    }

    /**
     * 상품(과일) 판매상태를 수정한다
     * 요청: { id: Long }
     * 응답: X
     */
    @PutMapping("/api/v1/fruit")
    public void changeSoldStatus(@RequestBody Long id) {
        fruitService.findById(id);
    }

    /**
     * 과일이름을 기준으로 판매금액, 미판매금액을 조회한다
     * 요청: { name: String }
     * 응답: { salesAmount: long, notSalesAmount: long }
     */
    @GetMapping("/api/v1/fruit/stat")
    public FruitSalesAmountResponseDto findSalesAmountByName(@RequestParam String name) {
        return fruitService.findSalesAmountByName(name);
    }

    /**
     * 과일이름을 기준으로 상품개수를 조회한다
     * 요청: { name: String }
     * 응답: { count: long }
     */
    @GetMapping("/api/v1/fruit/count")
    public FruitCountResponseDto countByName(@RequestParam String name) {
        long count = fruitService.countByName(name);
        return new FruitCountResponseDto(count);
    }

    /**
     * 판매되지 않은 특정 금액이상 또는 특정금액 이하의 상품목록을 조회한다
     * HTTP Method: GET,
     * Path: /api/v1/fruit/list
     * 요청: option: LTE | GTE, price: long
     * 응답: [ { name: String, price: long, warehousingDate: LocalDate } ... ]
     */
    @GetMapping("/api/v1/fruit/list")
    public List<FruitListResponseDto> listByPrice(@RequestParam PriceComparison option, @RequestParam long price) {
        return fruitService.findAllByComparisonAndPrice(option, price);
    }

}
