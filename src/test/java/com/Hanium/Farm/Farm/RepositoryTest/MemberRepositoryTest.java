package com.Hanium.Farm.Farm.RepositoryTest;

import com.Hanium.Farm.Farm.Domain.Member;
import com.Hanium.Farm.Farm.Repository.MemberRepository;
import com.Hanium.Farm.Farm.Repository.MemberRepositoryInterface;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
public class MemberRepositoryTest {

    @Autowired MemberRepositoryInterface memberRepository;
//    @Test
//    void 비번가져오기(){
//        // given
//        String id = "user";
//        String pw="user";
//
//        Member member = memberRepository.getMember(id);
//
//        String result = member.getPw();
//
//        System.out.println(result);
//
//        assertThat(result).isEqualTo(pw);
//    }

    @Test
    void 회원가입(){
        // given
        Member member = new Member("test", "test", "영관", "010-111-1111", 23);

        // when
        memberRepository.join(member);
    }
}
