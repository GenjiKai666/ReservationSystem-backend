package reservation.client;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import reservation.config.ClientConfig;
import reservation.pojo.User;
import reservation.utils.ResponseResult;

import javax.validation.constraints.NotBlank;

@Component
@FeignClient(name = "authentication", configuration = ClientConfig.class)
public interface AuthClient {
    @PostMapping("/auth")
    ResponseResult<?> auth(@RequestParam("username") @NotBlank String username,
                           @RequestParam("token") @NotBlank String token,
                           @RequestParam("type") @NotBlank String type);
    @PostMapping("/auth/user/info")
    ResponseResult<User> getUserInfo(@RequestParam("operator") @NotBlank String operator,
                                     @RequestParam("token") @NotBlank String token,
                                     @RequestParam("type") @NotBlank String type,
                                     @RequestParam("username") String username);
}
