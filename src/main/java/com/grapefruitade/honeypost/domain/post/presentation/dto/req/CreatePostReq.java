package com.grapefruitade.honeypost.domain.post.presentation.dto.req;

import com.grapefruitade.honeypost.domain.post.entity.enums.Book;
import com.grapefruitade.honeypost.domain.post.entity.enums.Category;
import com.grapefruitade.honeypost.domain.post.entity.enums.OTT;
import lombok.Getter;

@Getter
public class CreatePostReq {
    private String title;
    private String content;
    private Category category;
    private OTT ott;
    private Book book;
}
