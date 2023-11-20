package com.case_product_management.model;

import com.case_product_management.model.dto.RoleDTO;
import com.case_product_management.model.dto.UserDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.List;
import java.util.Set;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Accessors(chain = true)
@Entity
@Table(name = "users")
public class User extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    @ManyToOne
    @JoinColumn(name = "role_id", referencedColumnName = "id", nullable = false)
    private Role role;

////        userDTO.setRole(role.toRoleDTO());
//        return userDTO;
//    public UserDTO toUserDTO() {
//        return new UserDTO()
//                .setId()
//                .setUsername()
//                .setPassword(password);
////                .setRole(role.toRoleDTO());
//    }

//    public UserDTO toUserDTO(){
//        UserDTO userDTO = new UserDTO();
//        userDTO.setId(id);
//        userDTO.setUsername(username);
//        userDTO.setPassword(password);
//        // Chuyển đổi đối tượng Role sang RoleDTO
//        Role role = this.getRole();
//        RoleDTO roleDTO = role.toRoleDTO();
//        // Set đối tượng RoleDTO vào UserDTO
//        userDTO.setRole(roleDTO);
//
//        return userDTO;
//    }

    //chaining method
    public UserDTO toUserDTO(){
        return new UserDTO()
                .setId(id)
                .setUsername(username)
                .setPassword(password)
                .setRole(role.toRoleDTO());
    }
}
