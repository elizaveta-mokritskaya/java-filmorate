package ru.yandex.practicum.filmorate.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Data;
import ru.yandex.practicum.filmorate.model.validation.DoesntContainWhitespaces;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import java.util.Date;
import java.util.HashSet;

@Data
@Builder
public class User {
    private Integer id;
    @NotNull
    @Email
    private String email;
    @NotNull
    @NotEmpty
    @DoesntContainWhitespaces
    private String login;
    @NotNull
    private String name;
    @NotNull
    @PastOrPresent
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date birthday;

    private HashSet<Integer> friends = new HashSet<>();

    private HashSet<Integer> incomingFriendRequests = new HashSet<>();

    private HashSet<Integer> outgoingFriendRequests = new HashSet<>();

    public User(Integer id, String email, String login, String name, Date birthday, HashSet<Integer> friends) {
        this.id = id;
        this.email = email;
        this.login = login;
        this.name = name;
        this.birthday = birthday;
        if (friends != null) {
            this.friends = friends;
        }
    }
}

