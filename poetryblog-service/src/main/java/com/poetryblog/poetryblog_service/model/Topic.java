package com.poetryblog.poetryblog_service.model;

public class Topic {
  private String topicId;
  private String topicName;

  public Topic() {}

  public Topic(String topicId, String topicName) {
    this.topicId = topicId;
    this.topicName= topicName;
  }

  public String getTopicId() {
    return topicId;
  }

  public void setTopicId(String topicId) {
    this.topicId = topicId;
  }

  public String getTopicName() {
    return topicName;
  }

  public void setTopicName(String topicName) {
    this.topicName = topicName;
  }
}
