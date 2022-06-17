package completion.store;

import completion.Main;
import completion.model.User;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class UserDBStoreTest {

    @Test
    public void whenTestAddMethod() {
        UserDBStore userDB = new UserDBStore(new Main().loadPool());
        User user = new User(1, "m@mail.ru", "123", "Mikhail");
        assertThat(userDB.add(user).get(), is(user));
    }

    @Test
    public void whenTestFindByEmailAndPwd() {
        UserDBStore userDB = new UserDBStore(new Main().loadPool());
        User user = new User(1, "m@mail.ru", "123", "Mikhail");
        userDB.add(user);
        Optional<User> userOpt = userDB.findByEmailAndPwd(user.getEmail(), user.getPassword());
        assertTrue(userOpt.isEmpty(), user.getName());
        assertTrue(userOpt.isEmpty(), user.getEmail());
    }
}
