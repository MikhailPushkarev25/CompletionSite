package completion.store;

import completion.Main;
import completion.model.Candidate;
import completion.model.City;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

class CandidateDBStoreTest {

    @Test
    public void whenCreatePost() {
        CandidateDBStore store = new CandidateDBStore(new Main().loadPool());
        City city = new City(0, "Moskow");
        Candidate candidate = new Candidate(1, "Java", "2 year", false, city);
        store.add(candidate);
        Candidate candidateInDB = store.findById(candidate.getId());
        assertThat(candidateInDB.getName(), is(candidate.getName()));
    }

    @Test
    public void whenUpdatePost() {
        CandidateDBStore store = new CandidateDBStore(new Main().loadPool());
        City city = new City(0, "Moskow");
        Candidate candidate = new Candidate(1, "Java", "2 year", false, city);
        Candidate candidate1 = new Candidate(1, "Junior", "10 year", false, city);
        store.add(candidate);
        store.update(candidate1);
        Candidate candidateInDB = store.findById(candidate1.getId());
        assertThat(candidateInDB.getName(), is(candidate1.getName()));
    }

    @Test
    public void whenFindAll() {
        CandidateDBStore store = new CandidateDBStore(new Main().loadPool());
        City city = new City(0, "Moskow");
        Candidate candidate = new Candidate(1, "Java", "2 year", false, city);
        store.add(candidate);
        assertThat(store.findAll().get(0), is(new Candidate(1, "Java", "2 year", false, city)));
    }

}