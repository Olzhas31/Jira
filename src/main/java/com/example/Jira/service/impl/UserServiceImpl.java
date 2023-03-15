package com.example.Jira.service.impl;

import com.example.Jira.entity.Project;
import com.example.Jira.entity.User;
import com.example.Jira.entity.UserDetail;
import com.example.Jira.entity.states.Roles;
import com.example.Jira.exception.EmailAlreadyExistsException;
import com.example.Jira.exception.EntityNotFoundException;
import com.example.Jira.exception.UserBlockedException;
import com.example.Jira.exception.UsernameAlreadyExistsException;
import com.example.Jira.mapper.UserDetailMapper;
import com.example.Jira.mapper.UserMapper;
import com.example.Jira.model.UserDto;
import com.example.Jira.model.requests.CreateUserRequest;
import com.example.Jira.repository.ProjectRepository;
import com.example.Jira.repository.UserDetailRepository;
import com.example.Jira.repository.UserRepository;
import com.example.Jira.service.IUserService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
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
    private final ProjectRepository projectRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var user = repository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User with username " + username + " not found"));
        if (user.getLocked()) {
            throw new UserBlockedException("blocked");
        }
        return user;
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
    public List<UserDto> getUsersIsNotExistsByProjectId(Long projectId) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(()-> new EntityNotFoundException(PROJECT_NOT_FOUND + projectId));

        return repository.findAll()
                .stream()
                .filter(user -> !user.getRole().equals(Roles.ADMIN.name()) &&
                        !user.getRole().equals(Roles.PROJECT_MANAGER.name()) &&
                        !project.getUsers().contains(user)
                )
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
    public UserDto update(UserDto userDto) {
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

        user = repository.save(user);
        return mapper.toDto(user);
    }

    @Override
    public void changeBlock(Long id) {
        User user = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(USER_NOT_FOUND + id));
        user.setLocked(!user.getLocked());
        repository.save(user);
    }

    @Override
    public User getEntityById(Long userId) {
        return repository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException(USER_NOT_FOUND + userId));
    }

    @Override
    public void editPhotoByUser(User user, MultipartFile file) throws IOException {
//        String fileName = user.getId() + "_" + StringUtils.cleanPath(file.getOriginalFilename());
        String fileName = user.getId() + "." +
                file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".") + 1);;

        try (InputStream inputStream = file.getInputStream()) {
            Path filePath = uploadPath.resolve(fileName);
            Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException ioe) {
            throw new IOException("File saqtalmady: " + fileName, ioe);
        }

        UserDetail userDetail = user.getUserDetail();
        userDetail.setUrlPicture(fileName);
        repository.save(user);
    }
}
