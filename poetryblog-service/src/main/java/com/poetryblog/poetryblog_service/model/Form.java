package com.poetryblog.poetryblog_service.model;

public class Form {
  private String formId;
  private String formName;
  private String formDescription;

  public Form() {}

  public Form(String formId, String formName, String formDescription) {
    this.formId = formId;
    this.formName = formName;
    this.formDescription = formDescription;
  }

  public String getFormId() {
    return formId;
  }

  public void setFormId(String formId) {
    this.formId = formId;
  }

  public String getFormName() {
    return formName;
  }

  public void setFormName(String formName) {
    this.formName = formName;
  }

  public String getFormDescription() {
    return formDescription;
  }

  public void setFormDescription(String formDescription) {
    this.formDescription = formDescription;
  }
}
