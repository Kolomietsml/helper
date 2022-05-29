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
    public String getResponse(String command) {
        if (Command.START.getType().equals(command)) {
            return "Привіт, я твій Бот-Помічник:)";
        }  else if (Command.MAIN.getType().equals(command)) {
            return "Виберіть необхідний розділ";
        }  else if (Command.INFO.getType().equals(command)) {
            return "Виберіть необхідний підрозділ";
        }  else if (Command.LINKS.getType().equals(command)) {
            return linkService.getAllByCommand(Command.LINKS).stream().map(this::getLinkInfo).collect(Collectors.joining());
        } else if (Command.EMERGENCIES.getType().equals(command)) {
            return emergencyService.getAll().stream().map(this::getEmergencyInfo).collect(Collectors.joining());
        }  else if (Command.MEDICAL_CARE.getType().equals(command)) {
            return linkService.getAllByCommand(Command.MEDICAL_CARE).stream().map(this::getLinkInfo).collect(Collectors.joining());
        }  else if (Command.TRANSPORT.getType().equals(command)) {
            return linkService.getAllByCommand(Command.TRANSPORT).stream().map(this::getLinkInfo).collect(Collectors.joining());
        }else {
            return "Команда не знайдена";
        }
    }

    private String getLinkInfo(Link link) {
        return String.format("<a href='%s'>%s</a>\n%s\n\n", link.getUrl(), link.getTitle(), link.getDescription());
    }

    private String getEmergencyInfo(Emergency emergency) {
        return String.format("%s - %s\n\n", emergency.getPhone(), emergency.getTitle());
    }
}