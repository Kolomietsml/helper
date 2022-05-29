package dns.helper.telegramBot.keyboard;

import dns.helper.telegramBot.Command;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.List;
import java.util.Map;

@Service
public class KeyboardServiceImpl implements KeyboardService {

    private final Map<Command, ReplyKeyboardMarkup> keyboards;
    private final ReplyKeyboardMarkup mainKeyboard;

    public KeyboardServiceImpl() {
        this.keyboards = Map.of(
                Command.INFO, getKeyboard(informationList())
        );
        this.mainKeyboard = getKeyboard(
                List.of(
                        getRow(Command.INFO.getType()),
                        getRow(Command.MEDICAL_CARE.getType()),
                        getRow(Command.TRANSPORT.getType()))
        );
    }

    @Override
    public Map<Command, ReplyKeyboardMarkup> getKeyboards() {
        return keyboards;
    }

    @Override
    public ReplyKeyboardMarkup getMainKeyboard() {
        return mainKeyboard;
    }

    private List<KeyboardRow> informationList() {
        return List.of(
                getRow(Command.LINKS.getType()),
                getRow(Command.EMERGENCIES.getType()),
                getRow(Command.MAIN.getType())
        );
    }
}
