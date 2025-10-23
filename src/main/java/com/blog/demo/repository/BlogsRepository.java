package com.blog.demo.repository;

import com.blog.demo.entity.Blog;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Example;
import org.springframework.data.mongodb.core.query.TextCriteria;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.FluentQuery;

import java.util.List;
import java.util.function.Function;

public interface BlogsRepository extends MongoRepository<Blog, ObjectId> {
    List<Blog> findAllBy(TextCriteria criteria);

}
