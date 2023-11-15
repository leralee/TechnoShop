package com.dns.admin.user;

import com.dns.common.entity.Role;
import com.dns.common.entity.User;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@Transactional
public class UserService {

    public static final int USERS_PER_PAGE = 4;
    private final UserRepository userRepo;
    private final RoleRepository roleRepo;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    UserService(UserRepository userRepo, RoleRepository roleRepo, PasswordEncoder passwordEncoder) {
        this.userRepo = userRepo;
        this.roleRepo = roleRepo;
        this.passwordEncoder = passwordEncoder;
    }

    public List<User> listAll() {
        return userRepo.findAll();
    }

    public Page<User> listByPage(int pageNum, String sortField, String sortDir, String keyword) {
        Sort sort = Sort.by(sortField);
        sort = sortDir.equals("asc") ? sort.ascending() : sort.descending();
        Pageable pageable = PageRequest.of(pageNum - 1, USERS_PER_PAGE, sort);

        if (keyword != null) {
            return userRepo.findAll(keyword, pageable);
        }
        return userRepo.findAll(pageable);
    }

    public List<Role> listRoles() {
        return (List<Role>) roleRepo.findAll();
    }

    public User save(User user) {
        boolean isUpdatingUser = (user.getId() != null);
        if (isUpdatingUser) {
            User existingUser = userRepo.findById(user.getId()).get();
            if (user.getPassword().isEmpty()) {
                user.setPassword(existingUser.getPassword());
            } else {
                encodePassword(user);
            }
        } else {
            encodePassword(user);
        }
        return userRepo.save(user);
    }

    private void encodePassword(User user) {

        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
    }

    public boolean isEmailUnique(Integer id, String email) {
        User userByEmail = userRepo.findByEmail(email);
        // Если нет пользователя с таким email, он уникален
        if (userByEmail == null) return true;
        // Если id равно null, это значит, что создается новый пользователь,
        // и так как userByEmail не null, email не уникален
        if (id == null) {
            return false;
        }
        // Если редактируется существующий пользователь,
        // проверяем, совпадает ли id с найденным по email
        return userByEmail.getId().equals(id);

    }


    public User get(Integer id) throws UserNotFoundException {
        try {
            return userRepo.findById(id).get();
        } catch (NoSuchElementException ex) {
            throw new UserNotFoundException("Пользователь с ID: " + id + " не найден ");
        }
    }

    public void delete(Integer id) throws UserNotFoundException {
        Long countById = userRepo.countById(id);

        if (countById == 0) {
            throw new UserNotFoundException("Пользователь с ID: " + id + " не найден ");
        }
        userRepo.deleteById(id);
    }

    public void updateUserEnabledStatus(Integer id, boolean enabled) {
        userRepo.updateEnabledStatus(id, enabled);
    }
}
