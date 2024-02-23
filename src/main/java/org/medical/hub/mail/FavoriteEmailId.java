package org.medical.hub.mail;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
@Embeddable
@AllArgsConstructor
@NoArgsConstructor
public class FavoriteEmailId implements Serializable {

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "user_email_id")
    private Long userEmailId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass())
            return false;

        FavoriteEmailId that = (FavoriteEmailId) o;
        return Objects.equals(userId, that.userId) &&
                Objects.equals(userEmailId, that.userEmailId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, userEmailId);
    }
}