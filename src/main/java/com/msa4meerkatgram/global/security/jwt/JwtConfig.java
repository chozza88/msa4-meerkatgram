package com.msa4meerkatgram.global.security.jwt;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "security.jwt")
public record JwtConfig(
        // 쿠키를 HTTPS에서만 전송할지 여부를 확인
        // 운영 서버 -> true, 로컬 개발 -> false
        boolean secure,
        String issuer,
        String type,
        int accessTokenExpiry,
        int refreshTokenExpiry,
        String refreshTokenCookieName,
        int refreshTokenCookieExpiry,
        String secret,
        String headerKey,
        String scheme,
        String reissUri
) {
}
