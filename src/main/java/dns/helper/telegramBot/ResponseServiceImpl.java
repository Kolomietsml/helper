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
        if ("/start".equals(text)) {
            return "Привіт";
        }  else if ("Корисна інформація".equals(text)) {
            return linkService.getAll().stream().map(this::getLinkInfo).collect(Collectors.joining());
        } else if ("Номери телефонів екстреної допомоги".equals(text)) {
            return emergencyService.getAll().stream().map(this::getEmergencyInfo).collect(Collectors.joining());
        } else {
            return  "Command not found";
        }
    }

    private String getLinkInfo(Link link) {
        return String.format("<a href='%s'>%s</a>\n\n", link.getUrl(), link.getTitle());
    }

    private String getEmergencyInfo(Emergency emergency) {
        return String.format("%s - <i>%s</i>\n\n", emergency.getPhone(), emergency.getTitle());
    }
}