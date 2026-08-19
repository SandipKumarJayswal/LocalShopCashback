package com.localshop.cashback.service;

import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class OtpService {

    // Temporarily stores OTPs in memory: email -> OTP
    // (In production, this would use Redis or a database table with expiry)
    private final Map<String, String> otpStore = new ConcurrentHashMap<>();

    public String generateOtp(String email) {
        String otp = String.valueOf(100000 + new Random().nextInt(900000)); // 6-digit OTP
        otpStore.put(email, otp);

        // Simulates sending SMS - prints to console instead
        System.out.println("=================================================");
        System.out.println(" OTP for " + email + " is: " + otp);
        System.out.println(" (In production this would be sent via SMS gateway)");
        System.out.println("=================================================");

        return otp;
    }

    public boolean verifyOtp(String email, String otp) {
        String storedOtp = otpStore.get(email);
        return storedOtp != null && storedOtp.equals(otp);
    }

    public void clearOtp(String email) {
        otpStore.remove(email);
    }
}