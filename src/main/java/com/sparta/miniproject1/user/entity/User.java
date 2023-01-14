package com.sparta.miniproject1.user.entity;
import com.sparta.miniproject1.post.entity.Post;
import com.sparta.miniproject1.wish.entity.Wish;
import lombok.Getter;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Getter
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String username;
    @Column(nullable = false)
        private String password;
    @Enumerated(value = EnumType.STRING)
    private UserRoleEnum role;
    //올려둔 품목 리스트
    @OneToMany(mappedBy = "user")
    private List<Post> postList = new ArrayList<>();
    //상품 찜한 리스트
    @OneToMany(mappedBy = "user")
    private List<Wish> wishList = new ArrayList<>();


    public User(String username, String password, UserRoleEnum role) {
        this.username = username;
        this.password = password;
        this.role = role;
    }

    public void update(String npw) {
        this.password=npw;
    }
}
