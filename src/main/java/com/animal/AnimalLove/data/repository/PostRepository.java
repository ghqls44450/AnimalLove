package com.animal.AnimalLove.data.repository;

import com.animal.AnimalLove.data.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {
}
