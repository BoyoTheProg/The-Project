package com.movieapp.model;
import java.security.SecureRandom;

public class PromoCodeGenerator {

    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private static final int CODE_LENGTH = 8;

    private static SecureRandom random = new SecureRandom();

    public static String generateRandomPromoCode() {
        StringBuilder promoCode = new StringBuilder(CODE_LENGTH);

        for (int i = 0; i < CODE_LENGTH; i++) {
            int randomIndex = random.nextInt(CHARACTERS.length());
            promoCode.append(CHARACTERS.charAt(randomIndex));
        }

        return promoCode.toString();
    }

    public static void main(String[] args) {
        // Example usage
        String promoCode = generateRandomPromoCode();
        System.out.println("Generated Promo Code: " + promoCode);
    }
}
