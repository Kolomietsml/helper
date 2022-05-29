package dns.helper.telegramBot;

import dns.helper.telegramBot.keyboard.KeyboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class MarkupServiceImpl implements MarkupService {

    private final KeyboardService service;

    @Override
    public ReplyKeyboardMarkup getKeyBoard(String title) {

        for (Map.Entry<Command, ReplyKeyboardMarkup> entry : service.getKeyboards().entrySet()) {
            if (entry.getKey().getType().equals(title)){
                return entry.getValue();
            }
        }

        return service.getMainKeyboard();
    }
}