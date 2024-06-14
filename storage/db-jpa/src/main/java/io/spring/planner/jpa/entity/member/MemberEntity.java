package io.spring.planner.jpa.entity.member;

import io.spring.planner.domain.common.Email;
import io.spring.planner.domain.member.Member;
import io.spring.planner.domain.member.MemberPassword;
import io.spring.planner.domain.member.MemberRegistrationState;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Table(name = "member")
@Entity
public class MemberEntity {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;
    @Column(name = "nickname", nullable = false)
    private String nickname;
    @Column(name = "email", nullable = false, unique = true)
    private String email;
    @Column(name = "password", nullable = false)
    private String password;
    @Enumerated(EnumType.STRING)
    @Column(name = "registration_state", nullable = false)
    private MemberRegistrationState registrationState;

    public static MemberEntity create(Member member) {
        return new MemberEntity(
            member.getId(),
            member.getNickname(),
            member.getEmail().getValue(),
            member.getPassword().getEncodedPassword(),
            member.getRegistrationState()
        );
    }

    public Member toModel() {
        return Member.convert(
            this.id,
            this.nickname,
            new Email(this.email),
            new MemberPassword(this.password),
            this.registrationState
        );
    }

    public void delete() {
        this.registrationState = MemberRegistrationState.DELETION;
    }
}
