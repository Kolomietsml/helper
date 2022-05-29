package dns.helper.service;

import dns.helper.api.dto.request.LinkRequest;
import dns.helper.db.domain.Link;
import dns.helper.db.repository.LinkRepository;
import dns.helper.telegramBot.Command;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class LinkServiceImpl implements LinkService {

    private final LinkRepository repository;

    @Override
    public List<Link> getAll() {
        return repository.findAllLinks();
    }

    @Override
    public List<Link> getAllByCommand(Command command) {
        return repository.findAllLinksByCommand(command);
    }

    @Override
    public Link getLinkById(long id) {
        var link = repository.findLinkById(id);
        if (link == null) {
            throw new EntityNotFoundException("Link not found");
        }
        return link;
    }

    @Override
    public Link addLink(LinkRequest request) {
        var link = new Link();
        link.setUrl(request.getUrl());
        link.setTitle(request.getTitle());
        link.setDescription(request.getDescription());
        link.setCommand(Command.valueOf(request.getCommand()));
        return repository.save(link);
    }

    @Override
    public Link updateLinkById(LinkRequest request, long id) {
        var link = getLinkById(id);
        link.setUrl(request.getUrl());
        link.setTitle(request.getTitle());
        link.setDescription(request.getDescription());
        link.setCommand(Command.valueOf(request.getCommand()));
        return repository.save(link);
    }

    @Override
    public void deleteLinkById(long id) {
        var link = getLinkById(id);
        repository.delete(link);
    }

    @Override
    public void deleteAll() {
        repository.deleteAll();
    }
}