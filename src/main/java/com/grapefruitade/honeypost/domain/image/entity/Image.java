package com.grapefruitade.honeypost.domain.image.entity;

import com.grapefruitade.honeypost.domain.post.entity.Post;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "Image")
public class Image {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column (name = "image_id")
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "post_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Post post;

    @NotEmpty
    @Column(name = "original_name")
    private String originalName;

    @NotEmpty
    @Column(name = "save_name")
    private String saveName;

    @Column(name = "url")
    private String url;

}
