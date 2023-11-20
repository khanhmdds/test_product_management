package com.case_product_management.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Accessors(chain = true)
public class UserChangePassWordDTO {
    private Long id;

    @NotEmpty(message = "Not empty")
    private String userName;

    @NotEmpty(message = "Not empty")
    @Size(min = 6, max = 100, message = "6 - 100 characters")
    private String newPassword;

    @NotEmpty(message = "Not empty")
    @Size(min = 6, max = 100, message = "6 - 100 characters")
    private String newPasswordConfirm;
}
