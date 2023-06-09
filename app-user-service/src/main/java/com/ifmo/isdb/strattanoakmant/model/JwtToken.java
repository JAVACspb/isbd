package com.ifmo.isdb.strattanoakmant.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.function.Function;

@Entity
@Table(name = "token")
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@AllArgsConstructor
/**
 * Класс токен
 */
public class JwtToken {

    @Id
    @Column(name = "token")
    @JsonProperty("token")
    private String id;

    @Column(name = "role")
    @JsonProperty("role")
    private String role;

    @Column(name = "time")
    @JsonProperty("created_time")
    private LocalDateTime localDateTime;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        JwtToken jwtToken = (JwtToken) o;
        return id != null && Objects.equals(id, jwtToken.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
