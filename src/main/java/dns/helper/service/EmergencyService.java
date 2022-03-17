package dns.helper.service;

import dns.helper.db.domain.Emergency;
import dns.helper.api.dto.request.EmergencyRequest;

import java.util.List;

public interface EmergencyService {

    List<Emergency> getAll();
    Emergency getEmergencyById(long id);
    Emergency addEmergency(EmergencyRequest request);
    Emergency updateEmergencyById(EmergencyRequest request, long id);
    void deleteEmergencyById(long id);
    void deleteAll();
}