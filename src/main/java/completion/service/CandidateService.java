package completion.service;

import completion.model.Candidate;
import completion.store.CandidateDBStore;
import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@ThreadSafe
public class CandidateService {

    private final CandidateDBStore store;
    private CityService cityService;

    public CandidateService(CandidateDBStore store, CityService cityService) {
        this.store = store;
        this.cityService = cityService;
    }

    public List<Candidate> findAllCandidate() {
        List<Candidate> candidates = store.findAll();
        candidates.forEach(
                candidate -> candidate.setCity(
                        cityService.findById(candidate.getCity().getId())
                )
        );
        return candidates;
    }

    public void addCandidate(Candidate candidate) {
        store.add(candidate);
    }

    public Candidate findByIdCandidate(int id) {
        Candidate candidate = store.findById(id);
        candidate.setCity(cityService.findById(candidate.getCity().getId()));
        return candidate;
    }

    public void updateCandidate(Candidate candidate) {
        store.update(candidate);
    }
}
