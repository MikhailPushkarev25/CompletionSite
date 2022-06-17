package completion.controller.postController;

import completion.model.City;
import completion.model.Post;
import completion.service.CityService;
import completion.service.PostService;
import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;


@Controller
@ThreadSafe
public class PostController {
    private final PostService posts;
    private final CityService cities;
    public PostController(PostService posts, CityService cities) {
        this.posts = posts;
        this.cities = cities;
    }

    @GetMapping("/posts")
    public String posts(Model model) {
        model.addAttribute("posts", posts.findAllPost());
        return "posts";
    }

    @GetMapping("/formAddPost")
    public String formAddPost(Model model) {
        model.addAttribute("cities", cities.findAllCity());
        return "addPost";
    }

    @PostMapping("/updatePost")
    public String updatePost(@ModelAttribute Post post) {
        post.setCity(cities.findById(post.getCity().getId()));
        posts.update(post);
        return "redirect:/posts";
    }

    @PostMapping("/createPost")
    public String createPost(@ModelAttribute Post post) {
        post.setCity(cities.findById(post.getCity().getId()));
        posts.add(post);
        return "redirect:/posts";
    }

    @GetMapping("/formUpdatePost/{postId}")
    public String formUpdatePost(Model model, @PathVariable("postId") int id) {
        Post post = posts.findById(id);
        City city = post.getCity();
        if (city != null) {
            post.setCity(cities.findById(city.getId()));
        }
        model.addAttribute("post", post);
        model.addAttribute("cities", cities.findAllCity());
        return "updatePost";
    }
}
