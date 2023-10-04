package com.Recipe_Project.dto;

import com.Recipe_Project.entity.Member;
import lombok.*;
import org.hibernate.validator.constraints.Length;
import org.modelmapper.ModelMapper;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class MemberFormDto {

    private Long id;

    @NotBlank(message = "닉네임은 필수 입력 값입니다.")
    @Length(min = 2, max = 6, message = "닉네임은 2자 이상, 6자 이하로 입력해주세요.")
    private String nickname;

    @NotEmpty(message = "이메일은 필수 입력 값입니다.")
    @Email(message = "이메일 형식으로 입력해주세요.")
    private String email;

    @NotEmpty(message = "비밀번호는 필수 입력 값입니다.")
    @Length(min = 8, max = 16, message = "비밀번호는 8자 이상, 16자 이하로 입력해주세요.")
    private String password;

    @NotEmpty(message = "비밀번호 확인은 필수 입력 값입니다.")
    private String passwordcheck;

    private static ModelMapper modelMapper = new ModelMapper();
    public static MemberFormDto of(Member member){
        return modelMapper.map(member, MemberFormDto.class);
    }
}


