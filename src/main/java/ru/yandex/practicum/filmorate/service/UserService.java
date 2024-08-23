package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.UserDoesntExistException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Friend;
import ru.yandex.practicum.filmorate.model.FriendStatus;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.friend.FriendStorage;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final UserStorage storage;
    private final FriendStorage friendStorage;

    public UserService(@Qualifier("databaseUserStorage") UserStorage storage, FriendStorage friendStorage) {
        this.storage = storage;
        this.friendStorage = friendStorage;
    }

    public List<User> getUsers() {
        return storage.getList();
    }

    public User getUserById(Integer id) {
        Optional<User> optionalUser = storage.getById(id);
        if (optionalUser.isEmpty()) {
            throw new UserDoesntExistException();
        }
        return optionalUser.get();
    }

    public User addNewUser(User user) {
        return storage.add(user);
    }

    public User updateUser(User user) {
        Optional<User> optionalUser = storage.getById(user.getId());
        if (optionalUser.isEmpty()) {
            throw new UserDoesntExistException();
        }
        User currentUser = optionalUser.get();
        currentUser.setEmail(user.getEmail());
        currentUser.setLogin(user.getLogin());
        if ((user.getName() == null) || (user.getName().isEmpty())) {
            user.setName(user.getLogin());
        }
        currentUser.setName(user.getName());
        currentUser.setBirthday(user.getBirthday());
        return storage.updateUser(user);
    }

    public void makeFriends(Integer id1, Integer id2) {
        getUserById(id1);
        getUserById(id2);
        if (friendStorage.findFriend(id1, id2).isPresent()) {
            return;
        }
        Friend friendship = Friend.builder()
                .userId(id1)
                .friendId(id2)
                .friendStatus(FriendStatus.NOT_APPROVED)
                .build();
        friendStorage.addFriend(friendship);
    }

    public void deleteFriends(Integer id1, Integer id2) {
        if (friendStorage.findFriend(id1, id2).isEmpty()) {
            throw new ValidationException("В списке друзей нет пользователя с таким id");
        } else {
            friendStorage.deleteFriend(id1, id2);
        }
    }

    public List<User> getFriends(Integer id) {
        getUserById(id);
        List<Friend> friends = friendStorage.getFriendByUserId(id);
        Set<Integer> setId = new HashSet<>();
        friends.forEach(friend -> {
            setId.add(friend.getUserId());
            setId.add(friend.getFriendId());
        });
        setId.remove(id);
        return storage.findUsersByIds(setId);
    }

    public List<User> getCommonFriends(Integer id1, Integer id2) {
        Optional<User> userOptional = storage.getById(id1);
        Optional<User> userOptional2 = storage.getById(id2);
        if ((userOptional.isEmpty() || (userOptional2.isEmpty()))) {
            throw new UserDoesntExistException();
        }
        List<User> friends1 = getFriends(id1);
        List<User> friends2 = getFriends(id2);
        return friends1.stream().filter(friends2::contains).collect(Collectors.toList());
    }

    public void checkIfUserExists(Integer userId) {
        storage.getList().stream().filter(user -> Objects.equals(user.getId(), userId)).findAny()
                .orElseThrow(UserDoesntExistException::new);
    }
}
