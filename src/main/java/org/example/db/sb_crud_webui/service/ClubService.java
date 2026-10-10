package org.example.db.sb_crud_webui.service;

import org.example.db.sb_crud_webui.domain.Club;
import org.example.db.sb_crud_webui.dto.*;
import org.example.db.sb_crud_webui.repository.ClubRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import java.util.List;

@Service
public class ClubService {
    private final ClubRepository repository;

    public ClubService(ClubRepository repository) {
        this.repository = repository;
    }
    public ClubResponse create(ClubRequest r) {
        validate(r);
        return toResponse(repository.save(new Club(null,r.getName(),r.getRole(),r.getGender(),r.getStudentId(),r.getDepartment())));
    }
    public List<ClubResponse> findAll() {
        return repository.findAll().stream().map(this::toResponse).toList();
    }
    public int count() {
        return repository.findAll().size();
    }
    public ClubResponse findById(Long id) {
        return toResponse(findClub(id));
    }
    public ClubResponse update(Long id, ClubRequest r) {
        validate(r);
        Club b=findClub(id); b.setName(r.getName()); b.setRole(r.getRole()); b.setGender(r.getGender());
        b.setStudentId(r.getStudentId()); b.setDepartment(r.getDepartment());
        return toResponse(repository.update(b));
    }
    public void delete(Long id) { findClub(id); repository.deleteById(id); }
    private void validate(ClubRequest r) {
        if (r.getName() == null || r.getName().isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Name is required");
        }
        if (r.getRole() == null || r.getRole().isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Role is required");
        }
        if (r.getGender() == null || r.getGender().isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Gender is required");
        }
        if (r.getStudentId() == null || r.getStudentId().isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Student ID is required");
        }
        if (r.getDepartment() == null || r.getDepartment().isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Department is required");
        }
    }
    private Club findClub(Long id) { return repository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"Club not found: "+id)); }
    private ClubResponse toResponse(Club b) { return new ClubResponse(b.getId(),b.getName(),b.getRole(),b.getGender(),b.getStudentId(),b.getDepartment()); }
}
