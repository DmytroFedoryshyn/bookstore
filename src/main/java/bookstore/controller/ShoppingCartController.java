package bookstore.controller;

import bookstore.dto.cart.AddToCartDto;
import bookstore.dto.cart.CartResponseDto;
import bookstore.dto.cartitem.CartItemFullInfoResponseDto;
import bookstore.dto.cartitem.CartItemResponseDto;
import bookstore.service.ShoppingCartService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/cart")
@RequiredArgsConstructor
@Tag(name = "Shopping Cart Controller", description = "Endpoints for managing the shopping cart")
public class ShoppingCartController {
    private final ShoppingCartService service;

    @Operation(summary = "Get the user's shopping cart")
    @GetMapping
    public CartResponseDto get(Authentication authentication) {
        return service.getShoppingCartByUsername(authentication.getName());
    }

    @Operation(summary = "Add an item to the shopping cart")
    @PostMapping()
    @ApiResponse(responseCode = "204", description = "Item added to the cart")
    public void addCartItemTo(Authentication authentication,
                              @RequestBody @Parameter(description = "Item to add to the cart")
                              @Valid AddToCartDto addToCartDto) {
        service.addItemToCart(addToCartDto, authentication.getName());
    }

    @Operation(summary = "Update a shopping cart item by ID")
    @PutMapping("/cart-items/{id}")
    @ApiResponse(responseCode = "200", description = "Cart item updated", content = {
            @Content(mediaType = "application/json", schema =
            @Schema(implementation = CartItemResponseDto.class),
                    examples = @ExampleObject(value = "{\"id\": 1,"
                            + " \"book\": {\"id\": 1, \"title\":"
                            + " \"Book Title\"}, \"quantity\": 2}"))
    })
    @ApiResponse(responseCode = "404", description = "Cart item not found or doesn't"
            + " belong to the user's cart")
    public CartItemResponseDto updateCartItem(Authentication authentication,
                                              @PathVariable
                                              @Parameter
                                                      (description = "ID of the cart"
                                                              + " item to update") Long id,
                                              @RequestBody
                                                  @Parameter(description =
                                                          "Updated cart item details")
                                              @Valid AddToCartDto dto) {
        checkCartItemModificationPermission(authentication, id);
        return service.updateCartItem(id, dto);
    }

    @Operation(summary = "Delete a cart item by ID")
    @DeleteMapping("/cart-items/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Cart item deleted"),
            @ApiResponse(responseCode = "404", description = "Cart item not found")
    })
    public void deleteCartItem(Authentication authentication, @PathVariable @Parameter
            (description = "ID of the cart item to delete") Long id) {
        checkCartItemModificationPermission(authentication, id);

        service.deleteCartItem(id);
    }

    private void checkCartItemModificationPermission(Authentication authentication,
                                                     @Parameter
                                                             (description = "ID of the cart"
                                                                     + " item to delete")
                                                     @PathVariable Long id) {
        CartItemFullInfoResponseDto cartItem = service.getCartItemById(id);

        CartResponseDto cart = service.getShoppingCartByUsername(authentication.getName());
        if (cart == null || !cartItem.getShoppingCartId().equals(cart.getId())) {
            throw new AccessDeniedException("this shopping cart item doesn't"
                    + " belong to the current user");
        }
    }
}
