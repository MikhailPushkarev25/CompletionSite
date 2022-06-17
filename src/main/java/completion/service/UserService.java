package completion.service;

import completion.model.User;
import completion.store.UserDBStore;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private final UserDBStore store;

    public UserService(UserDBStore store) {
        this.store = store;
    }

    public Optional<User> add(User user) {
        return store.add(user);
    }

    public Optional<User> findUserByEmailAndPwd(String email, String password) {
        return store.findByEmailAndPwd(email, password);
    }
}
