package by.st.meetingwithfriendsbot.controller;

import by.st.meetingwithfriendsbot.model.UserEntity;
import by.st.meetingwithfriendsbot.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("http://localhost:8099/api/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    @PostMapping("/")
    public ResponseEntity<UserEntity> createUser(@RequestBody UserEntity newUser) {
        UserEntity user = userService.createUser(newUser);
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }
}
