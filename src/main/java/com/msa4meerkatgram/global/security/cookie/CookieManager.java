package com.msa4meerkatgram.global.security.cookie;

import com.msa4meerkatgram.global.security.jwt.JwtConfig;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class CookieManager {
    private final JwtConfig jwtConfig;

    // Request Header에서 특정 쿠키를 획득(Optional 반환)
    // Optional에 값이 없으면 예외를 발생시켜 비정상 요청 처리

    /**
     * Request Header에서 특정 쿠키를 획득(Optional 반환)
     * @param request 리퀘스트
     * @param name 찾고자하는 쿠키명
     * @return Optional<Cookie>
     * @throws Exception
     */
    public Optional<Cookie> getCookie(HttpServletRequest request, String name){
        // 쿠키 존재 여부 확인
        if(request.getCookies() == null){
            return Optional.empty();
        }

        // name에 맞는 쿠키 획득
        return Arrays.stream(request.getCookies())
                .filter(cookie -> cookie.getName().equals(name))
                .findFirst();
    }


    // 쿠키 생성 메서드
    public void setCookie(HttpServletResponse response, String name, String value, int maxAge, String path){
        Cookie cookie = new Cookie(name, value); // 해당 이름과 값으로 쿠키 인스턴스 생성
        cookie.setPath(path); // 쿠키를 사용할 path 설정
        cookie.setMaxAge(maxAge); // 쿠키 유효 시간 설정
        cookie.setHttpOnly(true); // HTTPOnly 설정 (XSS 공격 방지)
        // secure()의 값이 false인데 개발환경으로 http를 사용해야 하기 때문.
        cookie.setSecure(jwtConfig.secure()); // 시큐어 설정(MITM 공격 방지) MITM: 클라이언트에서 서버로 전송될 때 중간에 데이터를 탈취하는 것

        response.addCookie(cookie);
    }

}
