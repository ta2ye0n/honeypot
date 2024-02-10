package com.grapefruitade.honeypost.domain.post.dto;

import com.grapefruitade.honeypost.domain.post.Book;
import com.grapefruitade.honeypost.domain.post.Category;
import com.grapefruitade.honeypost.domain.post.OTT;
import lombok.Getter;

@Getter
public class WritePost {
    private String title;
    private String content;
    private Category category;
    private OTT ott;
    private Book book;
}
