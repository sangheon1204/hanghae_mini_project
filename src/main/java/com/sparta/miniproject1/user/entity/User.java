package com.sparta.miniproject1.user.entity;
import com.sparta.miniproject1.user.dto.SignupRequestDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import javax.persistence.*;

@NoArgsConstructor
@Getter
@Entity(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String username;
    @Column(nullable = false)
    private String nickname;
    @Column
    private Long kakaoId;
    @Column(nullable = false)
    private String password;
    @Enumerated(value = EnumType.STRING)
    private UserRoleEnum role = UserRoleEnum.USER;
    @Column
    private String imgurl;
    @Column(nullable = false)
    private boolean state = true;

    public User(SignupRequestDto signupRequestDto, String password, String imgurl) {
        this.username = signupRequestDto.getUsername();
        this.nickname = signupRequestDto.getNickname();
        this.password = password;
        this.imgurl = imgurl;
    }
    public User(String username, Long kakaoId, String password, String imgurl) {
        this.username = username;
        this.nickname = username;
        this.kakaoId = kakaoId;
        this.password = password;
        this.imgurl = imgurl;
    }
    public User kakaoIdUpdate(Long kakaoId) {
        this.kakaoId = kakaoId;
        return this;
    }
    public void update(String npw) {
        this.password=npw;
    }

    public void deleteUser() {
        this.state = false;
    }
}
