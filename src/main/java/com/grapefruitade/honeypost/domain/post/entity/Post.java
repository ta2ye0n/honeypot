package com.grapefruitade.honeypost.domain.post.entity;

import com.grapefruitade.honeypost.domain.post.entity.enums.Book;
import com.grapefruitade.honeypost.domain.post.entity.enums.Category;
import com.grapefruitade.honeypost.domain.post.entity.enums.OTT;
import com.grapefruitade.honeypost.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.*;

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

    @Lob
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

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User author;

    private String previewUrl;

    private Integer likes;

    public void modifyPost(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public void uploadPreviewUrl(String previewUrl) {
        this.previewUrl = previewUrl;
    }

    public void like() {
        likes++;
    }

    public void unlike() {
        likes--;
    }
}
