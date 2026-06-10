package com.msa4meerkatgram.domain.post.services;

import com.msa4meerkatgram.domain.post.entities.Post;
import com.msa4meerkatgram.domain.post.mapper.PostMapper;
import com.msa4meerkatgram.domain.post.requests.PostIndexReq;
import com.msa4meerkatgram.domain.post.requests.PostStoreReq;
import com.msa4meerkatgram.domain.post.responses.PostIndexRes;
import com.msa4meerkatgram.global.errors.custom.DeletedRecordException;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostMapper postMapper;

    public PostIndexRes index(PostIndexReq postIndexReq){
        int offset = (postIndexReq.page() -1) * postIndexReq.limit();

        // 특정 페이지 게시글 조회
        List<Post> posts = postMapper.getPagination(postIndexReq.limit(), offset);

        // 토탈 획득
        long total = postMapper.getTotal();
        boolean lastPage = offset + postIndexReq.limit() >= total;

        // 컨트롤러 전달
        return PostIndexRes.builder()
                .total(total)
                .lastPage(lastPage)
                .posts(posts)
                .build();
    }

    public Post show(long id){
        Post post = postMapper.findByPk(id);
        if(post == null){
            throw new DeletedRecordException("이미 삭제된 게시글입니다.");
        }

        return post;
    }

    public void store(PostStoreReq postStoreReq){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Claims claims = (Claims) auth.getPrincipal();
        long userId = Long.parseLong(claims.getSubject());
        Post post = Post.builder()
                .userId(userId)
                .content(postStoreReq.content())
                .image(postStoreReq.image())
                .build();
        postMapper.insert(post);
    }

    public void deletePost(long id){
        long result = postMapper.deletePost(id);

        if(result == 0){
            throw new DeletedRecordException("삭제할 게시글이 존재하지 않습니다.");
        }
    }
}
