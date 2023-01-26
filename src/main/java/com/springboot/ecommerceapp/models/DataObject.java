package com.springboot.ecommerceapp.models;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
public class DataObject {
    @NotNull(message = "Provide a valid categoryId")
    private Integer categoryId;

    @NotNull(message = "Provide a valid URL to populate the products")
    private String url;
}
