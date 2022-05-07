package dns.helper.telegramBot;

import dns.helper.telegramBot.keyboard.MainMenuService;
import dns.helper.telegramBot.keyboard.SubmenuService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class MarkupServiceImpl implements MarkupService {

    private final MainMenuService mainMenuService;
    private final SubmenuService submenuService;

    @Override
    public ReplyKeyboardMarkup getKeyBoard(String title) {

        for (Map.Entry<Command, ReplyKeyboardMarkup> entry : submenuService.getKeyboards().entrySet()) {
            if (entry.getKey().getType().equals(title)){
                return entry.getValue();
            }
        }

        return mainMenuService.getKeyboard();
    }
}