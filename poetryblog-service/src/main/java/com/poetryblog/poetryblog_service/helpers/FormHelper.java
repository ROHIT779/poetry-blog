package com.poetryblog.poetryblog_service.helpers;

import com.poetryblog.poetryblog_service.jdbc.JDBCManager;
import com.poetryblog.poetryblog_service.model.Form;
import com.poetryblog.poetryblog_service.model.Message;
import com.poetryblog.poetryblog_service.model.Writer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class FormHelper {
  private JDBCManager jdbcManager;

  @Autowired
  public FormHelper(JDBCManager jdbcManager) {
    this.jdbcManager = jdbcManager;
  }

  public Form createForm(Form form) {
    form.setFormId(jdbcManager.addForm(form));
    return form;
  }

  public Form getForm(String formId) {
    if (jdbcManager.validateId("form", formId)){
      return jdbcManager.getForm(formId);
    } else {
      return null;
    }
  }

  public List<Form> getForms() {
    return jdbcManager.getForms();
  }

  public Form updateForm(String formId, Form form)
  {
    if (jdbcManager.validateId("form", formId)) {
      form.setFormId(formId);
      return jdbcManager.updateForm(form);
    } else {
      return null;
    }
  }

  public Message deleteForm(String formId)
  {
    if (jdbcManager.validateId("form", formId)){
      int count = jdbcManager.deleteForm(formId);
      System.out.println("Delete count: "+ count);
      String message = "formId " + formId + " is deleted from Database";
      String notes = "Call " + "GET /service/forms/" + formId + " to verify";
      return new Message(message, notes);
    } else {
      return null;
    }
  }
}
