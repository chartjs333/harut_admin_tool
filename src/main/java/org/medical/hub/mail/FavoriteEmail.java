package org.medical.hub.mail;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.medical.hub.models.User;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "favorite_emails")
@AllArgsConstructor
@NoArgsConstructor
public class FavoriteEmail {

    @EmbeddedId
    private FavoriteEmailId id = new FavoriteEmailId();

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("userId")
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("userEmailId")
    @JoinColumn(name = "user_email_id")
    private UserEmails email;
}
