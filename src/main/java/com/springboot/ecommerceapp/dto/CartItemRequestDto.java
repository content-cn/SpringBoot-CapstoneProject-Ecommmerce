package com.springboot.ecommerceapp.dto;

import com.springboot.ecommerceapp.models.CartItem;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
@NoArgsConstructor
@ToString
public class CartItemRequestDto {
    private Integer id;

    @NotNull(message = "Provide a valid userID")
    private Integer userId;

    @NotNull(message = "Provide a valid productID")
    private Integer productId;

    @NotNull(message = "Provide a valid quantity")
    private Integer quantity;

    public CartItem convertToCartItem() {
        CartItem item = new CartItem();
        item.setId(this.getId());
        item.setUserId(this.getUserId());
        item.setProductId(this.getProductId());
        item.setUserId(this.getUserId());
        item.setQuantity(this.getQuantity());
        item.setCreatedDate(new Date());
        return item;
    }
}
