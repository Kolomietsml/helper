package dns.helper.telegramBot.keyboard;

import dns.helper.telegramBot.Command;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;

import java.util.List;

@Service
public class MainMenuServiceImpl implements MainMenuService, KeyboardService {

    @Override
    public ReplyKeyboardMarkup getKeyboard() {
        var keyboardRows = List.of(
                createRow(Command.MEDICINE.getType()),
                createRow(Command.GUARDIANSHIP.getType())
        );

        return ReplyKeyboardMarkup.builder()
                .keyboard(keyboardRows)
                .resizeKeyboard(true)
                .oneTimeKeyboard(true)
                .build();
    }
}