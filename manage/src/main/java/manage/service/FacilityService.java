package manage.service;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import manage.mapper.FacilityMapper;
import manage.pojo.Facility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class FacilityService {
    @Autowired
    FacilityMapper facilityMapper;

    public boolean insertFacility(Facility facility) {
        return facilityMapper.insert(facility) > 0;
    }
    public List<Facility> getFacilities(){
        return facilityMapper.selectList(Wrappers.lambdaQuery(Facility.class));
    }
}
