package dns.helper.telegramBot;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

public interface SendMessageService {

    SendMessage send(Message message);
}