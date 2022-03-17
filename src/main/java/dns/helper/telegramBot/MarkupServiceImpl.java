package dns.helper.telegramBot;

import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.List;

@Service
public class MarkupServiceImpl implements MarkupService {

    @Override
    public ReplyKeyboardMarkup getKeyBoard() {
        var keyboardRows = List.of(
                createRow("Корисна інформація"),
                createRow("Номери телефонів екстреної допомоги"));

        return ReplyKeyboardMarkup.builder()
                .keyboard(keyboardRows)
                .resizeKeyboard(true)
                .oneTimeKeyboard(true)
                .build();
    }

    private KeyboardRow createRow(String title) {
        var row = new KeyboardRow();
        row.add(title);
        return row;
    }
}