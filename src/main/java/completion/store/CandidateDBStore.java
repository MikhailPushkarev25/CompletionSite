package completion.store;

import completion.model.Candidate;
import completion.model.City;
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
public class CandidateDBStore {

    private final BasicDataSource pool;
    private static final Logger LOG = LoggerFactory.getLogger(CandidateDBStore.class.getName());


    public CandidateDBStore(BasicDataSource pool) {
        this.pool = pool;
    }

    public List<Candidate> findAll() {
        List<Candidate> candidates = new ArrayList<>();
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement("SELECT * FROM candidate")) {
            try (ResultSet it = ps.executeQuery()) {
                while (it.next()) {
                    candidates.add(new Candidate(
                            it.getInt("id"),
                            it.getString("name"),
                            it.getString("description"),
                            it.getBoolean("visible"),
                            new City(it.getInt("city_id"))
                    ));
                }
            }
        } catch (Exception e) {
            LOG.error("Exception logger findAll Candidates", e);
        }
        return candidates;
    }

    public Candidate add(Candidate candidate) {
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement(
                     "INSERT INTO candidate(name, description, visible, city_id) VALUES (?, ?, ?, ?)",
                     PreparedStatement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, candidate.getName());
            ps.setString(2, candidate.getDescription());
            ps.setBoolean(3, candidate.isVisible());
            ps.setInt(4, candidate.getCity().getId());
            ps.execute();
            try (ResultSet id = ps.getGeneratedKeys()) {
                if (id.next()) {
                    candidate.setId(id.getInt(1));
                }
            }
        } catch (Exception e) {
            LOG.error("Exception logger add Candidates", e);
        }
        return candidate;
    }

    public void update(Candidate candidate) {
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement(
                     "UPDATE candidate SET name = ?, description = ?, visible = ?, city_id = ? WHERE id = ?")) {
            ps.setString(1, candidate.getName());
            ps.setString(2, candidate.getDescription());
            ps.setBoolean(3, candidate.isVisible());
            ps.setInt(4, candidate.getCity().getId());
            ps.setInt(5, candidate.getId());
            ps.executeUpdate();
            try (ResultSet it = ps.executeQuery()) {
                if (it.next()) {
                    candidate.setId(it.getInt(1));
                }
            }
        } catch (Exception e) {
            LOG.error("Exception logger update Candidates", e);
        }
    }

    public Candidate findById(int id) {
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement("SELECT * FROM candidate WHERE id = ?")) {
            ps.setInt(1, id);
            try (ResultSet it = ps.executeQuery()) {
                if (it.next()) {
                    return new Candidate(
                            it.getInt("id"),
                            it.getString("name"),
                            it.getString("description"),
                            it.getBoolean("visible"),
                            new City(it.getInt("city_id")));
                }
            }
        } catch (Exception e) {
            LOG.error("Exception logger findById Candidates", e);
        }
        return null;
    }
}
