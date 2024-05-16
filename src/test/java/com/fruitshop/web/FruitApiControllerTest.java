package com.fruitshop.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fruitshop.domain.fruit.Fruit;
import com.fruitshop.domain.fruit.FruitRepository;
import com.fruitshop.domain.fruit.PriceComparison;
import com.fruitshop.web.dto.FruitAddRequestDto;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
class FruitApiControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private FruitRepository fruitRepository;

    @AfterEach
    void tearDown() {
        // [테스트 환경구성] 각 테스트는 독립되어야 한다
        fruitRepository.deleteAll();
    }

    @DisplayName("상품(과일)을 추가한다")
    @Test
    void save() throws Exception {
        // given
        String content = objectMapper.writeValueAsString(FruitAddRequestDto.builder()
                .name("사과")
                .price(2000)
                .build());

        // when
        mockMvc.perform(post("/api/v1/fruit")
                        .contentType(APPLICATION_JSON)
                        .content(content))
                .andExpect(status().isOk())
                .andDo(print());

        // then
        assertThat(fruitRepository.count()).isEqualTo(1L);

        Fruit fruit = fruitRepository.findAll().get(0);
        assertThat(fruit.getName()).isEqualTo("사과");
        assertThat(fruit.getPrice()).isEqualTo(2000);
    }

    @DisplayName("상품(과일) 판매상태를 수정한다")
    @Test
    void changeSoldStatus() throws Exception {
        // given
        Fruit savedFruit = fruitRepository.save(Fruit.builder()
                .name("사과")
                .price(3000)
                .build());

        String content = objectMapper.writeValueAsString(savedFruit.getId());

        // when
        mockMvc.perform(put("/api/v1/fruit")
                        .contentType(APPLICATION_JSON)
                        .content(content))
                .andExpect(status().isOk())
                .andDo(print());

        // then
        Fruit fruit = fruitRepository.findById(savedFruit.getId()).orElseThrow(() -> new IllegalArgumentException("해당하는 과일을 찾을 수 없습니다"));
        assertThat(fruit.isSold()).isTrue();
    }

    @DisplayName("과일이름을 기준으로 판매금액, 미판매금액을 조회한다")
    @Test
    void findSalesAmountByName() throws Exception {
        // given
        String apple = "사과";

        Fruit fruit1 = fruitRepository.save(Fruit.builder().name(apple).price(3000).build());
        fruitRepository.save(Fruit.builder().name(apple).price(4000).build());
        Fruit fruit2 = fruitRepository.save(Fruit.builder().name(apple).price(3000).build());

        fruit1.changeSoldStatus();
        fruit2.changeSoldStatus();

        fruitRepository.save(fruit1);
        fruitRepository.save(fruit2);

        // when
        mockMvc.perform(get("/api/v1/fruit/stat?name={name}", apple))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.salesAmount").value(6000))
                .andExpect(jsonPath("$.notSalesAmount").value(4000))
                .andDo(print());

        // then
        assertThat(fruitRepository.salesAmountByName(apple).orElse(0L)).isEqualTo(6000);
        assertThat(fruitRepository.notSalesAmountByName(apple).orElse(0L)).isEqualTo(4000);
    }

    @DisplayName("과일이름을 기준으로 상품개수를 조회한다")
    @Test
    void countByName() throws Exception {
        // given
        String apple = "사과";
        String banana = "바나나";

        fruitRepository.save(Fruit.builder().name(apple).price(1000).build());
        fruitRepository.save(Fruit.builder().name(banana).price(2000).build());
        fruitRepository.save(Fruit.builder().name(apple).price(3000).build());

        // when
        mockMvc.perform(get("/api/v1/fruit/count?name={name}", apple))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.count").value(2))
                .andDo(print());

        // then
        assertThat(fruitRepository.countByName(apple)).isEqualTo(2L);
    }

    @DisplayName("[GTE] 특정 금액 이상의 상품목록을 조회한다")
    @Test
    void listByPriceWithGTE() throws Exception {
        // given
        String apple = "사과";
        String banana = "바나나";

        long standardAmount = 3000;

        fruitRepository.save(Fruit.builder().name(apple).price(4000).build());
        fruitRepository.save(Fruit.builder().name(banana).price(6000).build());

        // when
        mockMvc.perform(get("/api/v1/fruit/list?option={option}&price={price}", PriceComparison.GTE, standardAmount))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("사과"))
                .andExpect(jsonPath("$[0].price").value(4000))
                .andExpect(jsonPath("$[1].name").value("바나나"))
                .andExpect(jsonPath("$[1].price").value(6000))
                .andDo(print());
    }

    @DisplayName("[LTE] 특정 금액 이하의 상품목록을 조회한다")
    @Test
    void listByPriceWithLTE() throws Exception {
        // given
        String apple = "사과";
        String banana = "바나나";

        long standardAmount = 5000;

        fruitRepository.save(Fruit.builder().name(apple).price(4000).build());
        fruitRepository.save(Fruit.builder().name(banana).price(6000).build());

        // when
        mockMvc.perform(get("/api/v1/fruit/list?option={option}&price={price}", PriceComparison.LTE, standardAmount))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].name").value("사과"))
                .andExpect(jsonPath("$[0].price").value(4000))
                .andDo(print());

    }
}
