package com.gamestats.proplaypalrest.service;


import com.gamestats.proplaypalrest.model.Team;
import com.gamestats.proplaypalrest.model.User;
import com.gamestats.proplaypalrest.model.UserDto;
import com.gamestats.proplaypalrest.model.UserRole;
import com.gamestats.proplaypalrest.repo.UserRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCrypt;

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

        assertEquals("User: ben10 Already Exist", exception.getMessage());
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

    @Test
    public void getUser_throws_exception() {
        UUID uuid = UUID.randomUUID();

        when(userRepo.findById(uuid)).thenThrow(new RuntimeException());

        Exception exception = assertThrows(Exception.class, () -> {
           userService.getUser(uuid);
        });
        assertEquals(String.format("User: [%s] was not able to be located", uuid), exception.getMessage());
    }

    @Test
    public void updateUser_successful() throws Exception {
        UUID uuid = UUID.randomUUID();
        UUID teamUuid = UUID.randomUUID();
        User testUser = User.builder()
                .id(uuid)
                .firstName("Ben")
                .lastName("Ten")
                .userName("Benjamin697")
                .password("test")
                .favoriteTeam(null)
                .build();

        User updatedUser = User.builder()
                .id(uuid)
                .userName("Benjamin392")
                .favoriteTeam(Team.builder()
                        .teamId(teamUuid)
                        .city("Detroit")
                        .fullName("Detroit Lions")
                        .conference("NFC")
                        .build())
                .build();

        UserDto testDto = UserDto.builder()
                .id(uuid)
                .userName("Benjamin392")
                .favoriteTeam(Team.builder()
                        .teamId(teamUuid)
                        .city("Detroit")
                        .fullName("Detroit Lions")
                        .conference("NFC")
                        .build())
                .build();

        when(userRepo.findById(uuid)).thenReturn(Optional.ofNullable(testUser));
        when(userRepo.findByUserName(anyString())).thenReturn(null);
        when(userRepo.save(isA(User.class))).thenReturn(updatedUser);
        when(userMapper.userEntityToDto(isA(User.class))).thenReturn(testDto);

        UserDto result = userService.updateUser(uuid, testDto);

        assertEquals(testDto.getUserName(), result.getUserName());
        assertEquals(testDto.getFavoriteTeam(), result.getFavoriteTeam());

        verify(userRepo, times(1)).findById(uuid);
        verify(userRepo, times(1)).findByUserName(anyString());
        verify(userRepo,times(1)).save(isA(User.class));
    }

    @Test
    public void updateUser_existing_userName() throws Exception {
        UUID uuid = UUID.randomUUID();
        UUID teamUuid = UUID.randomUUID();
        User testUser = User.builder()
                .id(uuid)
                .firstName("Ben")
                .lastName("Ten")
                .userName("Benjamin697")
                .password("test")
                .favoriteTeam(null)
                .build();

        User updatedUser = User.builder()
                .id(UUID.randomUUID())
                .userName("Benjamin698")
                .favoriteTeam(Team.builder()
                        .teamId(teamUuid)
                        .city("Detroit")
                        .fullName("Detroit Lions")
                        .conference("NFC")
                        .build())
                .build();

        UserDto testDto = UserDto.builder()
                .id(uuid)
                .userName("Benjamin698")
                .favoriteTeam(Team.builder()
                        .teamId(teamUuid)
                        .city("Detroit")
                        .fullName("Detroit Lions")
                        .conference("NFC")
                        .build())
                .build();

        when(userRepo.findById(uuid)).thenReturn(Optional.ofNullable(testUser));
        when(userRepo.findByUserName(anyString())).thenReturn(updatedUser);

        Exception exception = assertThrows(Exception.class, () -> {
           userService.updateUser(uuid, testDto);
        });

        assertEquals("Username: Benjamin698 already exists", exception.getMessage());

        verify(userRepo,times(1)).findById(uuid);
        verify(userRepo, times(1)).findByUserName(anyString());
    }

    @Test
    public void updateUser_null() throws Exception {
        UUID uuid = UUID.randomUUID();
        UUID teamUuid = UUID.randomUUID();
        UserDto testDto = UserDto.builder()
                .id(uuid)
                .userName("Benjamin698")
                .favoriteTeam(Team.builder()
                        .teamId(teamUuid)
                        .city("Detroit")
                        .fullName("Detroit Lions")
                        .conference("NFC")
                        .build())
                .build();

        when(userRepo.findById(uuid)).thenReturn(null);

        UserDto result = userService.updateUser(uuid, testDto);
        assertEquals(null, result);
    }

    @Test
    public void authenticateUser_true() {
        String userName = "Benjamin697";
        String givenPassword = "test";
        User user = User.builder()
                .id(UUID.randomUUID())
                .firstName("")
                .lastName("")
                .password(BCrypt.hashpw("test", BCrypt.gensalt()))
                .userName(userName)
                .build();

        when(userRepo.findByUserName(userName)).thenReturn(user);

        boolean result = userService.authenticateUser(userName, givenPassword);

        assertEquals(true, result);
        verify(userRepo, times(1)).findByUserName(userName);
    }

    @Test
    public void authenticateUser_false() {
        String userName = "Benjamin697";
        String givenPassword = "123";
        User user = User.builder()
                .id(UUID.randomUUID())
                .firstName("")
                .lastName("")
                .password(BCrypt.hashpw("test", BCrypt.gensalt()))
                .userName(userName)
                .build();

        when(userRepo.findByUserName(userName)).thenReturn(user);

        boolean result = userService.authenticateUser(userName, givenPassword);

        assertEquals(false, result);
        verify(userRepo, times(1)).findByUserName(userName);
    }
/*
    @Test
    public void updatePassword_succeeds() {
        String userName = "Benjamin697";
        String oldPassword = "test";
        String updatedPassword = "123";

        User user = User.builder()
                .id(UUID.randomUUID())
                .firstName("")
                .lastName("")
                .password(BCrypt.hashpw("test", BCrypt.gensalt()))
                .userName(userName)
                .build();

        User updatedUser = User.builder()
                .id(UUID.randomUUID())
                .firstName("")
                .lastName("")
                .password(BCrypt.hashpw("123", BCrypt.gensalt()))
                .userName(userName)
                .build();

        when(userRepo.findByUserName(userName)).thenReturn(user);
        when(userRepo.save(isA(User.class))).thenReturn(updatedUser);
    }
    TEST VOID FUNCTION
*/
}
