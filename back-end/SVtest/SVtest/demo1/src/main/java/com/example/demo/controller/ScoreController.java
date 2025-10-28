
package com.example.demo.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.Score;
import com.example.demo.service.ScoreService;

@RestController
@RequestMapping("/api/scores")
public class ScoreController {

    private final ScoreService scoreService;

    public ScoreController(ScoreService scoreService) {
        this.scoreService = scoreService;
    }

    @GetMapping
    public List<Score> getAll() {
        return scoreService.getAll();
    }

    @GetMapping("/{id}")
    public Score getById(@PathVariable Long id) {
        return scoreService.getById(id);
    }

    @PostMapping
    public Score create(@RequestBody Score score) {
        return scoreService.create(score);
    }

    @PutMapping("/{id}")
    public Score update(@PathVariable Long id, @RequestBody Score score) {
        return scoreService.update(id, score);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        scoreService.delete(id);
    }
}
