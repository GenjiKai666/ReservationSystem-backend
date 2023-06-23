package reservation.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import reservation.config.ClientConfig;
import reservation.pojo.Facility;
import reservation.utils.ResponseResult;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Component
@FeignClient(name = "manage", configuration = ClientConfig.class)
public interface FacilityClient {
    @PostMapping("/facility")
    ResponseResult<Facility> getFacility(@RequestParam("operator") @NotBlank String operator,
                                         @RequestParam("token") @NotBlank String token,
                                         @RequestParam("type") @NotBlank String type,
                                         @RequestParam("facility_id") @NotNull Integer facilityId);
}
