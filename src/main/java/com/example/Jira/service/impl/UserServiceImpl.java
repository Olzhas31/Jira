package com.example.Jira.service.impl;

import com.example.Jira.entity.User;
import com.example.Jira.entity.UserDetail;
import com.example.Jira.entity.states.Roles;
import com.example.Jira.exception.UsernameAlreadyExistsException;
import com.example.Jira.mapper.UserDetailMapper;
import com.example.Jira.mapper.UserMapper;
import com.example.Jira.model.UserDto;
import com.example.Jira.model.requests.CreateUserRequest;
import com.example.Jira.repository.UserRepository;
import com.example.Jira.service.IUserService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.example.Jira.configuration.Constants.USERNAME_ALREADY_EXISTS;

@Service
@AllArgsConstructor
public class UserServiceImpl implements IUserService {

    private final UserRepository repository;
    private final UserMapper mapper;
    private final UserDetailMapper userDetailMapper;
    private final BCryptPasswordEncoder passwordEncoder;

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
}
