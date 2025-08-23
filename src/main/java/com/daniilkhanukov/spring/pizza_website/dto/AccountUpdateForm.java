package com.daniilkhanukov.spring.pizza_website.dto;

import jakarta.validation.constraints.Pattern;

public class AccountUpdateForm {

    @Pattern(
            regexp = "\\+7\\(\\d{3}\\)-\\d{3}-\\d{2}-\\d{2}",
            message = "Телефон должен быть в формате +7(ххх)-ххх-хх-хх"
    )
    private String phone;

    @Pattern(
            regexp = "^Город Москва, улица [А-Яа-яЁё\\s]+, дом \\d+[А-Яа-я]?, кв\\. \\d+, код домофона .{1,9}$",
            message = "Адрес должен быть в формате: Город Москва, улица ..., дом ..., кв. ..., код домофона ..."
    )
    private String userAddress;

    public String getPhone() {
        return phone;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }
    public String getUserAddress() {
        return userAddress;
    }
    public void setUserAddress(String userAddress) {
        this.userAddress = userAddress;
    }
}
