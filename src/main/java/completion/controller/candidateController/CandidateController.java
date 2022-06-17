package completion.controller.candidateController;

import completion.model.Candidate;
import completion.model.City;
import completion.service.CandidateService;
import completion.service.CityService;
import net.jcip.annotations.ThreadSafe;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.core.io.Resource;

import java.io.IOException;

@Controller
@ThreadSafe
public class CandidateController {

    private final CandidateService can;
    private final CityService cityService;

    public CandidateController(CandidateService can, CityService cityService) {
        this.can = can;
        this.cityService = cityService;
    }

    @GetMapping("/candidates")
    public String candidates(Model model) {
        model.addAttribute("candidates", can.findAllCandidate());
        return "candidates";
    }

    @GetMapping("/formAddCandidate")
    public String fromAddCandidate(Model model) {
        model.addAttribute("cities", cityService.findAllCity());
        return "addCandidate";
    }

    @PostMapping("/updateCandidate")
    public String update(Candidate candidate,
                         @RequestParam("file") MultipartFile file) throws IOException {
        candidate.setPhoto(file.getBytes());
        candidate.setCity(cityService.findById(candidate.getCity().getId()));
        can.updateCandidate(candidate);
        return "redirect:/candidates";
    }

    @PostMapping("/createCandidate")
    public String createCandidate(@ModelAttribute Candidate candidate,
                                  @RequestParam("file") MultipartFile file) throws IOException {
        candidate.setPhoto(file.getBytes());
        candidate.setCity(cityService.findById(candidate.getCity().getId()));
        can.addCandidate(candidate);
        return "redirect:/candidates";
    }

    @GetMapping("/formUpdateCandidate/{updateId}")
    public String formUpdateCandidate(Model model, @PathVariable("updateId") int id) {
        Candidate candidate = can.findByIdCandidate(id);
        City city = candidate.getCity();
        if (city != null) {
            candidate.setCity(cityService.findById(candidate.getCity().getId()));
        }
        model.addAttribute("cities", cityService.findAllCity());
        model.addAttribute("candidate", candidate);
        return "updateCandidate";
    }

    @GetMapping("photoCandidate/{candidateId}")
    public ResponseEntity<Resource> downLoad(@PathVariable("candidateId") Integer candidateId) {
        Candidate candidate = can.findByIdCandidate(candidateId);
        return ResponseEntity.ok()
                .headers(new HttpHeaders())
                .contentLength(candidate.getPhoto().length)
                .contentType(MediaType.parseMediaType("application/octet-stream"))
                .body(new ByteArrayResource(candidate.getPhoto()));
    }
}
