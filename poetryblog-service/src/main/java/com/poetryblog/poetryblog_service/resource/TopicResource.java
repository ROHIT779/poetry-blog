package com.poetryblog.poetryblog_service.resource;

import com.poetryblog.poetryblog_service.helpers.TopicHelper;
import com.poetryblog.poetryblog_service.model.Message;
import com.poetryblog.poetryblog_service.model.Topic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/service/topics")
public class TopicResource {

    private final TopicHelper helper;

    @Autowired
    public TopicResource(TopicHelper helper) {
        this.helper = helper;
    }

    @PostMapping("/")
    public ResponseEntity createTopic(@RequestBody Topic topic) {
        topic = helper.createTopic(topic);
        if (topic != null) {
            return new ResponseEntity<>(topic, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(topic, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/{topicId}")
    public ResponseEntity getTopic(@PathVariable("topicId") String topicId) {
        Topic topic = helper.getTopic(topicId);
        if (topic != null) {
            return new ResponseEntity(topic, HttpStatus.OK);
        } else {
            return new ResponseEntity(topic, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/")
    public ResponseEntity getTopics() {
        List<Topic> topics = helper.getTopics();
        return new ResponseEntity(topics, HttpStatus.OK);

    }

    @PutMapping("/{topicId}")
    public ResponseEntity updateTopic(@PathVariable("topicId") String topicId, @RequestBody Topic topic) {
        topic = helper.updateTopic(topicId, topic);
        if (topic != null) {
            return new ResponseEntity<>(topic, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(topic, HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{topicId}")
    public ResponseEntity deleteTopic(@PathVariable("topicId") String topicId) {
        Message message = helper.deleteTopic(topicId);
        if (message != null) {
            return new ResponseEntity<>(message, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
        }
    }
}
