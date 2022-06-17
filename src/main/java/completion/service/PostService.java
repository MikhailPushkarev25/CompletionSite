package completion.service;

import completion.model.Post;
import completion.store.PostDBStore;
import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@ThreadSafe
public class PostService {

    private final PostDBStore store;
    private CityService cityService;

    public PostService(PostDBStore store, CityService cityService) {
        this.store = store;
        this.cityService = cityService;
    }

    public List<Post> findAllPost() {
        List<Post> posts = store.findAll();
        posts.forEach(
                post -> post.setCity(
                        cityService.findById(post.getCity().getId())
                )
        );
        return posts;
    }

    public void add(Post post) {
        store.add(post);
    }

    public Post findById(int id) {
        Post post = store.findById(id);
        post.setCity(cityService.findById(post.getCity().getId()));
        return post;
    }

    public void update(Post post) {
        store.update(post);
    }
}
