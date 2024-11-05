package org.marouanedbibih.springsecurity.user;

import java.util.List;

import org.marouanedbibih.springsecurity.exception.AlreadyExistException;
import org.marouanedbibih.springsecurity.exception.MyNotDeleteException;
import org.marouanedbibih.springsecurity.exception.MyNotFoundException;
import org.marouanedbibih.springsecurity.exception.MyNotSave;
import org.marouanedbibih.springsecurity.interfaces.IDaoService;
import org.marouanedbibih.springsecurity.user.dto.CreateUserRequest;
import org.marouanedbibih.springsecurity.user.dto.UpdateUserRequest;
import org.marouanedbibih.springsecurity.user.dto.UserDTO;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements IDaoService<User, UserDTO, CreateUserRequest, UpdateUserRequest, Long> {

    private final UserRepository repository;
    private final UserMapper mapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public UserDTO create(CreateUserRequest req) throws MyNotSave, AlreadyExistException {
        // Check if username already exists
        if (repository.existsByUsername(req.username())) {
            throw new AlreadyExistException("Username already exists", "username");
        }

        try {
            User user = mapper.toEntity(req);
            user.setPassword(passwordEncoder.encode(req.password()));
            User savedUser = repository.save(user);
            return mapper.toDto(savedUser);
        } catch (Exception e) {
            throw new MyNotSave("Failed to save the user");
        }
    }

    @Override
    @Transactional
    public UserDTO update(UpdateUserRequest req, Long id) throws MyNotSave, AlreadyExistException, MyNotFoundException {
        // Retrieve the user from the repository by ID
        User existingUser = repository.findById(id)
                .orElseThrow(() -> new MyNotFoundException("User not found with id: " + id));

        // Check if the requested username exists for another user
        if (req.username() != null && !req.username().equals(existingUser.getUsername()) &&
                repository.existsByUsername(req.username())) {
            throw new AlreadyExistException("Username already exists for another user");
        }

        try {
            // Map the request fields to the existing user entity
            User userToUpdate = mapper.toEntity(req, existingUser);
            if (req.password() != null) {
                userToUpdate.setPassword(passwordEncoder.encode(req.password()));
            }
            User updatedUser = repository.save(userToUpdate);
            return mapper.toDto(updatedUser);
        } catch (Exception e) {
            throw new MyNotSave("Failed to update the user");
        }
    }

    @Override
    public List<UserDTO> getAll() {
        List<User> users = repository.findAll();
        return mapper.toDtoList(users);
    }

    @Override
    public UserDTO get(Long id) throws MyNotFoundException {
        User user = repository.findById(id)
                .orElseThrow(() -> new MyNotFoundException("User not found with id " + id));
        return mapper.toDto(user);
    }

    @Override
    @Transactional
    public void delete(Long id) throws MyNotFoundException, MyNotDeleteException {
        User user = repository.findById(id)
                .orElseThrow(() -> new MyNotFoundException("User not found with id " + id));

        try {
            repository.delete(user);
        } catch (Exception e) {
            throw new MyNotDeleteException("Failed to delete user");
        }
    }
}
