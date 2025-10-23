package com.blog.demo.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.TextIndexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@NoArgsConstructor
@Document(collection = "blog_entry")
public class Blog {
    @Id
    private ObjectId id;
    @NonNull
    @TextIndexed
    private String title;
    @TextIndexed
    private String content;
    @TextIndexed
    private String category;
    private List<String> tags;
}
