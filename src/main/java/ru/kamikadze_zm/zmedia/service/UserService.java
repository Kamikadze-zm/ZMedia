package ru.kamikadze_zm.zmedia.service;

import java.util.List;
import ru.kamikadze_zm.zmedia.model.entity.User;

public interface UserService {

    public User getByEmail(String email);

    public List<User> getAll();

    public List<User> getPage(int page, int size);

    public long getCount();

    public boolean emailExist(String email);

    public boolean nameExist(String email);

    public void add(User user);

    public void update(User user);

    public void deleteById(String email);
}
