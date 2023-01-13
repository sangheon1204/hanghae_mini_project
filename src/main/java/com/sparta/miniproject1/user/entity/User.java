package com.sparta.miniproject1.user.entity;


import com.sparta.miniproject1.user.dto.ChangePasswordRequestDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Pattern;

@Getter
@NoArgsConstructor
@Entity(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // nullable: null 허용 여부
    // unique: 중복 허용 여부 (false 일때 중복 허용)

    @Column(nullable = false)
    @Pattern(regexp = "(?=.*[0-9])(?=.*[a-z]).{4,10}")
    private String username;


    @Column(nullable = false)
    @Pattern(regexp = "(?=.*[0-9])(?=.*[a-z]).{4,10}")
        private String password;


    @Enumerated(value = EnumType.STRING)
    private UserRoleEnum role;

    public User(String username, String password, UserRoleEnum role) {
        this.username = username;
        this.password = password;
        this.role = role;
    }

    public void update(String npw) {
        this.password=npw;
    }
}
