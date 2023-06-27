package manage.service;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import manage.mapper.FacilityMapper;
import manage.pojo.Facility;
import manage.pojo.FacilityVue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class FacilityService {
    @Autowired
    FacilityMapper facilityMapper;

    public boolean insertFacility(Facility facility) {
        return facilityMapper.insert(facility) > 0;
    }

    public List<Facility> getFacilities() {
        return facilityMapper.selectList(Wrappers.lambdaQuery(Facility.class));
    }

    public Facility getFacility(Integer facilityId) {
        return facilityMapper.selectById(facilityId);
    }

    public List<FacilityVue> getFacilitiesVue() {
        List<FacilityVue> facilityVues = getFacilities().stream()
                .map(FacilityVue::new)
                .collect(Collectors.toList());
        return facilityVues;
    }

    public boolean deleteFacility(Integer facilityId) {
        return facilityMapper.deleteById(facilityId) > 0;
    }

    public boolean updateFacility(Facility facility) {
        return facilityMapper.updateById(facility) > 0;
    }
}
