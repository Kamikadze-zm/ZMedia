package ru.kamikadze_zm.zmedia.model.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import ru.kamikadze_zm.zmedia.validators.Match;

@Match(field = "password", confirmationField = "confirmPassword", message = "Пароли не совпадают")
public class RegistrationDTO {

    @NotBlank(message = "Email не должен быть пустым")
    @Email(message = "Введите корректный email")
    private String email;
    @Size(min = 3, max = 64, message = "Имя пользователя должно содержать от {min} до {max} символов")
    @Pattern(regexp = "^[a-zA-Z][a-zA-Z0-9_\\-.]+$",
            message = "Имя пользователя должно состоять из латинских букв, цифр, символов '_', '-', '.' и начинаться с буквы")
    private String name;
    @NotNull(message = "Введите пароль")
    @Size(min = 6, max = 20, message = "Пароль должен содержать от {min} до {max} символов")
    private String password;
    @NotNull(message = "Подтвердите пароль")
    private String confirmPassword;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    @Override
    public String toString() {
        return "RegistrationDTO{" + "email=" + email + ", name=" + name + '}';
    }
}
