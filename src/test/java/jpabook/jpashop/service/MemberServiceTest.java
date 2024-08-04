package jpabook.jpashop.service;

import jakarta.transaction.Transactional;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Transactional
public class MemberServiceTest {

    @Autowired
    private MemberService memberService;

    @Autowired
    private MemberRepository memberRepository;

    private static final String MEMBER_NAME = "kim";

    @BeforeEach
    public void setUp() {
        // 기본적인 초기화 작업을 여기서 수행할 수 있습니다.
    }

    @Test
    public void testMemberJoin() {
        // Given
        Member member = createMember(MEMBER_NAME);

        // When
        Long savedId = memberService.join(member);

        // Then
        Member foundMember = memberRepository.findOne(savedId);
        assertEquals(member.getName(), foundMember.getName(), "저장된 회원의 이름이 일치하지 않습니다.");
    }

    @Test
    public void testDuplicateMemberThrowsException() {
        // Given
        Member member1 = createMember(MEMBER_NAME);
        Member member2 = createMember(MEMBER_NAME);

        // When
        memberService.join(member1);

        // Then
        IllegalStateException thrown = assertThrows(IllegalStateException.class, () -> {
            memberService.join(member2);
        }, "중복된 회원 가입 시 예외가 발생하지 않았습니다.");

        assertEquals("이미 존재하는 회원입니다.", thrown.getMessage());
    }

    private Member createMember(String name) {
        Member member = new Member();
        member.setName(name);
        return member;
    }
}
