package com.springboot.ecommerceapp.unittests;

import com.springboot.ecommerceapp.exception.CategoryNotFoundException;
import com.springboot.ecommerceapp.exception.UserNotFoundException;
import com.springboot.ecommerceapp.models.Address;
import com.springboot.ecommerceapp.models.Category;
import com.springboot.ecommerceapp.repositories.CategoryRepository;
import com.springboot.ecommerceapp.services.CategoryService;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.Silent.class)
public class CategoryServiceTests {

  @Mock
  private CategoryRepository categoryRepository;

  @InjectMocks
  private CategoryService categoryService;

  private Category category;

  @Before
  public void setup() {
    category = new Category();
    category.setId(1);
    category.setName("Electronics");
  }

  @DisplayName("Add a category")
  @Test
  public void addCategory() throws UserNotFoundException {
    given(categoryRepository.findById(category.getId()))
        .willReturn(Optional.empty());

    when(categoryRepository.save(any(Category.class))).thenAnswer(invocation -> {
      return category;
    });

    Category savedCategory = categoryService.addOrUpdateCategory(this.category);

    assertThat(savedCategory).isNotNull();
    assertThat(savedCategory.getName()).isEqualTo(this.category.getName());
  }

  @DisplayName("Fetch an category based on ID")
  @Test
  public void givenCategoryID_fetchCategory() throws CategoryNotFoundException {
    given(categoryRepository.findById(category.getId()))
        .willReturn(Optional.empty());

    when(categoryRepository.save(any(Category.class))).thenAnswer(invocation -> {
      return category;
    });

    when(categoryRepository.findById(any(Integer.class))).thenAnswer(invocation -> {
      return Optional.of(category);
    });

    Category savedCategory = categoryService.getCategory(category.getId());

    assertThat(savedCategory).isNotNull();
    assertThat(savedCategory.getName()).isEqualTo(category.getName());
  }

}
