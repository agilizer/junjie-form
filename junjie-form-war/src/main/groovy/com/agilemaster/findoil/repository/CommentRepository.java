package com.agilemaster.findoil.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.agilemaster.findoil.domain.Comment;


public interface CommentRepository extends JpaRepository<Comment, Long> {
}

