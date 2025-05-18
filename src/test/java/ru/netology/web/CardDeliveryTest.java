package ru.netology.web;

import com.codeborne.selenide.Condition;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Selenide.*;

public class CardDeliveryTest {

    @Test
    void shouldSubmitForm() {
        String planningDate = LocalDate.now().plusDays(3)
                .format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));

        open("http://localhost:9999");

        $("[data-test-id=city] input").setValue("Казань");
        $("[data-test-id=date] input")
                .sendKeys(Keys.chord(Keys.CONTROL, "a"), Keys.BACK_SPACE);
        $("[data-test-id=date] input").setValue(planningDate);
        $("[data-test-id=name] input").setValue("Иванов Иван");
        $("[data-test-id=phone] input").setValue("+79270000000");
        $("[data-test-id=agreement]").click();
        $$(".button").find(Condition.exactText("Забронировать")).click();

        $("[data-test-id=notification]")
                .shouldBe(Condition.visible, Duration.ofSeconds(15))
                .shouldHave(Condition.text("Успешно! Встреча успешно забронирована на " + planningDate));
    }
}
