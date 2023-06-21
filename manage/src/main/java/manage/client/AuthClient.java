package manage.client;

import manage.config.AuthClientConfig;
import manage.utils.ResponseResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.constraints.NotBlank;

@Component
@FeignClient(name = "authentication",configuration= AuthClientConfig.class)
public interface AuthClient {
    @PostMapping("/auth")
    ResponseResult<?> auth(@RequestParam("username") @NotBlank String username,
                           @RequestParam("token") @NotBlank String token,
                           @RequestParam("type") @NotBlank String type);
}
