package ru.topacademy.test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import java.time.Duration;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

import static ru.topacademy.data.DataGenerator.Registration.getRegisteredUser;
import static ru.topacademy.data.DataGenerator.Registration.getUser;
import static ru.topacademy.data.DataGenerator.getRandomLogin;
import static ru.topacademy.data.DataGenerator.getRandomPassword;

public class InetBankTest {

    @BeforeEach
    void setup() {
        open("http://localhost:9999");
    }

    @Test
    void shouldSuccessLoginIfRegisteredActiveUser() {
        var registeredUser = getRegisteredUser("active");
        $("[data-test-id=login] input").setValue(registeredUser.getLogin());
        $("[data-test-id=password] input").setValue(registeredUser.getPassword());
        $("[data-test-id=action-login]").click();
        $("h2").shouldHave(exactText("Личный кабинет"));
    }

    @Test
    void shouldErrorIfNotRegisteredUser() {
        var notRegisteredUser = getUser("active");
        $("[data-test-id=login] input").setValue(notRegisteredUser.getLogin());
        $("[data-test-id=password] input").setValue(notRegisteredUser.getPassword());
        $("[data-test-id=action-login]").click();
        $("[data-test-id=error-notification] .notification__title").shouldHave(exactText("Ошибка"), Duration.ofSeconds(10));
        $("[data-test-id=error-notification] .notification__content").shouldHave(exactText("Ошибка! Неверно указан логин или пароль"), Duration.ofSeconds(10));
    }

    @Test
    void shouldErrorIfBlockedUser() {
        var blockedUser = getUser("blocked");
        $("[data-test-id=login] input").setValue(blockedUser.getLogin());
        $("[data-test-id=password] input").setValue(blockedUser.getPassword());
        $("[data-test-id=action-login]").click();
        $("[data-test-id=error-notification] .notification__title").shouldHave(exactText("Ошибка"), Duration.ofSeconds(10));
        $("[data-test-id=error-notification] .notification__content").shouldHave(exactText("Ошибка! Неверно указан логин или пароль"), Duration.ofSeconds(10));
    }

}
