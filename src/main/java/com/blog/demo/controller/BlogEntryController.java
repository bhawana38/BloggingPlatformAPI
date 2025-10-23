package com.blog.demo.controller;

import com.blog.demo.entity.Blog;
import com.blog.demo.service.BlogEntryService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/posts")
public class BlogEntryController {
    @Autowired
    private BlogEntryService blogEntryService;

    @PostMapping()
    public ResponseEntity<Blog> createEntry(@RequestBody Blog blogEntry){
        try{
            blogEntryService.saveEntry(blogEntry);
            return new ResponseEntity<>(blogEntry,HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

    }

    @GetMapping("/{myid}")
    public ResponseEntity<?> getById(@PathVariable ObjectId myid){
        Optional<Blog> blogEntry=blogEntryService.findById(myid);
        if(blogEntry.isPresent()){
            return new ResponseEntity<>(blogEntry.get(),HttpStatus.OK);
        }
    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping("/{myid}")
    public ResponseEntity<?> updateBlogById(
            @PathVariable  ObjectId myid,
            @RequestBody Blog newEntry
    ){
            Optional<Blog> blogEntry=blogEntryService.findById(myid);
            if(blogEntry.isPresent()){
                Blog old = blogEntry.get();
                old.setTitle(!newEntry.getTitle().equals("") ? newEntry.getTitle() : old.getTitle());
                old.setContent(newEntry.getContent()!=null && !newEntry.getContent().isEmpty() ? newEntry.getContent() : old.getContent());
                old.setCategory(newEntry.getCategory()!=null && !newEntry.getCategory().isEmpty() ? newEntry.getCategory() : old.getCategory());
                old.setTags(newEntry.getTags()!=null && !newEntry.getTags().equals("") ? newEntry.getTags() : old.getTags());
                blogEntryService.saveEntry(old);
                return new ResponseEntity<>(old,HttpStatus.OK);
            }else{
                return new ResponseEntity<>(null,HttpStatus.NOT_FOUND);
            }

    }

    @DeleteMapping("/{myid}")
    public ResponseEntity<?> deleteById(@PathVariable ObjectId myid){
        boolean removed=blogEntryService.deleteById(myid);
        if (removed) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping
    public ResponseEntity<List<Blog>> getPosts(
            @RequestParam(name = "term", required = false) String term) {

        List<Blog> posts;

        if (term != null && !term.trim().isEmpty()) {
            // If 'term' is present, perform the search
            posts = blogEntryService.filterBlog(term);
        } else {
            // If 'term' is NOT present (or is empty), get all
            posts = blogEntryService.getAll();
        }

        if (posts == null || posts.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(posts, HttpStatus.OK);
    }

}
