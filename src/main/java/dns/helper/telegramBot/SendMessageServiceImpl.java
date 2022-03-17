package dns.helper.telegramBot;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

@Service
@RequiredArgsConstructor
public class SendMessageServiceImpl implements SendMessageService {

    private final ResponseService responseService;
    private final MarkupService markupService;

    @Override
    public SendMessage send(Message message) {

        var keyboard = markupService.getKeyBoard();

        return SendMessage.builder()
                .text(responseService.getResponse(message.getText()))
                .parseMode("HTML")
                .chatId(String.valueOf(message.getChatId()))
                .replyMarkup(keyboard)
                .disableWebPagePreview(true)
                .build();
    }
}