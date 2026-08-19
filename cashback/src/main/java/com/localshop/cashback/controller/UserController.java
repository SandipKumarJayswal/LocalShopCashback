package com.localshop.cashback.controller;

import com.localshop.cashback.dto.SendOtpRequest;
import com.localshop.cashback.dto.ResetPasswordWithOtpRequest;
import com.localshop.cashback.service.OtpService;
import com.localshop.cashback.dto.LoginRequest;
import com.localshop.cashback.dto.RegisterRequest;
import com.localshop.cashback.entity.User;
import com.localshop.cashback.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private OtpService otpService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest request) {
        try {
            User user = userService.register(request);
            return ResponseEntity.ok(user);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        try {
            User user = userService.login(request);
            return ResponseEntity.ok(user);
        } catch (RuntimeException e) {
            return ResponseEntity.status(401).body(e.getMessage());
        }
    }

    @PostMapping("/send-otp")
    public ResponseEntity<?> sendOtp(@RequestBody SendOtpRequest request) {
        boolean emailExists = userService.checkEmailExists(request.getEmail());
        if (!emailExists) {
            return ResponseEntity.badRequest().body("No account found with this email");
        }
        otpService.generateOtp(request.getEmail());
        return ResponseEntity.ok("OTP sent successfully. Check the backend console.");
    }

    @PostMapping("/reset-password")
    public ResponseEntity<?> resetPassword(@RequestBody ResetPasswordWithOtpRequest request) {
        boolean isValidOtp = otpService.verifyOtp(request.getEmail(), request.getOtp());
        if (!isValidOtp) {
            return ResponseEntity.badRequest().body("Invalid or expired OTP");
        }
        try {
            userService.updatePassword(request.getEmail(), request.getNewPassword());
            otpService.clearOtp(request.getEmail());
            return ResponseEntity.ok("Password reset successful. You can now log in.");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}