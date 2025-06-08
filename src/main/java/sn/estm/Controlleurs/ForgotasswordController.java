package sn.estm.Controlleurs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import sn.estm.Services.ForgotPasswordService;

@RestController
@CrossOrigin("http://localhost:4200")
public class ForgotasswordController {

    @Autowired
    private ForgotPasswordService service;

    public ForgotasswordController(ForgotPasswordService service) {
        this.service = service;
    }

    @SuppressWarnings("unused")
    @PostMapping("/forgot-password")
    public ResponseEntity<?> forgotPassword(@RequestParam String email) {
        String otp = service.sendOtpToEmail(email);
        return ResponseEntity.ok("Code OTP envoyé à l'email.");
    }
    @PostMapping("/verify-otp")
    public ResponseEntity<?> verifyOtp(@RequestParam String email, @RequestParam String otp) {
        boolean valid = service.verifyOtp(email, otp);
        if (valid) {
            service.invalidateOtp(email);
            return ResponseEntity.ok("OTP valide !");
        } else {
            return ResponseEntity.badRequest().body("Code OTP invalide.");
        }
    }
}
