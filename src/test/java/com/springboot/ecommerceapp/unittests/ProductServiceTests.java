package com.springboot.ecommerceapp.unittests;

import com.springboot.ecommerceapp.exception.CategoryNotFoundException;
import com.springboot.ecommerceapp.exception.UserNotFoundException;
import com.springboot.ecommerceapp.models.Category;
import com.springboot.ecommerceapp.models.Product;
import com.springboot.ecommerceapp.repositories.CategoryRepository;
import com.springboot.ecommerceapp.repositories.ProductRepository;
import com.springboot.ecommerceapp.services.CategoryService;
import com.springboot.ecommerceapp.services.ProductService;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.Silent.class)
public class ProductServiceTests {

  @Mock
  private CategoryService categoryService;

  @Mock
  private ProductRepository productRepository;

  @InjectMocks
  private ProductService productService;

  private Product product;

  @Before
  public void setup() {
    product = new Product();
    product.setId(1);
    product.setName("Sony OLED TV");
    product.setCategoryId(1);
    product.setPrice(300);
  }

  @DisplayName("Add a new product")
  @Test
  public void add_product() {
    given(productRepository.findById(product.getId()))
        .willReturn(Optional.empty());

    when(productRepository.save(any(Product.class))).thenAnswer(invocation -> {
      return product;
    });

    Product savedProduct = productService.addProduct(this.product);

    assertThat(savedProduct).isNotNull();
    assertThat(savedProduct.getName()).isEqualTo(this.product.getName());
    assertThat(savedProduct.getPrice()).isEqualTo(this.product.getPrice());
  }

  @DisplayName("Fetch a product based on ID")
  @Test
  public void givenProductID_fetchProduct() {
    given(productRepository.findById(product.getId()))
        .willReturn(Optional.empty());

    when(productRepository.save(any(Product.class))).thenAnswer(invocation -> {
      return product;
    });

    when(productRepository.findById(any(Integer.class))).thenAnswer(invocation -> {
      return Optional.of(product);
    });

    Optional<Product> optionalProduct = productService.getProduct(product.getId());
    assertThat(optionalProduct.get()).isNotNull();

    Product savedProduct = optionalProduct.get();
    assertThat(savedProduct.getName()).isEqualTo(product.getName());
    assertThat(savedProduct.getPrice()).isEqualTo(product.getPrice());
  }

  @DisplayName("Fetch products based on pattern")
  @Test
  public void fetchProductsBasedOnPattern() {
    given(productRepository.findById(product.getId()))
        .willReturn(Optional.empty());

    when(productRepository.findByNameContaining(any(String.class))).thenAnswer(invocation -> {
      return Arrays.asList(product);
    });

    List<Product> products = productService.getProductsByPattern("OLED");
    assertThat(products.size()).isNotEqualTo(0);

    Product savedProduct = products.get(0);
    assertThat(savedProduct.getName()).isEqualTo(product.getName());
    assertThat(savedProduct.getPrice()).isEqualTo(product.getPrice());
  }

  @DisplayName("Fetch products based on name")
  @Test
  public void fetchProductsBasedOnName() {
    given(productRepository.findById(product.getId()))
        .willReturn(Optional.empty());

    when(productRepository.findByName(any(String.class))).thenAnswer(invocation -> {
      return Optional.of(product);
    });

    Optional<Product> optionalProduct = productService.getProductByName("Sony OLED TV");
    assertThat(optionalProduct.get()).isNotNull();

    Product savedProduct = optionalProduct.get();
    assertThat(savedProduct.getName()).isEqualTo(product.getName());
    assertThat(savedProduct.getPrice()).isEqualTo(product.getPrice());
  }

  @DisplayName("Delete product")
  @Test
  public void deleteProduct() {
    given(productRepository.findById(product.getId()))
        .willReturn(Optional.empty());

    productService.deleteProduct(product.getId());
  }

}
