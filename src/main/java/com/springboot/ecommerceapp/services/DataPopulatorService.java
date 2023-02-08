package com.springboot.ecommerceapp.services;

import com.springboot.ecommerceapp.models.Product;
import com.springboot.ecommerceapp.models.ProductData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class DataPopulatorService {

    ProductService productService;

    @Autowired
    public DataPopulatorService(ProductService productService) {
        this.productService = productService;
    }

    public void populateData(Integer categoryId, String url) {
        RestTemplate template = new RestTemplate();
        ResponseEntity<List<ProductData>> productResponse = template.exchange(url, HttpMethod.GET, null, new ParameterizedTypeReference<List<ProductData>>(){});
        List<ProductData> productDataList = productResponse.getBody();

        List<Product> products = new ArrayList<>();

        productDataList.forEach(pd -> {
            Product p = new Product();
            p.setCategoryId(categoryId);
            p.setName(pd.getName());
            p.setImage(pd.getImg());

            if (pd.getPrice() != null) {
                p.setPrice(Double.parseDouble(pd.getPrice().replaceAll(",","")));
            }

            if (pd.getMrp() != null) {
                p.setMaxPrice(Double.parseDouble(pd.getMrp().replaceAll(",","")));
            }

            p.setWeight(Math.random() * 100);

            Optional<Product> product = productService.getProductByName(pd.getName());
            if (!product.isPresent()) {
                productService.addProduct(p);
            }
        });
    }
}
