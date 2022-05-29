package dns.helper.telegramBot.keyboard;

import dns.helper.telegramBot.Command;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.List;
import java.util.Map;

public interface KeyboardService {

    Map<Command, ReplyKeyboardMarkup> getKeyboards();
    ReplyKeyboardMarkup getMainKeyboard();

    default ReplyKeyboardMarkup getKeyboard(List<KeyboardRow> keyboardRows) {
        return ReplyKeyboardMarkup.builder()
                .keyboard(keyboardRows)
                .resizeKeyboard(true)
                .oneTimeKeyboard(true)
                .build();
    }

    default KeyboardRow getRow(String title) {
        var row = new KeyboardRow();
        row.add(title);
        return row;
    }
}
