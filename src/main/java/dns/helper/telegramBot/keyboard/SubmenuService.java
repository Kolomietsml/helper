package dns.helper.telegramBot.keyboard;

import dns.helper.telegramBot.Command;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;

import java.util.Map;

public interface SubmenuService {

    Map<Command, ReplyKeyboardMarkup> getKeyboards();
}