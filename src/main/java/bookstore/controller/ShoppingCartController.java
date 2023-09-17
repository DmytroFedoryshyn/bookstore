package bookstore.controller;

import bookstore.dto.cart.AddToCartDto;
import bookstore.dto.cart.CartResponseDto;
import bookstore.dto.cartItem.CartItemResponseDto;
import bookstore.dto.cartItem.UpdateCartItemDto;
import bookstore.service.ShoppingCartService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cart")
@RequiredArgsConstructor
@Tag(name = "Shopping Cart Controller", description = "Endpoints for managing the shopping cart")
public class ShoppingCartController {
    private final ShoppingCartService service;


    @Operation(summary = "Get the user's shopping cart")
    @GetMapping
    public CartResponseDto get() {
        return service.getByUser();
    }

    @Operation(summary = "Add an item to the shopping cart")
    @PostMapping()
    @ApiResponse(responseCode = "204", description = "Item added to the cart")
    public void addCartItemTo(
            @RequestBody @Parameter(description = "Item to add to the cart")
                    AddToCartDto addToCartDto) {
        service.addItemToCart(addToCartDto);
    }

    @Operation(summary = "Update a shopping cart item by ID")
    @PutMapping("/cart-items/{id}")
    @ApiResponse(responseCode = "200", description = "Cart item updated", content = {
            @Content(mediaType = "application/json", schema =
            @Schema(implementation = CartItemResponseDto.class),
                    examples = @ExampleObject(value = "{\"id\": 1,"
                            + " \"book\": {\"id\": 1, \"title\": \"Book Title\"}, \"quantity\": 2}"))
    })
    @ApiResponse(responseCode = "404", description = "Cart item not found")
    public CartItemResponseDto updateCartItem(
            @PathVariable @Parameter(description = "ID of the cart item to update") Long id,
            @RequestBody @Parameter(description = "Updated cart item details") UpdateCartItemDto dto) {
        return service.update(id, dto);
    }
    @Operation(summary = "Delete a cart item by ID")
    @DeleteMapping("/cart-items/{id}")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Cart item deleted"),
            @ApiResponse(responseCode = "404", description = "Cart item not found")
    })
    public void deleteCartItem(@PathVariable @Parameter(description = "ID of the cart item to delete") Long id) {
        service.delete(id);
    }
}
