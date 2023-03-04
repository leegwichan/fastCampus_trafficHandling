package com.example.fastcampusmysql.domain.member;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.example.fastcampusmysql.domain.member.entity.Member;
import com.example.fastcampusmysql.util.MemberFixtureFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class MemberTest {

    @DisplayName("회원의 닉네임을 변경할 수 있다.")
    @Test
    void changeNameTest() {
        Member member = MemberFixtureFactory.create();
        String newNickname = "nickname";

        member.changeNickname(newNickname);

        assertThat(member.getNickname()).isEqualTo(newNickname);
    }

    @DisplayName("닉네임 변경시, 회원의 닉네임은 null일 수 없다.")
    @Test
    void changeNameTest_whenNicknameIsNull() {
        Member member = MemberFixtureFactory.create();

        assertThatThrownBy(() -> member.changeNickname(null));
    }

    @DisplayName("닉네임 변경시, 회원의 닉네임은 10자를 초과할 수 없다")
    @Test
    void changeNameTest_whenNicknameLetterOver10() {
        Member member = MemberFixtureFactory.create();
        String newNickname = "nicknameeeee";

        assertThatThrownBy(() -> member.changeNickname(newNickname));
    }
}
