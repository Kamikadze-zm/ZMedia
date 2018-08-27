package ru.kamikadze_zm.zmedia.repository;

import java.util.List;
import ru.kamikadze_zm.zmedia.model.entity.User;
import ru.kamikadze_zm.zmedia.model.entity.User.Role;

public interface UserRepository extends Repository<User, String> {

    boolean existsByName(String name);

    public List<User> findUsersByRole(Role role);
}
