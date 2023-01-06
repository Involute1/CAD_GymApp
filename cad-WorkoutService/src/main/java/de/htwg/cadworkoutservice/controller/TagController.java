package de.htwg.cadworkoutservice.controller;

import de.htwg.cadworkoutservice.model.Tag;
import de.htwg.cadworkoutservice.service.TagServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tag")
public class TagController {
    //TODO testing

    private final TagServiceImpl tagService;

    public TagController(@Autowired TagServiceImpl tagService) {
        this.tagService = tagService;
    }

    @PostMapping("/{tagName}")
    public void insertTag(@PathVariable String tagName) {
        //TODO var checking
        //TODO logging
        Tag createdTag = new Tag(tagName);
        tagService.saveTag(createdTag);
    }

    @GetMapping("/{tagId}")
    public Tag getTagById(@PathVariable long tagId) {
        //TODO logging
        return tagService.getTagById(tagId);
    }

    @GetMapping("/all")
    public List<Tag> getAll() {
        //TODO logging
        return tagService.getAll();
    }

    @DeleteMapping("/{tagId}")
    public boolean deleteTagById(@PathVariable long tagId) {
        //TODO var checking
        //TODO logging
        tagService.deleteById(tagId);
        return true;
    }

    @PatchMapping("/{tagId}")
    public void updateTag(@PathVariable long tagId, @RequestBody String updatedName) {
        //TODO var checking
        //TODO logging
        tagService.updateTag(tagId, updatedName);
    }
}
