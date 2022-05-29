package dns.helper.service;

import dns.helper.db.domain.Link;
import dns.helper.api.dto.request.LinkRequest;
import dns.helper.telegramBot.Command;

import java.util.List;

public interface LinkService {

    List<Link> getAll();
    List<Link> getAllByCommand(Command command);
    Link getLinkById(long id);
    Link addLink(LinkRequest request);
    Link updateLinkById(LinkRequest request, long id);
    void deleteLinkById(long id);
    void deleteAll();
}