package dns.helper.telegramBot.keyboard;

import dns.helper.telegramBot.Command;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.List;
import java.util.Map;

@Service
public class SubMenuServiceImpl implements SubmenuService, KeyboardService {

    @Override
    public Map<Command, ReplyKeyboardMarkup> getKeyboards() {
        return Map.of(
                Command.MEDICINE, getKeyboard(medicineList()),
                Command.GUARDIANSHIP, getKeyboard(guardianshipList())
        );
    }

    private ReplyKeyboardMarkup getKeyboard(List<KeyboardRow> keyboardRows) {
        return ReplyKeyboardMarkup.builder()
                .keyboard(keyboardRows)
                .resizeKeyboard(true)
                .oneTimeKeyboard(true)
                .build();
    }

    private List<KeyboardRow> medicineList() {
        return List.of(
                createRow("Ліки"),
                createRow("Безкоштовна консультаціі лікарів"),
                createRow(Command.EMERGENCIES.getType()),
                createRow(Command.MAIN.getType())
        );
    }

    private List<KeyboardRow> guardianshipList() {
        return List.of(
                createRow("Безкоштовна консультація"),
                createRow(Command.MAIN.getType())
        );
    }
}