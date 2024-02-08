package com.grapefruitade.honeypost.domain.post.entity;

import com.grapefruitade.honeypost.domain.image.entity.Image;
import com.grapefruitade.honeypost.domain.post.Book;
import com.grapefruitade.honeypost.domain.post.Category;
import com.grapefruitade.honeypost.domain.post.OTT;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@Table(name = "Post")
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id")
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "content")
    private String content;

    @Enumerated(EnumType.STRING)
    @Column(name = "category")
    private Category category;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "OTT")
    private OTT ott;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "book")
    private Book book;

    @Column(name = "author")
    private String author;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
    private List<Image> images = new ArrayList<>();

    @ElementCollection
    @Column(name = "likes")
    private List<Long> likes;

    public void modifyPost(String title, String content) {
        this.title = title;
        this.content = content;
    }
}
