package dns.helper.service;

import dns.helper.db.domain.Emergency;
import dns.helper.api.dto.request.EmergencyRequest;
import dns.helper.db.repository.EmergencyRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmergencyServiceImpl implements EmergencyService {

    private final EmergencyRepository repository;

    @Override
    public List<Emergency> getAll() {
        return repository.findAllEmergencies();
    }

    @Override
    public Emergency getEmergencyById(long id) {
        var emergency = repository.findEmergencyById(id);
        if (emergency == null) {
            throw new EntityNotFoundException("Emergency not found");
        }
        log.info("Get emergency by id {}", id);
        return emergency;
    }

    @Override
    public Emergency addEmergency(EmergencyRequest request) {
        var emergency = new Emergency();
        emergency.setPhone(request.getPhone());
        emergency.setTitle(request.getTitle());
        emergency = repository.save(emergency);
        log.info("Add emergency: {}", emergency);
        return emergency;
    }

    @Override
    public Emergency updateEmergencyById(EmergencyRequest request, long id) {
        var emergency = getEmergencyById(id);
        emergency.setPhone(request.getPhone());
        emergency.setTitle(request.getTitle());
        emergency = repository.save(emergency);
        log.info("Update emergency by id {}", id);
        return emergency;
    }

    @Override
    public void deleteEmergencyById(long id) {
        var emergency = getEmergencyById(id);
        repository.delete(emergency);
        log.info("Delete emergency with id {}", id);
    }

    @Override
    public void deleteAll() {
        repository.deleteAll();
        log.info("Delete all emergency");
    }
}