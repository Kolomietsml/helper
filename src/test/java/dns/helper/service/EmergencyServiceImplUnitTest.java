package dns.helper.service;

import dns.helper.api.dto.request.EmergencyRequest;
import dns.helper.db.domain.Emergency;
import dns.helper.db.repository.EmergencyRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class EmergencyServiceImplUnitTest {

    @Mock
    private EmergencyRepository mockRepository;

    @InjectMocks
    private EmergencyServiceImpl service;

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
    void getAll_shouldReturnsEmergencyList() {
        // given
        var emergencies = List.of(
                createTestEmergency(1, "112", "Hомер екстреної допомоги"),
                createTestEmergency(2, "997", "Поліція"),
                createTestEmergency(3, "998", "Пожежна частина"),
                createTestEmergency(4, "999", "Служба швидкої допомоги"),
                createTestEmergency(5, "986", "Муніципальна поліція"));
        given(mockRepository.findAll()).willReturn(emergencies);

        // when
        var actual = service.getAll();

        // then
        assertEquals(5, actual.size());
        verify(mockRepository, times(1)).findAll();
    }

    @Test
    void getEmergencyById_shouldThrowsEntityNotFoundException() {
        // given

        // when
        var exception =
                assertThrows(EntityNotFoundException.class, () -> service.getEmergencyById(anyLong()));

        // then
        assertEquals("Emergency not found", exception.getMessage());
        verify(mockRepository, times(1)).findEmergencyById(anyLong());
    }

    @Test()
    void getEmergencyById_shouldReturnsEmergency() {
        // given
        var emergency = createTestEmergency(1, "112", "Hомер екстреної допомоги");
        given(mockRepository.findEmergencyById(emergency.getId())).willReturn(emergency);

        // when
        var actual = service.getEmergencyById(emergency.getId());

        // then
        assertEquals(1, actual.getId());
        assertEquals("112", actual.getPhone());
        assertEquals("Hомер екстреної допомоги", actual.getTitle());
        verify(mockRepository, times(1)).findEmergencyById(emergency.getId());
    }

    @Test
    void addEmergency_shouldReturnsEmergency() {
        // given
        var emergency = createTestEmergency(1, "112", "Hомер екстреної допомоги");
        var emergencyRequest = createTestEmergencyRequest("112", "Hомер екстреної допомоги");
        given(mockRepository.save(any(Emergency.class))).willReturn(emergency);

        // when
        var actual = service.addEmergency(emergencyRequest);

        // then
        assertEquals(1, actual.getId());
        assertEquals("112", actual.getPhone());
        assertEquals("Hомер екстреної допомоги", actual.getTitle());
        verify(mockRepository, times(1)).save(any(Emergency.class));
    }

    @Test
    void updateEmergency_shouldReturnsEmergency() {
        // given
        var emergency = createTestEmergency(1, "999", "Служба швидкої допомоги");
        var emergencyRequest = createTestEmergencyRequest("999", "Служба швидкої допомоги");
        given((mockRepository.findEmergencyById(emergency.getId()))).willReturn(emergency);
        given(mockRepository.save(any(Emergency.class))).willReturn(emergency);

        // when
        var actual = service.updateEmergencyById(emergencyRequest, emergency.getId());

        // then
        assertEquals(1, actual.getId());
        assertEquals("999", actual.getPhone());
        assertEquals("Служба швидкої допомоги", actual.getTitle());
        verify(mockRepository, times(1)).findEmergencyById(emergency.getId());
        verify(mockRepository, times(1)).save(any(Emergency.class));
    }

    @Test
    void updateEmergency_shouldThrowsEntityNotFoundException() {
        // given
        var emergencyRequest = createTestEmergencyRequest("999", "Служба швидкої допомоги");

        // when
        var exception =
                assertThrows(EntityNotFoundException.class, () -> service.updateEmergencyById(emergencyRequest, 2));

        // then
        assertEquals("Emergency not found", exception.getMessage());
        verify(mockRepository, times(1)).findEmergencyById(2);
        verify(mockRepository, times(0)).save(any(Emergency.class));
    }

    @Test
    void deleteEmergency() {
        // given
        var emergency = createTestEmergency(1, "999", "Служба швидкої допомоги");
        given(mockRepository.findEmergencyById(emergency.getId())).willReturn(emergency);

        // when
        service.deleteEmergencyById(emergency.getId());

        // then
        verify(mockRepository, times(1)).findEmergencyById(emergency.getId());
        verify(mockRepository, times(1)).delete(emergency);
    }

    @Test
    void deleteEmergency_shouldThrowsEntityNotFoundException() {
        // given

        // when
        var exception =
                assertThrows(EntityNotFoundException.class, () -> service.deleteEmergencyById(1));

        // then
        assertEquals("Emergency not found", exception.getMessage());
        verify(mockRepository, times(1)).findEmergencyById(1);
        verify(mockRepository, times(0)).delete(any(Emergency.class));
    }

    private Emergency createTestEmergency(long id, String phone, String title) {
        var emergency = new Emergency();
        emergency.setId(id);
        emergency.setPhone(phone);
        emergency.setTitle(title);
        return emergency;
    }

    private EmergencyRequest createTestEmergencyRequest(String phone, String title) {
        var emergencyRequest = new EmergencyRequest();
        emergencyRequest.setPhone(phone);
        emergencyRequest.setTitle(title);
        return emergencyRequest;
    }
}