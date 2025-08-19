package com.market_p.market_p.dto;

import com.market_p.market_p.example.constants.Messages;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterDto {
    @NotBlank(message = Messages.User.NAME_CANT_BE_EMPTY)
    @Pattern(regexp = "^[A-Z]{1,60}$", message = Messages.User.NAME_MUST_OBEY_REGEX_RULES)
    private String firstName;
    @NotBlank(message = Messages.User.SURNAME_CANT_BE_EMPTY)
    @Pattern(regexp = "^[A-Z]{1,60}$",message = Messages.User.SURNAME_MUST_OBEY_REGEX_RULES)
    private String surname;
    @NotBlank(message =  Messages.User.EMAIL_CANT_BE_EMPTY)
    @Pattern(regexp = "^[A-Z0-9._]+@[A-Z0-9.-]+.[A-Z]{2,6}$",message = Messages.User.EMAIL_MUST_OBEY_REGEX_RULES)
    private String email;
    @NotBlank(message = Messages.User.PASSWORD_CANT_BE_EMPTY)
    @Pattern(regexp = "^[A-Z0-9._!#^&%+;]{10,25}$",message = Messages.User.PASSWORD_MUST_OBEY_REGEX_RULES)
    private String password;
    @NotBlank(message = Messages.User.PHONE_CANT_BE_EMPTY)
    @Pattern(regexp = "[1-9]{1}+[0-9]{9}]",message = Messages.User.PHONE_MUST_OBEY_REGEX_RULES)
    private String phoneNum;
}
