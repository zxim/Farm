package com.Hanium.Farm.Farm.Service;

import com.Hanium.Farm.Farm.Repository.MemberRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;

public class memberServiceTest {
    MemberRepository memberRepository;

    @Autowired
    public memberServiceTest(MemberRepository memberRepository){
        this.memberRepository = memberRepository;
    }

//    @Test
//    void login(){
//        String inputId = "user";
//        String inputPw = "user";
//
//        String getpw = memberRepository.getPwHash(inputId);
//
//        assertThat(inputPw).isEqualTo(getpw);
//    }
}
