package de.htwg.cadworkoutservice.controller;

import de.htwg.cadworkoutservice.model.Set;
import de.htwg.cadworkoutservice.service.SetServiceImpl;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/set")
public class SetController {
    //TODO testing
    private static final Log LOGGER = LogFactory.getLog(SetController.class);

    private final SetServiceImpl setService;

    public SetController(@Autowired SetServiceImpl setService) {
        this.setService = setService;
    }

    @PostMapping("/")
    public Set insertSet(@RequestBody Set set) {
        //TODO var checks
        //TODO logging
        return setService.saveSet(set);
    }

    @GetMapping("/{setId}")
    public Set getSetById(@PathVariable long setId) {
        //TODO var checks
        //TODO logging
        return setService.getTagById(setId);
    }

    @GetMapping("/all")
    public List<Set> getAllSets() {
        //TODO var checks
        //TODO logging
        return setService.getAll();
    }

    @DeleteMapping("/{setId}")
    public void deleteById(@PathVariable long setId) {
        //TODO var checks
        //TODO logging
        setService.deleteById(setId);
    }

    @PatchMapping("/setId")
    public void updateById(@PathVariable long setId) {
        //TODO var checks
        //TODO logging
        setService.updateById(setId, 0.0, 0);
    }
}
