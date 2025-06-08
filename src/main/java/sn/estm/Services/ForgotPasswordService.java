package sn.estm.Services;

import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class ForgotPasswordService {
    private final SecureRandom random = new SecureRandom();
    private final ConcurrentHashMap<String, String> otpStore = new ConcurrentHashMap<>();

    // Génère un code OTP à 6 chiffres

    public String generateOtp() {
        int otp = 100000 + random.nextInt(900000); // 6 chiffres
        return String.valueOf(otp);
    }

    // Génère et associe un code OTP à l'email
    public String sendOtpToEmail(String email) {
        String otp = generateOtp();
        otpStore.put(email, otp);

        // Simulation d'envoi d'email (à remplacer par un vrai service mail)
        System.out.printf("OTP envoyé à %s : %s%n", email, otp);

        return otp;
    }

    // Vérifie le code OTP
    public boolean verifyOtp(String email, String otp) {
        String storedOtp = otpStore.get(email);
        return otp != null && otp.equals(storedOtp);
    }

    // Optionnel : nettoyer le code une fois vérifié
    public void invalidateOtp(String email) {
        otpStore.remove(email);
    }
}
