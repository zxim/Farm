package com.Hanium.Farm.Farm.Service;

import com.Hanium.Farm.Farm.Domain.Member;
import com.Hanium.Farm.Farm.Repository.MemberRepositoryInterface;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MemberService {
    MemberRepositoryInterface memberRepository;

    @Autowired
    public MemberService(MemberRepositoryInterface memberRepository){
        this.memberRepository = memberRepository;
    }

    public boolean login(String id, String pw) throws NoSuchAlgorithmException {
        boolean result = false;

        // 여기 memberRepository이용
        Member member = memberRepository.getMember(id);
        String hash_pw = member.getPw();

        if(pw.equals(hash_pw))
            result = true;

        return result;
    }

    public void join(Member member){
//        boolean result = false;
        memberRepository.join(member);
//        return result;
    }

    public String getPw(String id){
        String pw;
        Member member = memberRepository.getMember(id);
        pw = member.getPw();
        return pw;
    }

    public boolean delete(String id){
        boolean result = memberRepository.delete(id);
        return result;
    }
}
