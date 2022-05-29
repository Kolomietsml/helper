package dns.helper.telegramBot;

import lombok.Getter;

@Getter
public enum Command {

    START("/start"),
    MAIN("Головне меню"),
    INFO("Корисна інформація"),
    LINKS("Корисні посилання"),
    EMERGENCIES("Номери телефонів екстреної допомоги"),
    MEDICAL_CARE("Медична допомога"),
    TRANSPORT("Транспорт");

    private final String type;

    Command(String type) {
        this.type = type;
    }
}