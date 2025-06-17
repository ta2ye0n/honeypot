package com.grapefruitade.honeypost.domain.post.dto.req;

import com.grapefruitade.honeypost.domain.post.enums.Book;
import com.grapefruitade.honeypost.domain.post.enums.Category;
import com.grapefruitade.honeypost.domain.post.enums.OTT;
import lombok.Getter;

@Getter
public class CreatePostReq {
    private String title;
    private String content;
    private Category category;
    private OTT ott;
    private Book book;
}
