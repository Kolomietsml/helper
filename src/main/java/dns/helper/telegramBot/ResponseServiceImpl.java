package dns.helper.telegramBot;

import dns.helper.db.domain.Emergency;
import dns.helper.db.domain.Link;
import dns.helper.service.EmergencyService;
import dns.helper.service.LinkService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ResponseServiceImpl implements ResponseService {

    private final LinkService linkService;
    private final EmergencyService emergencyService;

    @Override
    public String getResponse(String text) {
        if (Command.START.getType().equals(text)) {
            return "Привіт, я твій Бот-Помічник:)";
        }  else if (Command.MAIN.getType().equals(text)) {
            return "Виберіть необхідний розділ";
        }  else if (Command.MEDICINE.getType().equals(text)) {
            return "Виберіть необхідний підрозділ";
        }  else if (Command.GUARDIANSHIP.getType().equals(text)) {
            return "Виберіть необхідний підрозділ";
        }  else if (Command.LINKS.getType().equals(text)) {
            return linkService.getAll().stream().map(this::getLinkInfo).collect(Collectors.joining());
        } else if (Command.EMERGENCIES.getType().equals(text)) {
            return emergencyService.getAll().stream().map(this::getEmergencyInfo).collect(Collectors.joining());
        } else {
            return "Команда не знайдена";
        }
    }

    private String getLinkInfo(Link link) {
        return String.format("<a href='%s'>%s</a>\n\n", link.getUrl(), link.getTitle());
    }

    private String getEmergencyInfo(Emergency emergency) {
        return String.format("%s - %s\n\n", emergency.getPhone(), emergency.getTitle());
    }
}