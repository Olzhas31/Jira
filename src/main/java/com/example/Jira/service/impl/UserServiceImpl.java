package com.example.Jira.service.impl;

import com.example.Jira.entity.User;
import com.example.Jira.entity.UserDetail;
import com.example.Jira.entity.states.Roles;
import com.example.Jira.exception.EmailAlreadyExistsException;
import com.example.Jira.exception.EntityNotFoundException;
import com.example.Jira.exception.UsernameAlreadyExistsException;
import com.example.Jira.mapper.UserDetailMapper;
import com.example.Jira.mapper.UserMapper;
import com.example.Jira.model.UserDto;
import com.example.Jira.model.requests.CreateUserRequest;
import com.example.Jira.repository.UserDetailRepository;
import com.example.Jira.repository.UserRepository;
import com.example.Jira.service.IUserService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.example.Jira.configuration.Constants.*;

@Service
@AllArgsConstructor
public class UserServiceImpl implements IUserService {

    private final UserRepository repository;
    private final UserMapper mapper;
    private final UserDetailMapper userDetailMapper;
    private final BCryptPasswordEncoder passwordEncoder;
    private final UserDetailRepository userDetailRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return repository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User with username " + username + " not found"));
    }

    @Override
    public UserDto save(CreateUserRequest request) {
        if (repository.existsByUsername(request.getUsername())) {
            throw new UsernameAlreadyExistsException(USERNAME_ALREADY_EXISTS + request.getUsername());
        }
        if (userDetailRepository.existsByEmail(request.getEmail())) {
            throw new EmailAlreadyExistsException(EMAIL_ALREADY_EXISTS + request.getEmail());
        }

        request.setPassword(passwordEncoder.encode(request.getPassword()));

        UserDetail userDetail = userDetailMapper.toEntity(request);
        User user = mapper.toEntity(request, userDetail);
        userDetail.setUser(user);

        repository.save(user);

        return mapper.toDto(user);
    }

    @Override
    public List<UserDto> getAll() {
        return repository.findAll()
                .stream().filter(user -> !user.getRole().equals(Roles.ADMIN.name()))
                .map(mapper::toDto)
                .toList();
    }

    @Override
    public UserDto getById(Long id) {
        return repository.findById(id)
                .map(mapper::toDto)
                .orElseThrow(() -> new EntityNotFoundException(USER_NOT_FOUND + id));
    }

    @Override
    public void update(UserDto userDto) {
        User user = repository.findById(userDto.getId())
                .orElseThrow(() -> new EntityNotFoundException(USER_NOT_FOUND + userDto.getId()));

        if (!user.getUserDetail().getEmail().equalsIgnoreCase(userDto.getEmail()) &&
                userDetailRepository.existsByEmail(userDto.getEmail())) {
            throw new EmailAlreadyExistsException(EMAIL_ALREADY_EXISTS + userDto.getEmail());
        }

        user.getUserDetail().setName(userDto.getName());
        user.getUserDetail().setSurname(userDto.getSurname());
        user.getUserDetail().setEmail(userDto.getEmail());
        user.getUserDetail().setPhoneNumber(userDto.getPhoneNumber());
        user.getUserDetail().setInfo(userDto.getInfo());

        repository.save(user);
    }
}
