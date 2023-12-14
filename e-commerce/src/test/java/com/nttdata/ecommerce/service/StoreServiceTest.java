package com.nttdata.ecommerce.service;

import com.nttdata.ecommerce.controller.StoreController;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
class StoreServiceTest {

    @Mock
    private StoreServiceImpl storeServiceImpl;

    @InjectMocks
    private StoreController storeController;

    @AfterEach
    void teardown() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Testing getStoreInfo() method in StoreController")
    void getStoreDetailsTest() {
        when(storeServiceImpl.getStoreInfo()).thenReturn("Mocked eCom store service");
        String result = storeController.getStoreDetails();

        Mockito.verify(storeServiceImpl, Mockito.times(1)).getStoreInfo();
        assertEquals("Mocked eCom store service", result);
    }
}