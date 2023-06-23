package authentication.controller;

import authentication.pojo.User;
import authentication.service.AuthService;
import authentication.utils.ConstantData;
import authentication.utils.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotBlank;

@RestController
@Validated
public class AuthController {
    @Autowired
    AuthService authService;

    @PostMapping("/auth/user/register")
    public ResponseResult<?> userRegister(@RequestParam("username") @NotBlank String username,
                                          @RequestParam("password") @NotBlank String password) {
        if (authService.userRegister(username, password)) {
            return new ResponseResult<>(ConstantData.CODE_NORMAL, "新用户注册成功");
        } else {
            return new ResponseResult<>(ConstantData.CODE_OPERATION_FAILED, "新用户注册失败");
        }
    }

    @PostMapping("/auth/user/login")
    public ResponseResult<?> userLogin(@RequestParam("username") @NotBlank String username,
                                       @RequestParam("password") @NotBlank String password) {
        String token = authService.userLogin(username, password);
        if (!token.isEmpty()) {
            return new ResponseResult<>(ConstantData.CODE_NORMAL, "登录成功", token);
        } else {
            return new ResponseResult<>(ConstantData.CODE_OPERATION_FAILED, "登录失败");
        }
    }

    @PostMapping("/auth/admin/register")
    public ResponseResult<?> adminRegister(@RequestParam("username") @NotBlank String username,
                                           @RequestParam("password") @NotBlank String password) {
        if (authService.adminRegister(username, password)) {
            return new ResponseResult<>(ConstantData.CODE_NORMAL, "新管理员注册成功");
        } else {
            return new ResponseResult<>(ConstantData.CODE_OPERATION_FAILED, "新管理员注册失败");
        }
    }

    @PostMapping("/auth/admin/login")
    public ResponseResult<?> adminLogin(@RequestParam("username") @NotBlank String username,
                                        @RequestParam("password") @NotBlank String password) {
        String token = authService.adminLogin(username, password);
        if (!token.isEmpty()) {
            return new ResponseResult<>(ConstantData.CODE_NORMAL, "登录成功", token);
        } else {
            return new ResponseResult<>(ConstantData.CODE_OPERATION_FAILED, "登录失败");
        }
    }

    @PostMapping("/auth")
    public ResponseResult<?> auth(@RequestParam("username") @NotBlank String username,
                                  @RequestParam("token") @NotBlank String token,
                                  @RequestParam("type") @NotBlank String type) {
        if (authService.auth(username, token, type)) {
            return new ResponseResult<>(ConstantData.CODE_NORMAL, "用户认证成功", true);
        } else {
            return new ResponseResult<>(ConstantData.CODE_OPERATION_FAILED, "用户未认证成功", false);
        }
    }

    @PostMapping("/auth/user/info")
    public ResponseResult<User> getUserInfo(@RequestParam("operator") @NotBlank String operator,
                                            @RequestParam("token") @NotBlank String token,
                                            @RequestParam("type") @NotBlank String type,
                                            @RequestParam("username")@NotBlank String username) {
        if (auth(operator, token, type).getData().equals(true)) {
            if (type.equals("user") && !operator.equals(username)) {
                return new ResponseResult<>(ConstantData.CODE_OPERATION_FAILED, "无权限查询其他用户信息");
            }
            User user = authService.getUserInfo(username);
            if(user.getUsername() == null){
                return new ResponseResult<>(ConstantData.CODE_OPERATION_FAILED,"该用户不存在");
            }
            return new ResponseResult<>(ConstantData.CODE_NORMAL, "获取用户信息成功", user);
        } else {
            return new ResponseResult<>(ConstantData.CODE_OPERATION_FAILED, "用户未通过认证");
        }
    }
}
