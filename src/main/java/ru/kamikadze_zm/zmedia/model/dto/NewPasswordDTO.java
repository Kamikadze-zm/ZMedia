package ru.kamikadze_zm.zmedia.model.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import ru.kamikadze_zm.zmedia.validators.Match;

@Match(field = "password", confirmationField = "confirmPassword", message = "Пароли не совпадают")
public class NewPasswordDTO {

    @NotNull(message = "Введите пароль")
    @Size(min = 6, max = 20, message = "Пароль должен содержать от {min} до {max} символов")
    private String password;
    @NotNull(message = "Подтвердите пароль")
    private String confirmPassword;

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
}
