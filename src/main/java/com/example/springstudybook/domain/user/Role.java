package com.example.springstudybook.domain.user;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Role {
    
//  스프링 시큐리티에서는 권한 코드에 항상 ROLE_ 이앞에 있어야한다
//  코드별 키값을 ROLE_GUEST, ROLE_USER 드응로 지정한다
    GUEST("ROLE_GUEST", "손님"),
    USER("ROLE_USER", "일반 사용자");
    
    private final String key;
    private final String title;
}
