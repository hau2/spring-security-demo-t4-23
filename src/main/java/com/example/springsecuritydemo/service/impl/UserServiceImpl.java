package com.example.springsecuritydemo.service.impl;

import com.example.springsecuritydemo.entity.Role;
import com.example.springsecuritydemo.entity.User;
import com.example.springsecuritydemo.exception.PrivateCodeHasExpired;
import com.example.springsecuritydemo.exception.UserAlreadyExistException;
import com.example.springsecuritydemo.exception.UserNotFoundException;
import com.example.springsecuritydemo.model.ERole;
import com.example.springsecuritydemo.payload.request.ResetPasswordRequest;
import com.example.springsecuritydemo.query.PrivateCodeResult;
import com.example.springsecuritydemo.repository.RoleRepository;
import com.example.springsecuritydemo.repository.UserRepository;
import com.example.springsecuritydemo.service.IUserRoleService;
import com.example.springsecuritydemo.service.IUserService;
import com.example.springsecuritydemo.template.RequestResetPasswordTemplate;
import com.example.springsecuritydemo.utils.CustomMailSender;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import static com.example.springsecuritydemo.constants.Constants.RESET_PW;

@Service
public class UserServiceImpl implements IUserService {
    @Value("${demo.app.verifyCodeDurationMinutes}")
    private Integer verifyCodeDurationMinutes;

    @Autowired
    UserRepository userRepository;

    @Autowired
    CustomMailSender mailSender;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    IUserRoleService userRoleService;

    @Autowired
    RoleRepository roleRepository;

    @Override
    public Optional<User> findByMail(String mail) {
        return userRepository.findByMail(mail);
    }

    @Override
    public void updateIsEnableByMail(Boolean isEnable, String username) {
        userRepository.updateIsEnableByMail(isEnable, username);
    }

    @Override
    public void sendMailResetPassword(String mail) {
        // Check private code in the database if private code has existed -> not generate a new private code
        PrivateCodeResult privateCodeResult = userRepository.findPrivateCodeByMail(mail);
        String privateCode = privateCodeResult.getPrivateCode();

        if (privateCode == null || isExpiredPrivateCode(privateCode)) {
            privateCode = UUID.randomUUID().toString().replace("-", "");
            // Expiry date code will expire after 2 minute from now
            LocalDateTime timeExpired = LocalDateTime.now().plusSeconds(verifyCodeDurationMinutes);
            userRepository.setPrivateCodeForUser(privateCode, timeExpired, mail);
        }

        String finalPrivateCode = privateCode;
        Thread t = new Thread(() -> {
            try {
                sendCodeResetToUser(mail, finalPrivateCode);
            } catch (MessagingException e) {
                throw new RuntimeException(e);
            }
        });

        t.start();
    }

    @Override
    public User finByPrivateCode(String privateCode) throws UserNotFoundException, PrivateCodeHasExpired {
        // Check expiration private code
        Boolean isExpiredPrivateCode = isExpiredPrivateCode(privateCode);
        if (isExpiredPrivateCode) throw new PrivateCodeHasExpired("Private code has expired");

        return userRepository.finByPrivateCode(privateCode).orElseThrow(() -> new UserNotFoundException("User not found"));
    }

    @Override
    public void setNewPassword(ResetPasswordRequest request) throws PrivateCodeHasExpired, UserNotFoundException {
        // Check expiration private code
        Boolean isExpiredPrivateCode = isExpiredPrivateCode(request.getPrivateCode());
        if (isExpiredPrivateCode) throw new PrivateCodeHasExpired("Private code has expired");
        userRepository.setNewPassword(encoder.encode(request.getNewPassword()), request.getPrivateCode());

        // Unlock this user if blocked
        User user = finByPrivateCode(request.getPrivateCode());
        if (!user.isEnable()) {
            userRepository.unlockUser(request.getPrivateCode());
        }

        // Delete private code
        userRepository.killPrivateCode(request.getPrivateCode());
    }

    private void sendCodeResetToUser(String mail, String code) throws MessagingException {
        mailSender.send(mail, RESET_PW, RequestResetPasswordTemplate.build(mail, "localhost:8080/reset?code=" + code));
    }

    private boolean emailExists(String mail) {
        return userRepository.findByMail(mail).isPresent();
    }

    @Override
    public User register(User user) throws UserAlreadyExistException {
        if (emailExists(user.getMail())) {
            throw new UserAlreadyExistException(user.getMail());
        }
        User resultUser = userRepository.save(user);
        Optional<Role> role = roleRepository.findByRoleName(String.valueOf(ERole.ROLE_ADMIN_GROUP));
        userRoleService.save(resultUser, role.get());
        return resultUser;
    }

    private void sendCodeToUser(String mail, String code) throws MessagingException {
        mailSender.send(mail, "RESET_PASSWORD", "localhost:8080/reset?code=" + code);
    }

    private Boolean isExpiredPrivateCode(String privateCode) {
        LocalDateTime expiryDate = userRepository.findExpiryDateOfPrivateCode(privateCode);
        if(expiryDate == null) return false;
        return expiryDate.isBefore(LocalDateTime.now());
    }
}
