package com.msa4meerkatgram.domain.post.repositories;

import com.msa4meerkatgram.domain.post.entities.Post;
import com.msa4meerkatgram.domain.user.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> { // 전달할 객체와, pk가 되는 자료형을 전달해야함.
    long countByUser(User user);
}
