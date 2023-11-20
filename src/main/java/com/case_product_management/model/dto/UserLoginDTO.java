package com.codegym.model.dto.user;


import com.case_product_management.model.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class UserLoginDTO {
    private Long id;

    @NotBlank(message = "Not Empty")
    @Email(message = "Invalid")
    @Size(min = 5, max = 50, message = "5 - 100 characters")
    private String username;

    @NotBlank(message = "Not empty")
    @Size(max = 30, message = "max 30 characters")
    private String password;

    public UserLoginDTO(Long id, String username) {
        this.id = id;
        this.username = username;
    }

    public User toUser() {
        return new User()
                .setId(id)
                .setUsername(username)
                .setPassword(password);
    }
}