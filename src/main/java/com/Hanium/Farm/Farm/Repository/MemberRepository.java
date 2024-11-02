package com.Hanium.Farm.Farm.Repository;

import com.Hanium.Farm.Farm.Domain.Member;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MemberRepository implements MemberRepositoryInterface {

    private final JdbcTemplate jdbcTemplate;

    // jdbc template 생성자로 생성
    public MemberRepository(DataSource dataSource){
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public Optional<Member> findMemberById(String id) {
        return null;
    }

    @Override
    public void join(Member member) {
        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate);

        jdbcTemplate.update(
                "INSERT INTO user VALUES (?, ?, ?, ?, ?);"
                , member.getId(), member.getPw(), member.getName(), member.getPhone(), member.getAge());
    }

    @Override
    public boolean update(Member member) {
        return false;
    }

    @Override
    public boolean delete(String id) {
        int count = jdbcTemplate.update("DELETE FROM user WHERE id = ?", id);
        boolean result = false;
        if(count == 0){
            result = false;
        }else
            result = true;
        return result;
    }

    @Override
    public Member getMember(String id) {
        List<String> result = jdbcTemplate.query("SELECT * FROM user WHERE id = ?", pwRowMapper(), id);
        String memberInfo = result.get(0);
        String[] info = memberInfo.split(" ");
        Member member = new Member(info[0], info[1], info[2], info[3], Integer.parseInt(info[4]));
        return member; // indexOutOfBoundsException에러 잡기
    }

    private RowMapper<String> pwRowMapper(){
        return (rs, rowNum) -> {
            String result = null;
            StringBuilder temp = null;
            temp = new StringBuilder(rs.getString("id"));
            temp.append(" " + rs.getString("pw"));
            temp.append(" " + rs.getString("name"));
            temp.append(" " + rs.getString("phone_num"));
            temp.append(" " + rs.getString("age"));
            result = temp.toString();
            return result;
        };
    }
}
