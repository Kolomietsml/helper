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
        String text = message.getText();

        var keyboard = markupService.getKeyBoard(text);

        return SendMessage.builder()
                .text(responseService.getResponse(text))
                .parseMode("HTML")
                .chatId(String.valueOf(message.getChatId()))
                .replyMarkup(keyboard)
                .disableWebPagePreview(true)
                .build();
    }
}