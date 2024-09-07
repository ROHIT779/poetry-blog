package com.poetryblog.poetryblog_service.model;

import java.util.List;

public class Poem {
  private String poemId;
  private String poemName;
  private String poemContent;
  private String writerId;
  private List<String> formIds;
  private List<String> topicIds;

  public Poem() {}

  public Poem(String poemId, String poemName, String poemContent, String writerId, List<String> formIds, List<String> topicIds) {
    this.poemId = poemId;
    this.poemName = poemName;
    this.poemContent = poemContent;
    this.writerId = writerId;
    this.formIds = formIds;
    this.topicIds = topicIds;
  }

  public String getPoemId() {
    return poemId;
  }

  public void setPoemId(String poemId) {
    this.poemId = poemId;
  }

  public String getPoemName() {
    return poemName;
  }

  public void setPoemName(String poemName) {
    this.poemName = poemName;
  }

  public String getPoemContent() {
    return poemContent;
  }

  public void setPoemContent(String poemContent) {
    this.poemContent = poemContent;
  }

  public String getWriterId() {
    return writerId;
  }

  public void setWriterId(String writerId) {
    this.writerId = writerId;
  }

  public List<String> getFormIds() {
    return formIds;
  }

  public void setFormIds(List<String> formIds) {
    this.formIds = formIds;
  }

  public List<String> getTopicIds() {
    return topicIds;
  }

  public void setTopicIds(List<String> topicIds) {
    this.topicIds = topicIds;
  }
}
