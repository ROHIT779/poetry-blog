package com.poetryblog.poetryblog_service.resource;

import com.poetryblog.poetryblog_service.helpers.WriterHelper;
import com.poetryblog.poetryblog_service.model.Message;
import com.poetryblog.poetryblog_service.model.Writer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/service/writers")
public class WriterResource {

    private final WriterHelper helper;

    @Autowired
    public WriterResource(WriterHelper helper) {
        this.helper = helper;
    }

    @PostMapping("/")
    public ResponseEntity createWriter(@RequestBody Writer writer) {
        writer = helper.createWriter(writer);
        if (writer != null) {
            return new ResponseEntity<>(writer, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(writer, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/{writerId}")
    public ResponseEntity getWriter(@PathVariable("writerId") String writerId) {
        Writer writer = helper.getWriter(writerId);
        if (writer != null) {
            return new ResponseEntity(writer, HttpStatus.OK);
        } else {
            return new ResponseEntity(null, HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{writerId}")
    public ResponseEntity updateWriter(@PathVariable("writerId") String writerId, @RequestBody Writer writer) {
        writer = helper.updateWriter(writerId, writer);
        if (writer != null) {
            return new ResponseEntity<>(writer, HttpStatus.OK);
        } else {
            return new ResponseEntity(null, HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{writerId}")
    public ResponseEntity deleteWriter(@PathVariable("writerId") String writerId) {
        Message message = helper.deleteWriter(writerId);
        if (message != null) {
            return new ResponseEntity<>(message, HttpStatus.OK);
        } else {
            return new ResponseEntity(null, HttpStatus.BAD_REQUEST);
        }
    }
}
