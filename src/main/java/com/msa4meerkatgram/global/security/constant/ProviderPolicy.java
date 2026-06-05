package com.msa4meerkatgram.global.security.constant;

import lombok.Getter;

@Getter
public enum ProviderPolicy {
    // provate ProviderPolicy NONE = new ProviderPolicy("NONE");
    NONE("NONE")
    ,KAKAO("KAKAO")
    ,GOOGLE("GOOGLE");

    private final String provider;

    ProviderPolicy(String provider){
        this.provider = provider;
    }
}
