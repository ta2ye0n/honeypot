package com.grapefruitade.honeypost.domain.auth.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@Table(name = "RefreshToken")
@Entity
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RefreshToken {

    @Id
    @Column(name = "refresh_key")
    private String key;

    @Column
    private String value;

    public RefreshToken updateValue(String token) {
        this.value = token;
        return this;
    }
}
