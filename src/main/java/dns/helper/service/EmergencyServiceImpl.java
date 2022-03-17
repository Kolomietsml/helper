package dns.helper.service;

import dns.helper.db.domain.Emergency;
import dns.helper.api.dto.request.EmergencyRequest;
import dns.helper.db.repository.EmergencyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EmergencyServiceImpl implements EmergencyService {

    private final EmergencyRepository repository;

    @Override
    public List<Emergency> getAll() {
        return repository.findAll();
    }

    @Override
    public Emergency getEmergencyById(long id) {
        var emergency = repository.findEmergencyById(id);
        if (emergency == null) {
            throw new EntityNotFoundException("Emergency not found");
        }
        return emergency;
    }

    @Override
    public Emergency addEmergency(EmergencyRequest request) {
        var emergency = new Emergency();
        emergency.setPhone(request.getPhone());
        emergency.setTitle(request.getTitle());
        return repository.save(emergency);
    }

    @Override
    public Emergency updateEmergencyById(EmergencyRequest request, long id) {
        var emergency = getEmergencyById(id);
        emergency.setPhone(request.getPhone());
        emergency.setTitle(request.getTitle());
        return repository.save(emergency);
    }

    @Override
    public void deleteEmergencyById(long id) {
        var emergency = getEmergencyById(id);
        repository.delete(emergency);
    }

    @Override
    public void deleteAll() {
        repository.deleteAll();
    }
}