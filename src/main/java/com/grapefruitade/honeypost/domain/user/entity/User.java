package com.grapefruitade.honeypost.domain.user.entity;

import com.grapefruitade.honeypost.domain.user.enums.Role;
import jakarta.persistence.*;
import lombok.*;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
@Entity
@Table(name = "HoneyPot")
public class User {

    @Id
    @Column(name = "user_id")
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "username", unique = true, nullable = false)
    private String username; //회원 가입, 로그인 할 때 사용

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "nickname", unique = true, nullable = false)
    private String nickname; // 글 작성할 때 사용

    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    private Role role;

}
