package dns.helper.service;

import dns.helper.db.domain.Link;
import dns.helper.api.dto.request.LinkRequest;

import java.util.List;

public interface LinkService {

    List<Link> getAll();
    Link getLinkById(long id);
    Link addLink(LinkRequest request);
    Link updateLinkById(LinkRequest request, long id);
    void deleteLinkById(long id);
    void deleteAll();
}