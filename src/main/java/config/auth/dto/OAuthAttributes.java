package config.auth.dto;

import com.ssw.book.springboot.domain.user.Role;
import com.ssw.book.springboot.domain.user.User;
import lombok.Builder;
import lombok.Getter;

import java.util.Map;

@Getter
public class OAuthAttributes {
    private Map<String, Object> attributes;
    private String nameAttributeKey;
    private String name;
    private String email;
    private String picture;

    @Builder
    public OAuthAttributes(Map<String, Object> attributes, String nameAttributeKey, String name, String email, String picture) {
        this.attributes = attributes;
        this.nameAttributeKey = nameAttributeKey;
        this.name = name;
        this.email = email;
        this.picture = picture;
        System.out.println("construct OAuthAttributes");
    }

    //OAuth2User에서 반환하는 사용자 정보는 Map이기 때문에 값 하나하나를 변환해야 한다.
    public static OAuthAttributes of(String registrationId, String userNameAttributeName, Map<String, Object> attributes) {
        System.out.println("of method started");
        return ofGoogle(userNameAttributeName, attributes);
    }

    private static OAuthAttributes ofGoogle(String userNameAttributeName, Map<String, Object> attributes) {
        System.out.println("start ofGoogle");
        return OAuthAttributes.builder().name((String)attributes.get("name"))
                .email((String) attributes.get("email"))
                .picture((String) attributes.get("picture"))
                .attributes(attributes)
                .nameAttributeKey(userNameAttributeName)
                .build();
    }

    //User 엔티티 생성.
    //OAuthAttributes에서 엔티티를 생성하는 시점은 처음 가입할 때.
    //가입할 때의 기본 권한을 GUSET로 주기 위해서 role 빌더값에는 Role.GUSETf를 사용.
    //OAuthAttributes 클래스 생성이 끝났으면 같은 패키지에 SessionUser 클래스를 생성한다.
    public User toEntity() {
        System.out.println("start toEnttity");
        return User.builder()
                .name(name)
                .email(email)
                .picture(picture)
                .role(Role.GUSET)
                .build();
    }
}
