package ru.kamikadze_zm.zmedia.controller;

import java.util.ArrayList;
import java.util.List;
import javax.validation.Valid;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.kamikadze_zm.zmedia.exception.ValidationFieldsException;
import ru.kamikadze_zm.zmedia.model.ApiError;
import ru.kamikadze_zm.zmedia.model.ApiError.ErrorCode;
import ru.kamikadze_zm.zmedia.model.dto.NewPasswordDTO;
import ru.kamikadze_zm.zmedia.model.dto.RegistrationDTO;
import ru.kamikadze_zm.zmedia.service.AccountService;
import ru.kamikadze_zm.zmedia.util.AuthenticationUtil;

@RestController
@RequestMapping("/api/account/")
public class AccountController {

    private static final String EMAIL_PARAM = "email";
    private static final String NAME_PARAM = "name";

    @Autowired
    private AccountService accountService;

    @PreAuthorize("!isAuthenticated()")
    @PostMapping(path = "registration")
    public ResponseEntity register(@Valid @RequestBody RegistrationDTO registration) {
        boolean emailExist = exist(EMAIL_PARAM, registration.getEmail());
        boolean nameExist = exist(NAME_PARAM, registration.getName());
        if (emailExist || nameExist) {
            List<FieldError> fieldErrors = new ArrayList<>(2);
            if (emailExist) {
                fieldErrors.add(ValidationFieldsException.createError("registration", "email", registration.getEmail(), "Email уже зарегистрирован"));
            }
            if (nameExist) {
                fieldErrors.add(ValidationFieldsException.createError("registration", "email", registration.getName(), "Имя пользователя уже занято"));
            }
            throw new ValidationFieldsException(fieldErrors);
        }
        accountService.registerUser(registration);
        return new ResponseEntity(HttpStatus.CREATED);
    }

    @PreAuthorize("permitAll")
    @PostMapping(path = "refresh-token", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public String refreshAccessToken(@RequestBody String refreshToken) {
        return "\"" + accountService.refreshAccessToken(refreshToken) + "\"";
    }

    @PreAuthorize("permitAll")
    @GetMapping(path = "exist/{param}")
    public boolean exist(@PathVariable("param") String param, @RequestParam String value) {
        if (param == null || param.isEmpty() || value == null || value.isEmpty()) {
            return false;
        }
        switch (param.toLowerCase()) {
            case EMAIL_PARAM:
                return accountService.existEmail(value);
            case NAME_PARAM:
                return accountService.existName(value);
            default:
                return false;
        }
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping(path = "confirmation-message")
    public ResponseEntity sendConfirmationMessage() {
        accountService.sendEmailConfirmationMessage(AuthenticationUtil.getAuthenticatedUser());
        return new ResponseEntity(HttpStatus.OK);
    }

    @PreAuthorize("permitAll")
    @GetMapping(path = "email-confirm/{code}")
    public ResponseEntity confirmEmail(@PathVariable("code") String code) {
        accountService.confirmEmail(code);
        return new ResponseEntity(HttpStatus.OK);
    }

    @PreAuthorize("permitAll")
    @GetMapping(path = "password-restoring-message/{email}/")
    public ResponseEntity<Object> sendPasswordRestoringMessage(@PathVariable("email") String email) {
        if (accountService.sendPasswordRestoringMessage(email)) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(new ApiError(ErrorCode.MESSAGE, "Пользователь не найден или email не подтвержден"), HttpStatus.BAD_REQUEST);
        }
    }

    @PreAuthorize("permitAll")
    @PostMapping(path = "password-restoring/{code}")
    public ResponseEntity restorePassword(@PathVariable("code") String code, @Valid @RequestBody NewPasswordDTO password) {
        accountService.restorePassword(code, password);
        return new ResponseEntity(HttpStatus.OK);
    }
}
