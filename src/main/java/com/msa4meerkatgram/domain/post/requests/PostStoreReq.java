package com.msa4meerkatgram.domain.post.requests;


import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

@Builder
public record PostStoreReq(
        @NotBlank
        String content,
        String image
) {
}
