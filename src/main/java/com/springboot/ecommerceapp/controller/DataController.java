package com.springboot.ecommerceapp.controller;

import com.springboot.ecommerceapp.exception.CategoryNotFoundException;
import com.springboot.ecommerceapp.models.Category;
import com.springboot.ecommerceapp.models.DataObject;
import com.springboot.ecommerceapp.services.CategoryService;
import com.springboot.ecommerceapp.services.DataPopulatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/data")
public class DataController {

    private DataPopulatorService dataPopulatorService;

    @Autowired
    public DataController(DataPopulatorService dataPopulatorService) {
        this.dataPopulatorService = dataPopulatorService;
    }

    @PostMapping
    public void saveCategory(@Valid @RequestBody DataObject dataObject) {
        dataPopulatorService.populateData(dataObject.getCategoryId(), dataObject.getUrl());
    }
}