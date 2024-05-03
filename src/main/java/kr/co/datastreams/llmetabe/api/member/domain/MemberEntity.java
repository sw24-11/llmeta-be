package kr.co.datastreams.llmetabe.api.member.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import java.util.List;
import kr.co.datastreams.llmetabe.api.auth.dto.request.SignupRequestDto;
import kr.co.datastreams.llmetabe.api.extraction.domain.ExtractionEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "member")
public class MemberEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id", nullable = false)
    private Integer memberId;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "email", nullable = false, unique = true)
    @Email
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "job", nullable = false)
    private String job;

    @OneToMany(mappedBy = "member")
    private List<ExtractionEntity> extractions;

    public MemberEntity(SignupRequestDto signupRequestDto) {
        this.name = signupRequestDto.getName();
        this.email = signupRequestDto.getEmail();
        this.password = signupRequestDto.getPassword();
        this.job = signupRequestDto.getJob();
    }
}
