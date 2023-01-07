package de.htwg.cadworkoutservice.service;

import de.htwg.cadworkoutservice.model.Tag;
import de.htwg.cadworkoutservice.repository.ITagRepository;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TagServiceImpl implements ITagService {
    private static final Log LOGGER = LogFactory.getLog(TagServiceImpl.class);

    private final ITagRepository tagRepository;

    public TagServiceImpl(@Autowired ITagRepository tagRepository) {
        this.tagRepository = tagRepository;
    }

    @Override
    public Tag saveTag(Tag tag) {
        return tagRepository.save(tag);
    }

    @Override
    public Tag getTagById(long id) {
        return tagRepository.getReferenceById(id);
    }

    @Override
    public int updateTag(long id, String updatedName) {
        return tagRepository.updateNameById(updatedName, id);
    }

    @Override
    public List<Tag> getAll() {
        return tagRepository.findAll();
    }

    @Override
    public void deleteById(long id) {
        tagRepository.deleteById(id);
    }
}
