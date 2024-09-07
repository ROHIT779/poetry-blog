package com.poetryblog.poetryblog_service.helpers;

import com.poetryblog.poetryblog_service.jdbc.JDBCManager;
import com.poetryblog.poetryblog_service.model.Message;
import com.poetryblog.poetryblog_service.model.Writer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class WriterHelper {
  private JDBCManager jdbcManager;

  @Autowired
  public WriterHelper(JDBCManager jdbcManager) {
    this.jdbcManager = jdbcManager;
  }

  public Writer createWriter(Writer writer) {
    writer.setWriterId(jdbcManager.addWriter(writer));
    return writer;
  }

  public Writer getWriter(String writerId) {
    if (jdbcManager.validateId("writer", writerId)) {
      return jdbcManager.getWriter(writerId);
    } else {
      return null;
    }
  }

  public Writer updateWriter(String writerId, Writer writer)
  {
    if (jdbcManager.validateId("writer", writerId)) {
      writer.setWriterId(writerId);
      return jdbcManager.updateWriter(writer);
    } else {
      return null;
    }
  }

  public Message deleteWriter(String writerId)
  {
    if (jdbcManager.validateId("writer", writerId)) {
      int count = jdbcManager.deleteWriter(writerId);
      System.out.println("Delete count: " + count);
      String message = "writerId " + writerId + " is deleted from Database";
      String notes = "Call " + "GET /service/writers/" + writerId + " to verify";
      return new Message(message, notes);
    } else {
      return null;
    }
  }
}
