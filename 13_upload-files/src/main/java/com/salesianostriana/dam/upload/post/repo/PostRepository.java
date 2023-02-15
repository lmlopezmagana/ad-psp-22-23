package com.salesianostriana.dam.upload.post.repo;

import com.salesianostriana.dam.upload.post.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {
}
