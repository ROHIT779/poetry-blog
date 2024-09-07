package com.poetryblog.poetryblog_service.resource;

import com.poetryblog.poetryblog_service.helpers.FormHelper;
import com.poetryblog.poetryblog_service.helpers.PoemHelper;
import com.poetryblog.poetryblog_service.model.Form;
import com.poetryblog.poetryblog_service.model.Message;
import com.poetryblog.poetryblog_service.model.Poem;
import com.poetryblog.poetryblog_service.model.Topic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/service/poems")
public class PoemResource {

    private final PoemHelper helper;

    @Autowired
    public PoemResource(PoemHelper helper) {
        this.helper = helper;
    }

    @PostMapping("/")
    public ResponseEntity createPoem(@RequestBody Poem poem) {
        poem = helper.createPoem(poem);
        if (poem != null) {
            return new ResponseEntity<>(poem, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(poem, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/{poemId}")
    public ResponseEntity getPoem(@PathVariable("poemId") String poemId) {
        Poem poem = helper.getPoem(poemId);
        if (poem != null) {
            return new ResponseEntity(poem, HttpStatus.OK);
        } else {
            return new ResponseEntity(poem, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/")
    public ResponseEntity getPoems(@RequestParam("writerIds") Optional<List<String>> writerIds, @RequestParam("formIds") Optional<List<String>> formIds, @RequestParam("topicIds") Optional<List<String>> topicIds) {
        List<Poem> poems = helper.getPoemsFiltered(writerIds.orElse(new ArrayList<>()), formIds.orElse(new ArrayList<>()), topicIds.orElse(new ArrayList<>()));
        if (poems != null) {
            return new ResponseEntity(poems, HttpStatus.OK);
        } else {
            return new ResponseEntity(poems, HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{poemId}")
    public ResponseEntity updatePoem(@PathVariable("poemId") String poemId, @RequestBody Poem poem) {
        poem = helper.updatePoem(poemId, poem);
        if (poem != null) {
            return new ResponseEntity<>(poem, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(poem, HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{poemId}")
    public ResponseEntity deletePoem(@PathVariable("poemId") String poemId) {
        Message message = helper.deletePoem(poemId);
        if (message != null) {
            return new ResponseEntity<>(message, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/")
    public ResponseEntity deletePoems() {
        Message message = helper.deletePoems();
        if (message != null) {
            return new ResponseEntity<>(message, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
        }
    }
}
