package com.example.springstudybook.config.auth;

import com.example.springstudybook.config.dto.OAuthAttributes;
import com.example.springstudybook.config.dto.SessionUser;
import com.example.springstudybook.domain.user.User;
import com.example.springstudybook.domain.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.Collections;

@RequiredArgsConstructor
@Service
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {
    private final UserRepository userRepository;
    private final HttpSession httpSession;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2UserService delegate = new DefaultOAuth2UserService();
        OAuth2User oAuth2user = delegate
                .loadUser(userRequest);

//      현재 로그인 진행중인 서비스를 구분하는 코드(ex. 구글 로그인인지, 네이버 로그인인지 구분)
        String registrationId = userRequest.getClientRegistration().getRegistrationId();

//      userNameAttributeName : OAuth2 로그인 진행 시 키가 되는 필드값을 이야기한다 Primary Key와 같은 의미
//      구글의 경우 기본적으로 코드를 지원, 네이버 카카오 등은 기본 지원을 하지않는다
//      구글의 기본코드는 sub , 네이버, 구글 로그인을 동시 지원할 때 사용된다
        String userNameAttributeName = userRequest.getClientRegistration().getProviderDetails()
                .getUserInfoEndpoint().getUserNameAttributeName();

//      OAuth2UserService를 통해 가져온 OAuth2User의 attribute를 담은 클래스
//      네이버 등 다른 소셜 로그인도 이 클래스 사용
        OAuthAttributes attributes = OAuthAttributes.of(registrationId, userNameAttributeName, oAuth2user.getAttributes());

        User user = saveOrUpdate(attributes);

//      SessionUser세션에 사용자 정보를 저장하기 위한 Dto 클래스
//      왜 User 클래스를 사용하지않고, SessionUser라는 클래스를 만들어 사용할까?
//      User 클래스를 사용하면 직렬화를 구현하지않았다라는 오류가 발생한다
//      User는 Entity이기때문에 언제 다른 엔티티와 관계가 형성될지 모르기 때문에 User에 직렬화 코드를 넣는건 고민해봐야한다
//      ex) OneToMany 나 ManyToMany 등 자식 엔티티를 가지고있는 경우 직렬화 대상에 자식들까지 포함되니
//      성능이슈, 부수효과가 발생할 확률이 높기 때문인다
        httpSession.setAttribute("user", new SessionUser(user));

        return new DefaultOAuth2User(
                Collections.singleton(
                        new SimpleGrantedAuthority(user.getRoleKey())),
                        attributes.getAttributes(),
                        attributes.getNameAttributeKey());
    }

    private User saveOrUpdate(OAuthAttributes attributes){
        User user = userRepository.findByEmail(attributes.getEmail())
                .map(entity -> entity.update(attributes.getName(),
                        attributes.getPicture()))
                .orElse(attributes.toEntity());

        return userRepository.save(user);
    }
}
