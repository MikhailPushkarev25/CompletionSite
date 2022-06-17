package completion.store;

import completion.Main;
import completion.model.City;
import completion.model.Post;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;


public class PostDBStoreTest {

    @Test
    public void whenCreatePost() {
        PostDBStore store = new PostDBStore(new Main().loadPool());
        City city = new City(0, "Moskow");
        Post post = new Post(1, "Java job", "2 year", false, city);
        store.add(post);
        Post postInDB = store.findById(post.getId());
        assertThat(postInDB.getName(), is(post.getName()));
    }

    @Test
    public void whenUpdatePost() {
        PostDBStore store = new PostDBStore(new Main().loadPool());
        City city = new City(0, "Moskow");
        Post post = new Post(1, "Java job", "2 year", false, city);
        Post post1 = new Post(1, "Junior job", "15 year", false, city);
        store.add(post);
        store.update(post1);
        Post postInDB = store.findById(post1.getId());
        assertThat(postInDB.getName(), is(post1.getName()));
    }

    @Test
    public void whenFindAll() {
        PostDBStore store = new PostDBStore(new Main().loadPool());
        City city = new City(0, "Moskow");
        Post post = new Post(1, "Java job", "2 year", false, city);
        store.add(post);
        assertThat(store.findAll().get(0), is(new Post(1, "Java job", "2 year", false, city)));
    }
}