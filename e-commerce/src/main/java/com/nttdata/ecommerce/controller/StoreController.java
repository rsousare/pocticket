package com.nttdata.ecommerce.controller;

import com.nttdata.ecommerce.service.StoreServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/store")
@AllArgsConstructor
public class StoreController {

    private final StoreServiceImpl storeServiceImpl;
    @GetMapping("/details")
    public String getStoreDetails() {
        return storeServiceImpl.getStoreInfo();
    }
}
