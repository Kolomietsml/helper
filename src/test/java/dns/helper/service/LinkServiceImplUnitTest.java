package dns.helper.service;

import dns.helper.api.dto.request.LinkRequest;
import dns.helper.db.domain.Link;
import dns.helper.db.repository.LinkRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
class LinkServiceImplUnitTest {

    @Mock
    private LinkRepository mockRepository;

    @InjectMocks
    private LinkServiceImpl service;

    @Test
    void getAll_shouldReturnsEmptyList() {
        // given
        given(mockRepository.findAll()).willReturn(new ArrayList<>());

        // when
        var actual = service.getAll();

        // then
        assertEquals(0, actual.size());
        verify(mockRepository, times(1)).findAll();
    }

    @Test
    void getAll_shouldReturnsLinksList() {
        // given
        var links = List.of(
                createTestLink(1, "https://jakdojade.pl", "Міський транспорт"),
                createTestLink(2, "https://www.znanylekarz.pl/dla-ukrainy", "Лікарі, які надають консультації українською aбо російською мовами"));
        given(mockRepository.findAll()).willReturn(links);

        // when
        var actual = service.getAll();

        // then
        assertEquals(2, actual.size());
        verify(mockRepository, times(1)).findAll();
    }

    @Test
    void getLinkById_shouldThrowsEntityNotFoundException() {
        // given

        // when
        var exception =
                assertThrows(EntityNotFoundException.class, () -> service.getLinkById(anyLong()));

        // then
        assertEquals("Link not found", exception.getMessage());
        verify(mockRepository, times(1)).findLinkById(anyLong());
    }

    @Test()
    void getLinkById_shouldReturnsLink() {
        // given
        var link = createTestLink(1, "https://www.gov.pl/web/ua/Lehalne-perebuvannya-v-Polshchi", "Сайт для громадян України");
        given(mockRepository.findLinkById(link.getId())).willReturn(link);

        // when
        var actual = service.getLinkById(link.getId());

        // then
        assertEquals(1, actual.getId());
        assertEquals("https://www.gov.pl/web/ua/Lehalne-perebuvannya-v-Polshchi", actual.getUrl());
        assertEquals("Сайт для громадян України", actual.getTitle());
        verify(mockRepository, times(1)).findLinkById(link.getId());
    }

    @Test
    void addLink_shouldReturnsLink() {
        // given
        var link = createTestLink(1, "https://jakdojade.pl", "Міський транспорт");
        var linkRequest = createTestLinkRequest("https://jakdojade.pl", "Міський транспорт");
        given(mockRepository.save(any(Link.class))).willReturn(link);

        // when
       var actual = service.addLink(linkRequest);

        // then
        assertEquals(1, actual.getId());
        assertEquals("https://jakdojade.pl", actual.getUrl());
        assertEquals("Міський транспорт", actual.getTitle());
        verify(mockRepository, times(1)).save(any(Link.class));
    }

    @Test
    void updateLink_shouldReturnsLink() {
        // given
        var link = createTestLink(1, "https://www.znanylekarz.pl/dla-ukrainy", "Лікарі, які надають консультації українською aбо російською мовами");
        var linkRequest = createTestLinkRequest("https://www.znanylekarz.pl/dla-ukrainy", "Лікарі, які надають консультації українською aбо російською мовами");
        given((mockRepository.findLinkById(link.getId()))).willReturn(link);
        given(mockRepository.save(any(Link.class))).willReturn(link);

        // when
        var actual = service.updateLinkById(linkRequest, link.getId());

        // then
        assertEquals(1, actual.getId());
        assertEquals("https://www.znanylekarz.pl/dla-ukrainy", actual.getUrl());
        assertEquals("Лікарі, які надають консультації українською aбо російською мовами", actual.getTitle());
        verify(mockRepository, times(1)).findLinkById(link.getId());
        verify(mockRepository, times(1)).save(any(Link.class));
    }

    @Test
    void updateLink_shouldThrowsEntityNotFoundException() {
        // given
        var linkRequest = createTestLinkRequest("https://www.znanylekarz.pl/dla-ukrainy", "Лікарі, які надають консультації українською aбо російською мовами");

        // when
        var exception =
                assertThrows(EntityNotFoundException.class, () -> service.updateLinkById(linkRequest, 2));

        // then
        assertEquals("Link not found", exception.getMessage());
        verify(mockRepository, times(1)).findLinkById(2);
        verify(mockRepository, times(0)).save(any(Link.class));
    }

    @Test
    void deleteLink() {
        // given
        var link = createTestLink(1, "https://www.znanylekarz.pl/dla-ukrainy", "Лікарі, які надають консультації українською aбо російською мовами");
        given(mockRepository.findLinkById(link.getId())).willReturn(link);

        // when
        service.deleteLinkById(link.getId());

        // then
        verify(mockRepository, times(1)).findLinkById(link.getId());
        verify(mockRepository, times(1)).delete(link);
    }

    @Test
    void deleteLink_shouldThrowsEntityNotFoundException() {
        // given

        // when
        var exception =
                assertThrows(EntityNotFoundException.class, () -> service.deleteLinkById(1));

        // then
        assertEquals("Link not found", exception.getMessage());
        verify(mockRepository, times(1)).findLinkById(1);
        verify(mockRepository, times(0)).delete(any(Link.class));
    }

    private Link createTestLink(long id, String url, String title) {
        var link = new Link();
        link.setId(id);
        link.setUrl(url);
        link.setTitle(title);
        return link;
    }

    private LinkRequest createTestLinkRequest(String url, String title) {
        var linkRequest = new LinkRequest();
        linkRequest.setUrl(url);
        linkRequest.setTitle(title);
        return linkRequest;
    }
}