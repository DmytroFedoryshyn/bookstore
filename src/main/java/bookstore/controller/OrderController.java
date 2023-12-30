package bookstore.controller;

import bookstore.dto.order.OrderRequestDto;
import bookstore.dto.order.OrderResponseDto;
import bookstore.dto.order.OrderUpdateDto;
import bookstore.dto.orderitem.OrderItemResponseDto;
import bookstore.service.OrderItemService;
import bookstore.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
@ControllerAdvice
@Tag(name = "Order Controller", description = "Endpoints for managing orders")
public class OrderController {
    private final OrderService orderService;
    private final OrderItemService orderItemService;
    
    @Operation(summary = "Place a new order")
    @ResponseStatus(HttpStatus.CREATED)
    @ApiResponse(responseCode = "201", description = "Order created", content = {
            @Content(mediaType = "application/json", schema =
            @Schema(implementation = OrderResponseDto.class),
            examples = @ExampleObject(value = "{\n"
                                              + "    \"id\": 1,\n"
                                              + "    \"shippingAddress\": \"address\",\n"
                                              + "    \"orderItems\": [\n"
                                              + "      {\n"
                                              + "        \"bookId\": 1,\n"
                                              + "        \"quantity\": 2\n"
                                              + "      }\n"
                                              + "    ]\n"
                                              + "}"))
    })
    @PostMapping
    public OrderResponseDto placeOrder(@Valid @RequestBody OrderRequestDto order,
                                       Authentication authentication) {
        return orderService.save(order, authentication.getName());
    }

    @Operation(summary = "Get all orders")
    @PreAuthorize("hasAuthority('USER')")
    @GetMapping
    public List<OrderResponseDto> getAllOrders(Authentication authentication,
                                               @Parameter(description =
                                                   "Page number (zero-based)", example = "0")
            @RequestParam(defaultValue = "0") int page,
                                               @Parameter(description =
                                                   "Number of items per page", example = "10")
                                                   @RequestParam(defaultValue = "10") int size,
                                               @Parameter(description =
                                                   "Sort order and fields",
                                                   example = "orderDate,desc")
                                                   @RequestParam(defaultValue = "orderDate,desc")
                                                       String sort) {
        return orderService.getByUser(authentication.getName(), page, size, sort);
    }

    @Operation(summary = "Update an existing order status by ID")
    @ApiResponse(responseCode = "200", description = "Order updated", content = {
                @Content(mediaType = "application/json", schema =
                @Schema(implementation = OrderResponseDto.class),
            examples = @ExampleObject(value = "{\"status\": \"DELIVERED\"}"))
    })
    @ApiResponse(responseCode = "404", description = "Order not found")
    @PreAuthorize("hasAuthority('ADMIN')")
    @PatchMapping("/{id}")
    public OrderResponseDto updateOrder(@PathVariable Long id,
                                        @Valid @RequestBody OrderUpdateDto dto) {
        return orderService.update(dto, id);
    }

    @Operation(summary = "Get all orders")
    @GetMapping("/{orderId}/items")
    public List<OrderItemResponseDto> getOrderItems(@PathVariable Long orderId) {
        return orderItemService.getAllByOrderId(orderId);
    }
    
    @Operation(summary = "Get all orders")
    @GetMapping("/{orderId}/items/{orderItemId}")
    public OrderItemResponseDto getOrderItemByIdAndOrder(@PathVariable Long orderId,
                                                         @PathVariable Long orderItemId) {
        return orderItemService.findByOrderIdAndOrderItemId(orderId, orderItemId);
    }
}
