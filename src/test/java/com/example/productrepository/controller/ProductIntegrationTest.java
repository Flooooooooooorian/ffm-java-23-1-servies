package com.example.productrepository.controller;

import com.example.productrepository.products.ProductRepository;
import com.example.productrepository.products.models.Product;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class ProductIntegrationTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    @DirtiesContext
    void testGetAllProducts() throws Exception {
        //GIVEN
        productRepository.save(new Product("1", "test", 5));

        //WHEN
        mockMvc.perform(MockMvcRequestBuilders.get("/api/products"))

                //THEN
                .andExpect(status().isOk())
                .andExpect(content().json("""
                        [
                        {
                        "id": "1",
                        "title": "test",
                        "price": 5
                        }
                        ]
                        """));

    }

    @Test
    @DirtiesContext
    void addProductTest() throws Exception {
        //GIVEN

        //WHEN
        mockMvc.perform(MockMvcRequestBuilders.post("/api/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "title": "test-title",
                                    "price": 23
                                }
                                """)
                )

                //THEN
                .andExpect(status().isOk())
                .andExpect(content().json("""
                        {
                            "title": "test-title",
                            "price": 23
                        }
                        """))
                .andExpect(jsonPath("$.id").isNotEmpty());
    }

    @Test
    @DirtiesContext
    void testFindProductById() throws Exception {
        //GIVEN
        String body = mockMvc.perform(MockMvcRequestBuilders.post("/api/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "title": "test-title",
                                    "price": 23
                                }
                                """)
                )
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        Product responseProduct = objectMapper.readValue(body, Product.class);

        //WHEN
        mockMvc.perform(MockMvcRequestBuilders.get("/api/products/" + responseProduct.id()))

                //THEN
                .andExpect(status().isOk())
                .andExpect(content().json("""
                        {
                        "title": "test-title",
                        "price": 23
                        }                   
                        """))
                .andExpect(jsonPath("$.id").value(responseProduct.id()));

    }
}
