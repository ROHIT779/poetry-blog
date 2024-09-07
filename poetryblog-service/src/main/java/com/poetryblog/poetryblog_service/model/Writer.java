package com.poetryblog.poetryblog_service.model;

public class Writer {
  private String writerId;
  private String writerName;
  private String writerInfo;
  private String writerPetname;

  public Writer() {}

  public Writer(String writerId, String writerName, String writerInfo, String writerPetname) {
    this.writerId = writerId;
    this.writerName = writerName;
    this.writerInfo = writerInfo;
    this.writerPetname = writerPetname;
  }

  public String getWriterId() {
    return writerId;
  }

  public void setWriterId(String writerId) {
    this.writerId = writerId;
  }

  public String getWriterName() {
    return writerName;
  }

  public void setWriterName(String writerName) {
    this.writerName = writerName;
  }

  public String getWriterInfo() {
    return writerInfo;
  }

  public void setWriterInfo(String writerInfo) {
    this.writerInfo = writerInfo;
  }

  public String getWriterPetname() {
    return writerPetname;
  }

  public void setWriterPetname(String writerPetname) {
    this.writerPetname = writerPetname;
  }
}
