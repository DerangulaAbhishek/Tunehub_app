package com.example.demo.controller;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.example.demo.entity.Users;
import com.example.demo.repository.UsersReporsitory;
import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import com.razorpay.Utils;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/payment")
@CrossOrigin(origins = "http://localhost:3000")
public class PaymentController {

    private static final String RAZORPAY_KEY_ID = "rzp_test_RTyZoE0H5fz84x";
    private static final String RAZORPAY_SECRET_KEY = "OzyT4tCw7pgFzsVQybMLg7b2";

    @Autowired
    private UsersReporsitory usersReporsitory;

    // ------------------------------------------------------------
    // ✅ 1️⃣ CREATE ORDER
    // ------------------------------------------------------------
    @PostMapping("/createOrder")
    public Map<String, Object> createOrder(@RequestBody Map<String, Object> requestData) {
        Map<String, Object> response = new HashMap<>();

        try {
            RazorpayClient razorpay = new RazorpayClient(RAZORPAY_KEY_ID, RAZORPAY_SECRET_KEY);

            int amount = (int) requestData.get("amount");
            String currency = (String) requestData.get("currency");

            JSONObject orderRequest = new JSONObject();
            orderRequest.put("amount", amount);
            orderRequest.put("currency", currency);
            orderRequest.put("receipt", "receipt#1");
            orderRequest.put("payment_capture", 1);

            Order order = razorpay.orders.create(orderRequest);

            response.put("orderId", order.get("id"));
            response.put("amount", order.get("amount"));
            response.put("currency", order.get("currency"));
            response.put("key", RAZORPAY_KEY_ID);

        } catch (Exception e) {
            e.printStackTrace();
            response.put("error", "Order creation failed");
        }

        return response;
    }

    // ------------------------------------------------------------
    // ✅ 2️⃣ VERIFY PAYMENT SIGNATURE
    // ------------------------------------------------------------
    @PostMapping("/verify")
    public Map<String, Object> verifyPayment(@RequestBody Map<String, Object> paymentData) {
        Map<String, Object> response = new HashMap<>();

        try {
            String orderId = (String) paymentData.get("razorpay_order_id");
            String paymentId = (String) paymentData.get("razorpay_payment_id");
            String signature = (String) paymentData.get("razorpay_signature");

            String payload = orderId + "|" + paymentId;
            boolean isValid = Utils.verifySignature(payload, signature, RAZORPAY_SECRET_KEY);

            if (isValid) {
                response.put("status", "success");
                response.put("message", "Payment verified successfully");
            } else {
                response.put("status", "failed");
                response.put("message", "Invalid payment signature");
            }

        } catch (Exception e) {
            e.printStackTrace();
            response.put("status", "error");
            response.put("message", "Verification error");
        }

        return response;
    }

    // ------------------------------------------------------------
    // ✅ 3️⃣ UPDATE PREMIUM (SAFE FIX)
    // ------------------------------------------------------------
    @PostMapping("/updatePremium")
    public Map<String, Object> updatePremium(@RequestBody Map<String, Object> data) {
        Map<String, Object> response = new HashMap<>();

        try {
            // Safe null check
            Object idObj = data.get("userId");
            if (idObj == null) {
                response.put("status", "error");
                response.put("message", "userId missing");
                return response;
            }

            int userId = Integer.parseInt(idObj.toString());

            Users user = usersReporsitory.findById(userId).orElse(null);
            if (user == null) {
                response.put("status", "error");
                response.put("message", "User not found");
                return response;
            }

            user.setPremium(true); // will store as 1 in DB
            usersReporsitory.save(user);

            response.put("status", "success");
            response.put("message", "Premium activated successfully");

        } catch (Exception e) {
            e.printStackTrace();
            response.put("status", "error");
            response.put("message", "Failed to update premium");
        }

        return response;
    }
}
