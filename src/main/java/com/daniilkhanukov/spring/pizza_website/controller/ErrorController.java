package com.daniilkhanukov.spring.pizza_website.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.ui.Model;
import jakarta.servlet.http.HttpServletRequest;

@Controller
public class ErrorController implements org.springframework.boot.web.servlet.error.ErrorController {

    @RequestMapping("/error")
    public String handleError(HttpServletRequest request, Model model) {
        // Получаем код ошибки
        Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
        // Получаем сообщение об ошибке
        String errorMessage = (String) request.getAttribute("javax.servlet.error.message");

        // Добавляем минимальную информацию в модель
        model.addAttribute("statusCode", statusCode != null ? statusCode : "Неизвестно");
        model.addAttribute("errorMessage", errorMessage != null && !errorMessage.isEmpty() ? errorMessage : "Что-то пошло не так");

        return "error";
    }
}
