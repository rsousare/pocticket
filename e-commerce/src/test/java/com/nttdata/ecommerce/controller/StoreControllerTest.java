package com.nttdata.ecommerce.controller;

import com.nttdata.ecommerce.configuration.StoreProperties;
import com.nttdata.ecommerce.service.StoreServiceImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class StoreControllerTest {

    @Autowired
    private StoreProperties storeProperties;
    @InjectMocks
    private StoreServiceImpl storeServiceImpl;

    @AfterEach
    void teardown() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Test the store information and method getStoreInfo()")
    void getStoreInfoTest() {

        StoreProperties storePropertiesMock = mock(StoreProperties.class);

        when(storePropertiesMock.getName()).thenReturn("GStore");
        when(storePropertiesMock.getCity()).thenReturn("Lagos");
        when(storePropertiesMock.getCountry()).thenReturn("Portugal");

        storeServiceImpl = new StoreServiceImpl(storePropertiesMock);

        String storeInfo = storeServiceImpl.getStoreInfo();

        assertEquals("GStore - Lagos - Portugal", storeInfo);
    }


}