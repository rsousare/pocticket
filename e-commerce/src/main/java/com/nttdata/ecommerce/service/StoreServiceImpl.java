
package com.nttdata.ecommerce.service;

import com.nttdata.ecommerce.configuration.StoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Data
public class StoreServiceImpl implements StoreService {

    private final StoreProperties storeProperties;
    @Override
    public String getStoreInfo() {
        String name = storeProperties.getName();
        String city = storeProperties.getCity();
        String country = storeProperties.getCountry();

        return String.format("%s - %s - %s", name, city, country);
    }
}
