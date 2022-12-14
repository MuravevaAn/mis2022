package ru.mis2022.service.entity.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.mis2022.models.entity.User;
import ru.mis2022.repositories.UserRepository;
import ru.mis2022.service.entity.UserService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository.findByEmail(email);
    }

    @Override public User getCurrentUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    //todo не должно быть написано в строку
    public boolean existsByEmail(String email) { return userRepository.existsByEmail(email); }

    @Override
    //todo не должно быть написано в строку
    public boolean existsById(Long id) { return userRepository.existsById(id); }

    @Override
    public User findByEmailAndExceptCurrentId(String email, Long id) {
        return userRepository.findByEmailAndExceptCurrentId(email, id);
    }

    @Override
    public List<User> findPersonalByFullName(String fullName, String roleName) {
        return userRepository.findPersonalByFullName(fullName, roleName);
    }

    @Override
    public User persist(User user) {
        return userRepository.save(user);
    }

}
