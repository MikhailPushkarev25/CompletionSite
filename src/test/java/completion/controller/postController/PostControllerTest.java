package completion.controller.postController;

import completion.model.City;
import completion.model.Post;
import completion.service.CityService;
import completion.service.PostService;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import org.junit.jupiter.api.Test;
import org.springframework.ui.Model;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;

class PostControllerTest {

    @Test
    public void whenPosts() {
        List<Post> posts = Arrays.asList(
                new Post(1, "new Post"),
                new Post(2, "new Post")
        );

        Model model = mock(Model.class);
        PostService postService = mock(PostService.class);
        when(postService.findAllPost()).thenReturn(posts);
        CityService cityService = mock(CityService.class);
        PostController postController = new PostController(
                postService,
                cityService
        );
        String page = postController.posts(model);
        verify(model).addAttribute("posts", posts);
        assertThat(page, is("posts"));
    }

    @Test
    public void whenCreatePost() {
        Post post = new Post(1, "new Post");
        City city = new City(1, "Moskow");
        post.setCity(city);
        PostService postService = mock(PostService.class);
        CityService cityService = mock(CityService.class);
        PostController postController = new PostController(
                postService,
                cityService
        );
        String page = postController.createPost(post);
        verify(postService).add(post);
        assertThat(page, is("redirect:/posts"));
    }

    @Test
    public void whenUpdatePost() {
        Post post = new Post(1, "new Post");
        Post postUpdate = new Post(1, "new Post1");
        City city = new City(1, "Moskow");
        post.setCity(city);
        postUpdate.setCity(city);
        PostService postService = mock(PostService.class);
        CityService cityService = mock(CityService.class);
        PostController postController = new PostController(
                postService,
                cityService
        );
        postController.createPost(post);
        String page = postController.updatePost(postUpdate);
        verify(postService).add(postUpdate);
        assertThat(page, is("redirect:/posts"));
    }

    @Test
    public void whenFromAddPost() {
        List<City> cities = Arrays.asList(
                new City(1, "Moskow"),
                new City(2, "Chelybinsk")
        );
        PostService postService = mock(PostService.class);
        CityService cityService = mock(CityService.class);
        when(cityService.findAllCity()).thenReturn(cities);
        Model model = mock(Model.class);
        PostController postController = new PostController(
                postService,
                cityService
        );
        String page = postController.formAddPost(model);
        verify(model).addAttribute("cities", cities);
        assertThat(page, is("addPost"));
    }

    @Test
    public void whenFormUpdatePost() {
        Post post = new Post(1, "new Post");
        City city = new City(1, "city");
        post.setCity(city);
        List<City> cities = Arrays.asList(
                new City(2, "Moskow"),
                new City(3, "Chelybinsk")
        );

        PostService postService = mock(PostService.class);
        when(postService.findById(anyInt())).thenReturn(post);
        CityService cityService = mock(CityService.class);
        when(cityService.findAllCity()).thenReturn(cities);
        Model model = mock(Model.class);
        PostController postController = new PostController(
                postService,
                cityService
        );

        String page = postController.formUpdatePost(model, post.getId());
        verify(model).addAttribute("post", post);
        verify(model).addAttribute("cities", cities);
        assertThat(page, is("updatePost"));
    }
}