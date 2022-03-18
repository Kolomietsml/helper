package dns.helper.telegramBot;

import lombok.Getter;

@Getter
public enum Command {

    GREETINGS("Привіт"),
    LINKS("Корисна інформація"),
    EMERGENCIES("Номери телефонів екстреної допомоги"),
    NOTFOUND("Команда не знайдена");

    private final String type;

    Command(String type) {
        this.type = type;
    }
}