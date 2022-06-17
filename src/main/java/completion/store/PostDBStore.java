package completion.store;

import completion.model.City;
import completion.model.Post;
import org.apache.commons.dbcp2.BasicDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

@Repository
public class PostDBStore {

    private final BasicDataSource pool;
    private static final Logger LOG = LoggerFactory.getLogger(PostDBStore.class.getName());

    public PostDBStore(BasicDataSource pool) {
        this.pool = pool;
    }

    public List<Post> findAll() {
        List<Post> posts = new ArrayList<>();
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement("SELECT * FROM post")) {
            try (ResultSet it = ps.executeQuery()) {
                while (it.next()) {
                    posts.add(new Post(
                            it.getInt("id"),
                            it.getString("name"),
                            it.getString("description"),
                            it.getBoolean("visible"),
                            new City(it.getInt("city_id"))
                    ));
                }
            }
        } catch (Exception e) {
            LOG.error("Exception logger findAll Post", e);
        }
        return posts;
    }

    public Post add(Post post) {
        try (Connection cn = pool.getConnection();
        PreparedStatement ps = cn.prepareStatement(
                "INSERT INTO post(name, description, visible, city_id) VALUES (?, ?, ?, ?)",
                PreparedStatement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, post.getName());
            ps.setString(2, post.getDescription());
            ps.setBoolean(3, post.isVisible());
            ps.setInt(4, post.getCity().getId());
            ps.execute();
            try (ResultSet id = ps.getGeneratedKeys()) {
                if (id.next()) {
                    post.setId(id.getInt(1));
                }
            }
        } catch (Exception e) {
            LOG.error("Exception logger add Post", e);
        }
        return post;
    }

    public void update(Post post) {
        try (Connection cn = pool.getConnection();
        PreparedStatement ps = cn.prepareStatement(
                "UPDATE post SET name = ?, description = ?, visible = ?, city_id = ? WHERE id = ?")) {
            ps.setString(1, post.getName());
            ps.setString(2, post.getDescription());
            ps.setBoolean(3, post.isVisible());
            ps.setInt(4, post.getCity().getId());
            ps.setInt(5, post.getId());
           ps.executeUpdate();
           try (ResultSet it = ps.executeQuery()) {
               if (it.next()) {
                   post.setId(it.getInt(1));
               }
           }
        } catch (Exception e) {
            LOG.error("Exception logger update Post", e);
        }
    }

    public Post findById(int id) {
        try (Connection cn = pool.getConnection();
        PreparedStatement ps = cn.prepareStatement("SELECT * FROM post WHERE id = ?")) {
            ps.setInt(1, id);
            try (ResultSet it = ps.executeQuery()) {
                if (it.next()) {
                    return new Post(
                            it.getInt("id"),
                            it.getString("name"),
                            it.getString("description"),
                            it.getBoolean("visible"),
                            new City(it.getInt("city_id")));
                }
            }
        } catch (Exception e) {
            LOG.error("Exception logger findById Post", e);
        }
        return null;
    }
}
