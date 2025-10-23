package com.blog.demo.service;

import com.blog.demo.entity.Blog;
import com.blog.demo.repository.BlogsRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.TextCriteria;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Component
public class BlogEntryService {

    @Autowired
    private BlogsRepository blogsRepository;


    @Transactional
    public void saveEntry(Blog blog){
        try{
            blogsRepository.save(blog);
        }catch(Exception e){
            throw new RuntimeException("an error occured while saving the blog");
        }
    }

    public List<Blog> getAll(){
        return blogsRepository.findAll();
    }

    public Optional<Blog> findById(ObjectId id){
        return blogsRepository.findById(id);
    }

    @Transactional
    public boolean deleteById(ObjectId id){
        boolean removed = false;
        try{
            blogsRepository.deleteById(id);
        }catch(Exception e){
            System.out.println(e);
            throw new RuntimeException("an error occured while deleting the entry ",e);
        }
        return removed;
    }

    public List<Blog> filterBlog(String search){
        TextCriteria criteria = TextCriteria.forDefaultLanguage().matchingAny(search);
        return blogsRepository.findAllBy(criteria);
    }
}
