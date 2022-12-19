package com.example.ecommerce.Controller;

import com.example.ecommerce.Common.ApiResponse;
import com.example.ecommerce.Dto.Checkout.CheckoutItemDto;
import com.example.ecommerce.Dto.Checkout.StripeResponse;
import com.example.ecommerce.Exception.AuthenticationFailException;
import com.example.ecommerce.Exception.OrderNotFoundException;
import com.example.ecommerce.Model.Order;
import com.example.ecommerce.Model.User;
import com.example.ecommerce.Service.OrderService;
import com.example.ecommerce.Service.UserService;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/order")
public class OrderController {
    @Autowired
    private OrderService orderService;

    @Autowired
    private UserService userService;


    // stripe create session API
    @PostMapping("/create-checkout-session")
    public ResponseEntity<StripeResponse> checkoutList(@RequestBody List<CheckoutItemDto> checkoutItemDtoList) throws StripeException {
        // create the stripe session
        Session session = orderService.createSession(checkoutItemDtoList);
        StripeResponse stripeResponse = new StripeResponse(session.getId());
        // send the stripe session id in response
        return new ResponseEntity<>(stripeResponse, HttpStatus.OK);
    }

    // place order after checkout
    @PostMapping("/add")
    public ResponseEntity<ApiResponse> placeOrder(@RequestParam("id") long id, @RequestParam("sessionId") String sessionId) {
        // retrieve user
        User user = userService.getUserById(id);
        // place the order
        orderService.placeOrder(user, sessionId);
        return new ResponseEntity<>(new ApiResponse(true, "Order has been placed"), HttpStatus.CREATED);
    }

    // get all orders
    @GetMapping("/")
    public ResponseEntity<List<Order>> getAllOrders(@RequestParam("id") long id) throws AuthenticationFailException {
        User user = userService.getUserById(id);
        // get orders
        List<Order> orderDtoList = orderService.listOrders(user);

        return new ResponseEntity<>(orderDtoList, HttpStatus.OK);
    }


    // get orderitems for an order
    @GetMapping("/{id}")
    public ResponseEntity<Object> getOrderById(@PathVariable("id") long id, @RequestParam("token") String token) {
        // validate token
        try {
            Order order = orderService.getOrder(id);
            return new ResponseEntity<>(order,HttpStatus.OK);
        }
        catch (OrderNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(),HttpStatus.NOT_FOUND);
        }

    }

}
