package ru.kamikadze_zm.zmedia.service.impl;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kamikadze_zm.zmedia.model.entity.User;
import ru.kamikadze_zm.zmedia.repository.UserRepository;
import ru.kamikadze_zm.zmedia.service.UserService;

@Service
public class UserServiceImpl implements UserService, UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    @Transactional(readOnly = true)
    public User getByEmail(String email) {
        Optional<User> ou = userRepository.findById(email);
        if (!ou.isPresent()) {
            return null;
        }
        return ou.get();
    }

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(final String email) throws UsernameNotFoundException {
        User u = getByEmail(email);
        if (u == null) {
            throw new UsernameNotFoundException("Пользователь не найден");
        }
        return u;
    }

    @Override
    @Transactional(readOnly = true)
    public List<User> getAll() {
        return userRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public List<User> getPage(int page, int size) {
        return userRepository.findAll(PageRequest.of(page, size)).getContent();
    }

    @Override
    @Transactional(readOnly = true)
    public long getCount() {
        return userRepository.count();
    }

    @Override
    @Transactional(readOnly = true)
    public boolean emailExist(String email) {
        return userRepository.existsById(email);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean nameExist(String name) {
        return userRepository.existsByName(name);
    }

    @Override
    @Transactional
    public void add(User user) {
        userRepository.save(user);
    }

    @Override
    @Transactional
    public void update(User user) {
        userRepository.save(user);
    }

    @Override
    @Transactional
    public void deleteById(String id) {
        userRepository.deleteById(id);
    }
}
