package de.htwg.cadworkoutservice.service;

import de.htwg.cadworkoutservice.model.Tag;

import java.util.List;

public interface ITagService {
    Tag saveTag(Tag tag);

    Tag getTagById(long id);

    int updateTag(long id, String updatedName);

    List<Tag> getAll();

    void deleteById(long id);
}
