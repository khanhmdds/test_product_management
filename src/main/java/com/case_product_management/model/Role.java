package com.case_product_management.model;

import com.case_product_management.model.dto.RoleDTO;
import com.case_product_management.model.dto.UserDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Accessors(chain = true)
@Table(name = "roles")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String code;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private EnumRole name;

    @OneToMany(targetEntity = User.class)
    private List<User> users;

    public RoleDTO toRoleDTO(){
        RoleDTO roleDTO = new RoleDTO();
        roleDTO.setId(id);
        roleDTO.setCode(code);
        return roleDTO;
    }


//    public RoleDTO toRoleDTO(){
//        RoleDTO roleDTO = new RoleDTO();
//        Role role = new Role();
//        roleDTO.setId(role.getId());
//        roleDTO.setCode(role.getCode());
//        return roleDTO;
//    }

//    public RoleDTO toRoleDTO(){
//        return new RoleDTO()
//                .setId(id)
//                .setCode(code);
//    }

}
