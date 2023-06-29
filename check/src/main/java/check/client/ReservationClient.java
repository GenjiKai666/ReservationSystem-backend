package check.client;

import check.config.ClientConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Component
@FeignClient(name = "reservation", configuration = ClientConfig.class)
public interface ReservationClient {
    @PostMapping("/check")
    void check(@RequestParam("id")Integer id);

}
