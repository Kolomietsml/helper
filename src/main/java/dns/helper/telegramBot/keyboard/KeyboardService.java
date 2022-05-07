package dns.helper.telegramBot.keyboard;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

public interface KeyboardService {

    default KeyboardRow createRow(String title) {
        var row = new KeyboardRow();
        row.add(title);
        return row;
    }
}