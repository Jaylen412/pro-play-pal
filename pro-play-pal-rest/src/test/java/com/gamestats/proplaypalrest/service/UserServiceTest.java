package com.gamestats.proplaypalrest.service;


import com.gamestats.proplaypalrest.model.User;
import com.gamestats.proplaypalrest.model.UserDto;
import com.gamestats.proplaypalrest.model.UserRole;
import com.gamestats.proplaypalrest.repo.UserRepo;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepo userRepo;
    @Mock
    private  UserMapper userMapper;
    private UserService userService;

    @BeforeEach
    public void setUp() {
       userService = new UserService(userRepo, userMapper);
    }

    @Test
    public void createUser_successful() throws Exception {
        UUID uuid = UUID.randomUUID();
        User testUser = User.builder()
                .id(uuid)
                .firstName("Ben")
                .lastName("Ten")
                .userName("ben10")
                .password("test")
                .userRole(UserRole.USER)
                .favoriteTeam(null)
                .createdDate(Instant.now())
                .build();

        UserDto testDto = UserDto.builder()
                .id(uuid)
                .firstName("Ben")
                .lastName("Ten")
                .userName("ben10")
                .userRole(UserRole.USER)
                .favoriteTeam(null)
                .createdDate(Instant.now())
                .build();

        when(userRepo.save(isA(User.class))).thenReturn(testUser);
        when(userMapper.userEntityToDto(isA(User.class))).thenReturn(testDto);

        UserDto result = userService.createUser(testUser);

        assertEquals(testDto, result);
        verify(userRepo,times(1)).save(isA(User.class));
        verify(userRepo, times(1)).findByUserName(anyString());
        verify(userMapper, times(1)).userEntityToDto(isA(User.class));
    }

    @Test
    public void createUser_throw_exception() {
        UUID uuid = UUID.randomUUID();
        User testUser = User.builder()
                .id(uuid)
                .firstName("Ben")
                .lastName("Ten")
                .userName("ben10")
                .password("test")
                .userRole(UserRole.USER)
                .favoriteTeam(null)
                .createdDate(Instant.now())
                .build();

        when(userRepo.findByUserName(anyString())).thenReturn(testUser);

        Exception exception = assertThrows(Exception.class, () -> {
            userService.createUser(testUser);
        });
        verify(userRepo, times(1)).findByUserName(anyString());
    }

    @Test
    public void getUser_succeeds() {
        UUID uuid = UUID.randomUUID();

        User testUSer = User.builder()
                .id(uuid)
                .firstName("")
                .lastName("")
                .password("test")
                .userName("")
                .userRole(UserRole.USER)
                .build();

        UserDto userDto = UserDto.builder()
                .id(uuid)
                .firstName(testUSer.getFirstName())
                .lastName(testUSer.getLastName())
                .userName(testUSer.getUserName())
                .userRole(testUSer.getUserRole())
                .build();

        when(userRepo.findById(uuid)).thenReturn(Optional.ofNullable(testUSer));
        when(userMapper.userEntityToDto(isA(User.class))).thenReturn(userDto);

        UserDto result = userService.getUser(uuid);

        assertEquals(userDto, result);
        verify(userRepo, times(1)).findById(uuid);
        verify(userMapper,times(1)).userEntityToDto(isA(User.class));
    }

//    @Test
//    public void getUser_throws_exception() {
//        UUID uuid = UUID.randomUUID();
//
//        User testUSer = User.builder()
//                .id(uuid)
//                .firstName("")
//                .lastName("")
//                .password("test")
//                .userName("")
//                .userRole(UserRole.USER)
//                .build();
//
//        when(userRepo.findById(uuid)).thenThrow(new Exception());
//
//        Exception exception = assertThrows(Exception.class, () -> {
//           userService.getUser(uuid);
//        });
//        assertEquals(String.format("User: [%s] was not able to be located", uuid), exception.getMessage());
//    }
}
