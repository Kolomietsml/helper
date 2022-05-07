package dns.helper.telegramBot;

import lombok.Getter;

@Getter
public enum Command {

    START("/start"),
    GREETINGS("Привіт"),
    MAIN("Головне меню"),
    MEDICINE("Опіка медична"),
    GUARDIANSHIP("Опіка правова"),
    LINKS("Корисна інформація"),
    EMERGENCIES("Номери телефонів екстреної допомоги");

    private final String type;

    Command(String type) {
        this.type = type;
    }
}