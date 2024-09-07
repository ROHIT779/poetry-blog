package com.poetryblog.poetryblog_service.helpers;

import com.poetryblog.poetryblog_service.jdbc.JDBCManager;
import com.poetryblog.poetryblog_service.model.Form;
import com.poetryblog.poetryblog_service.model.Message;
import com.poetryblog.poetryblog_service.model.Topic;
import com.poetryblog.poetryblog_service.model.Writer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class TopicHelper {
  private JDBCManager jdbcManager;

  @Autowired
  public TopicHelper(JDBCManager jdbcManager) {
    this.jdbcManager = jdbcManager;
  }

  public Topic createTopic(Topic topic) {
    topic.setTopicId(jdbcManager.addTopic(topic));
    return topic;
  }

  public Topic getTopic(String topicId) {
    if (jdbcManager.validateId("topic", topicId)) {
      return jdbcManager.getTopic(topicId);
    } else {
      return null;
    }
  }

  public List<Topic> getTopics() {
    return jdbcManager.getTopics();
  }

  public Topic updateTopic(String topicId, Topic topic)
  {
    if (jdbcManager.validateId("topic", topicId)) {
      topic.setTopicId(topicId);
      return jdbcManager.updateTopic(topic);
    } else {
      return null;
    }
  }

  public Message deleteTopic(String topicId)
  {
    if (jdbcManager.validateId("topic", topicId)) {
      int count = jdbcManager.deleteTopic(topicId);
      System.out.println("Delete count: " + count);
      String message = "topicId " + topicId + " is deleted from Database";
      String notes = "Call " + "GET /service/topics/" + topicId + " to verify";
      return new Message(message, notes);
    } else {
      return null;
    }
  }

}
