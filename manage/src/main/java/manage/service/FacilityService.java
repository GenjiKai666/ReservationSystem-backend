package manage.service;

import manage.mapper.FacilityMapper;
import manage.pojo.Facility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class FacilityService {
    @Autowired
    FacilityMapper facilityMapper;

    public boolean insertFacility(Facility facility) {
        return facilityMapper.insert(facility) > 0;
    }
}
