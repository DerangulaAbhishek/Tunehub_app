package com.example.demo.controller;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import com.example.demo.entity.Users;
import com.example.demo.services.UsersService;
import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import com.razorpay.Utils;

import jakarta.servlet.http.HttpSession;

@Controller
public class PaymentController {

    @Autowired
    UsersService service;

    private static final String RAZORPAY_KEY_ID = "rzp_test_H6tIcJ83WlS5V7";
    private static final String RAZORPAY_SECRET_KEY = "StASXzfGXSkDLRdISBcsDYqa";

    // Step 1: Create Order
    @PostMapping("/createOrder")
    @ResponseBody
    public String createOrder() {
        try {
            RazorpayClient razorpay = new RazorpayClient(RAZORPAY_KEY_ID, RAZORPAY_SECRET_KEY);

            JSONObject orderRequest = new JSONObject();
            orderRequest.put("amount", 50000); // Amount in paise (₹500 = 50000 paise)
            orderRequest.put("currency", "INR");
            orderRequest.put("receipt", "receipt#1");

            Order order = razorpay.orders.create(orderRequest);
            return order.toString();

        } catch (RazorpayException e) {
            e.printStackTrace();
            return "{\"error\":\"Order creation failed\"}";
        }
    }

    // Step 2: Verify Payment and Update Database
    @PostMapping("/verify")
    @ResponseBody
    public boolean verifyPayment(@RequestParam String orderId, 
                                 @RequestParam String paymentId,
                                 @RequestParam String signature, 
                                 HttpSession session) {
        try {
            // Step 2.1: Verify Payment Signature
            String verificationData = orderId + "|" + paymentId;
            boolean isValid = Utils.verifySignature(verificationData, signature, RAZORPAY_SECRET_KEY);

            if (isValid) {
                // Step 2.2: Update User is_premium Status
                String email = (String) session.getAttribute("email");
                if (email != null) {
                    Users user = service.getUser(email);
                    if (user != null) {
                        user.setPremium(true); // Set user as Premium
                        service.updateUser(user);
                    }
                }

                // Step 2.3: Redirect to Customer Home Page
                return true;
            } else {
                return false; // Payment verification failed
            }

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @GetMapping("/payment-success")
    public String paymentSuccess() {
        return "redirect:/customerHome"; // Redirect to customer home page
    }

    @GetMapping("/payment-failure")
    public String paymentFailure() {
        return "login"; // Redirect to login page
    }

    @GetMapping("/pay")
    public String payPage() {
        return "pay"; // Ensure you have "pay.html" in the templates folder
    }
}
