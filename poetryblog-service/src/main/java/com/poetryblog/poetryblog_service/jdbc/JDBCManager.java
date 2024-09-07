package com.poetryblog.poetryblog_service.jdbc;

import com.poetryblog.poetryblog_service.model.Form;
import com.poetryblog.poetryblog_service.model.Poem;
import com.poetryblog.poetryblog_service.model.Topic;
import com.poetryblog.poetryblog_service.model.Writer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Indexed;
import org.springframework.stereotype.Service;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class JDBCManager {

  @Autowired private JDBCProperties jdbcProperties;

  @Autowired
  public JDBCManager(JDBCProperties jdbcProperties) {
    this.jdbcProperties = jdbcProperties;
    System.out.println("Database URL: " + this.jdbcProperties.getDatabaseUrl());
    System.out.println("Database Name: " + this.jdbcProperties.getDatabaseName());
    System.out.println("Database Username: " + this.jdbcProperties.getUserName());
    System.out.println("Database Password: " + this.jdbcProperties.getPassword());
  }

  public String addWriter(Writer writer) {
    String writerId = "";
    String writerName = writer.getWriterName();
    String writerInfo = writer.getWriterInfo();
    String writerPetname = writer.getWriterPetname();
    int rowsAffected = 0;

    Connection connection = null;
    PreparedStatement statement = null;
    ResultSet resultSet = null;
    try {
      // below two lines are used for connectivity.
      // Class.forName("com.mysql.cj.jdbc.Driver");
      connection =
          DriverManager.getConnection(
              jdbcProperties.getDatabaseUrl() + "/" + jdbcProperties.getDatabaseName(),
              jdbcProperties.getUserName(),
              jdbcProperties.getPassword());

      // mydb is database
      // mydbuser is name of database
      // mydbuser is password of database

      String sql = "insert into writer (writer_name, writer_info, writer_petname) values (?,?,?)";
      statement = connection.prepareStatement(sql, new String[] {"writer_id"});
      statement.setString(1, writerName);
      statement.setString(2, writerInfo);
      statement.setString(3, writerPetname);
      rowsAffected = statement.executeUpdate();
      resultSet = statement.getGeneratedKeys();
      if (resultSet.next()) {
        writerId = String.valueOf(resultSet.getLong(1));
      }
    } catch (Exception exception) {
      System.out.println(exception);
    } finally {
      try {
        if (resultSet != null) {
          resultSet.close();
        }
        if (statement != null) {
          statement.close();
        }
        if (connection != null) {
          connection.close();
        }
      } catch (SQLException exception) {
        System.out.println(exception);
      }
    }
    System.out.println(rowsAffected + " rows affected");
    return writerId;
  }

  public Writer getWriter(String writerId) {
    String writerName = "";
    String writerInfo = "";
    String writerPetname = "";

    Writer writer = null;
    Connection connection = null;
    PreparedStatement statement = null;
    ResultSet resultSet = null;
    try {
      // below two lines are used for connectivity.
      // Class.forName("com.mysql.cj.jdbc.Driver");
      connection =
          DriverManager.getConnection(
              jdbcProperties.getDatabaseUrl() + "/" + jdbcProperties.getDatabaseName(),
              jdbcProperties.getUserName(),
              jdbcProperties.getPassword());

      // mydb is database
      // mydbuser is name of database
      // mydbuser is password of database

      statement = connection.prepareStatement("select * from writer where writer_id=?");
      statement.setString(1, writerId);
      resultSet = statement.executeQuery();
      while (resultSet.next()) {
        writerId = resultSet.getString("writer_id").trim();
        writerName = resultSet.getString("writer_name").trim();
        writerInfo = resultSet.getString("writer_info").trim();
        writerPetname = resultSet.getString("writer_petname").trim();


        System.out.println(
            "writerId : "
                + writerId
                + " writerName : "
                + writerName
                + "writerInfo"
                + writerInfo);
      }
      writer = new Writer(writerId, writerName, writerInfo, writerPetname);
    } catch (Exception exception) {
      System.out.println(exception);
    } finally {
      try {
        if (resultSet != null) {
          resultSet.close();
        }
        if (statement != null) {
          statement.close();
        }
        if (connection != null) {
          connection.close();
        }
      } catch (SQLException exception) {
        System.out.println(exception);
      }
    }
    return writer;
  }

  public Writer updateWriter(Writer writer)
  {
    String writerId = writer.getWriterId();
    String writerName = writer.getWriterName();
    String writerInfo = writer.getWriterInfo();
    String writerPetname = writer.getWriterPetname();
    int rowsAffected = 0;

    Connection connection = null;
    PreparedStatement statement = null;
    try {
      // below two lines are used for connectivity.
      // Class.forName("com.mysql.cj.jdbc.Driver");
      connection =
              DriverManager.getConnection(
                      jdbcProperties.getDatabaseUrl() + "/" + jdbcProperties.getDatabaseName(),
                      jdbcProperties.getUserName(),
                      jdbcProperties.getPassword());

      // mydb is database
      // mydbuser is name of database
      // mydbuser is password of database

      String sql = "update writer set writer_name=?, writer_info=?, writer_petname=? where writer_id=?";
      statement = connection.prepareStatement(sql);
      statement.setString(1, writerName);
      statement.setString(2, writerInfo);
      statement.setString(3, writerPetname);
      statement.setString(4, writerId);
      rowsAffected = statement.executeUpdate();
    } catch (Exception exception) {
      System.out.println(exception);
    } finally {
      try {
        if (statement != null) {
          statement.close();
        }
        if (connection != null) {
          connection.close();
        }
      } catch (SQLException exception) {
        System.out.println(exception);
      }
    }
    System.out.println(rowsAffected + " rows affected");
    return writer;
  }

  public int deleteAll()
  {
    int totalCount = 0;
    totalCount += deleteAllTopics();
    totalCount += deleteAllForms();
    totalCount += deleteAllPoems();
    totalCount += deleteAllWriters();
    return totalCount;
  }

  public int deleteAllPoems()
  {
    Connection connection = null;
    PreparedStatement statement = null;
    int response = 0;
    try {
      // below two lines are used for connectivity.
      // Class.forName("com.mysql.cj.jdbc.Driver");
      connection =
              DriverManager.getConnection(
                      jdbcProperties.getDatabaseUrl() + "/" + jdbcProperties.getDatabaseName(),
                      jdbcProperties.getUserName(),
                      jdbcProperties.getPassword());

      // mydb is database
      // mydbuser is name of database
      // mydbuser is password of database

      statement = connection.prepareStatement("delete from poem");
      response = statement.executeUpdate();

    } catch (Exception exception) {
      System.out.println(exception);
    } finally {
      try {
        if (statement != null) {
          statement.close();
        }
        if (connection != null) {
          connection.close();
        }
      } catch (SQLException exception) {
        System.out.println(exception);
      }
    }
    return response;
  }

  public int deleteAllWriters()
  {
    Connection connection = null;
    PreparedStatement statement = null;
    int response = 0;
    try {
      // below two lines are used for connectivity.
      // Class.forName("com.mysql.cj.jdbc.Driver");
      connection =
              DriverManager.getConnection(
                      jdbcProperties.getDatabaseUrl() + "/" + jdbcProperties.getDatabaseName(),
                      jdbcProperties.getUserName(),
                      jdbcProperties.getPassword());

      // mydb is database
      // mydbuser is name of database
      // mydbuser is password of database

      statement = connection.prepareStatement("delete from writer");
      response = statement.executeUpdate();

    } catch (Exception exception) {
      System.out.println(exception);
    } finally {
      try {
        if (statement != null) {
          statement.close();
        }
        if (connection != null) {
          connection.close();
        }
      } catch (SQLException exception) {
        System.out.println(exception);
      }
    }
    return response;
  }

  public int deleteAllForms()
  {
    Connection connection = null;
    PreparedStatement statement = null;
    int response = 0;
    try {
      // below two lines are used for connectivity.
      // Class.forName("com.mysql.cj.jdbc.Driver");
      connection =
              DriverManager.getConnection(
                      jdbcProperties.getDatabaseUrl() + "/" + jdbcProperties.getDatabaseName(),
                      jdbcProperties.getUserName(),
                      jdbcProperties.getPassword());

      // mydb is database
      // mydbuser is name of database
      // mydbuser is password of database

      statement = connection.prepareStatement("delete from poem_form");
      response = statement.executeUpdate();
      statement = connection.prepareStatement("delete from form");
      response += statement.executeUpdate();

    } catch (Exception exception) {
      System.out.println(exception);
    } finally {
      try {
        if (statement != null) {
          statement.close();
        }
        if (connection != null) {
          connection.close();
        }
      } catch (SQLException exception) {
        System.out.println(exception);
      }
    }
    return response;
  }

  public int deleteAllTopics()
  {
    Connection connection = null;
    PreparedStatement statement = null;
    int response = 0;
    try {
      // below two lines are used for connectivity.
      // Class.forName("com.mysql.cj.jdbc.Driver");
      connection =
              DriverManager.getConnection(
                      jdbcProperties.getDatabaseUrl() + "/" + jdbcProperties.getDatabaseName(),
                      jdbcProperties.getUserName(),
                      jdbcProperties.getPassword());

      // mydb is database
      // mydbuser is name of database
      // mydbuser is password of database

      statement = connection.prepareStatement("delete from poem_topic");
      response = statement.executeUpdate();
      statement = connection.prepareStatement("delete from topic");
      response += statement.executeUpdate();

    } catch (Exception exception) {
      System.out.println(exception);
    } finally {
      try {
        if (statement != null) {
          statement.close();
        }
        if (connection != null) {
          connection.close();
        }
      } catch (SQLException exception) {
        System.out.println(exception);
      }
    }
    return response;
  }

  public Poem updatePoem(Poem poem)
  {
    String poemId = poem.getPoemId();
    String poemName = poem.getPoemName();
    String poemContent = poem.getPoemContent();
    String writerId = poem.getWriterId();
    List<String> formIds = poem.getFormIds();
    List<String> topicIds = poem.getTopicIds();
    int rowsAffected = 0;

    Connection connection = null;
    PreparedStatement statement = null;
    try {
      // below two lines are used for connectivity.
      // Class.forName("com.mysql.cj.jdbc.Driver");
      connection =
              DriverManager.getConnection(
                      jdbcProperties.getDatabaseUrl() + "/" + jdbcProperties.getDatabaseName(),
                      jdbcProperties.getUserName(),
                      jdbcProperties.getPassword());

      // mydb is database
      // mydbuser is name of database
      // mydbuser is password of database

      deleteTopicForPoem(poemId);
      deleteFormForPoem(poemId);
      String sql = "update poem set poem_name=?, poem_content=?, writer_id=? where poem_id=?";
      statement = connection.prepareStatement(sql);
      statement.setString(1, poemName);
      statement.setString(2, poemContent);
      statement.setString(3, writerId);
      statement.setString(4, poemId);
      rowsAffected = statement.executeUpdate();
      addPoemTopic(connection, poemId, topicIds);
      addPoemForm(connection, poemId,formIds);
    } catch (Exception exception) {
      System.out.println(exception);
    } finally {
      try {
        if (statement != null) {
          statement.close();
        }
        if (connection != null) {
          connection.close();
        }
      } catch (SQLException exception) {
        System.out.println(exception);
      }
    }
    System.out.println(rowsAffected + " rows affected");
    return poem;
  }

  public int deleteTopicForPoem(String poemId)
  {
    Connection connection = null;
    PreparedStatement statement = null;
    int response = 0;
    try {
      // below two lines are used for connectivity.
      // Class.forName("com.mysql.cj.jdbc.Driver");
      connection =
              DriverManager.getConnection(
                      jdbcProperties.getDatabaseUrl() + "/" + jdbcProperties.getDatabaseName(),
                      jdbcProperties.getUserName(),
                      jdbcProperties.getPassword());

      // mydb is database
      // mydbuser is name of database
      // mydbuser is password of database

      statement = connection.prepareStatement("delete from poem_topic where poem_id=?");
      statement.setString(1, poemId);
      response = statement.executeUpdate();

    } catch (Exception exception) {
      System.out.println(exception);
    } finally {
      try {
        if (statement != null) {
          statement.close();
        }
        if (connection != null) {
          connection.close();
        }
      } catch (SQLException exception) {
        System.out.println(exception);
      }
    }
    return response;
  }

  public int deleteFormForPoem(String poemId)
  {
    Connection connection = null;
    PreparedStatement statement = null;
    int response = 0;
    try {
      // below two lines are used for connectivity.
      // Class.forName("com.mysql.cj.jdbc.Driver");
      connection =
              DriverManager.getConnection(
                      jdbcProperties.getDatabaseUrl() + "/" + jdbcProperties.getDatabaseName(),
                      jdbcProperties.getUserName(),
                      jdbcProperties.getPassword());

      // mydb is database
      // mydbuser is name of database
      // mydbuser is password of database

      statement = connection.prepareStatement("delete from poem_form where poem_id=?");
      statement.setString(1, poemId);
      response = statement.executeUpdate();

    } catch (Exception exception) {
      System.out.println(exception);
    } finally {
      try {
        if (statement != null) {
          statement.close();
        }
        if (connection != null) {
          connection.close();
        }
      } catch (SQLException exception) {
        System.out.println(exception);
      }
    }
    return response;
  }

  public Topic updateTopic(Topic topic)
  {
    String topicId = topic.getTopicId();
    String topicName = topic.getTopicName();
    int rowsAffected = 0;

    Connection connection = null;
    PreparedStatement statement = null;
    try {
      // below two lines are used for connectivity.
      // Class.forName("com.mysql.cj.jdbc.Driver");
      connection =
              DriverManager.getConnection(
                      jdbcProperties.getDatabaseUrl() + "/" + jdbcProperties.getDatabaseName(),
                      jdbcProperties.getUserName(),
                      jdbcProperties.getPassword());

      // mydb is database
      // mydbuser is name of database
      // mydbuser is password of database

      String sql = "update topic set topic_name=? where topic_id=?";
      statement = connection.prepareStatement(sql);
      statement.setString(1, topicName);
      statement.setString(2, topicId);
      rowsAffected = statement.executeUpdate();
    } catch (Exception exception) {
      System.out.println(exception);
    } finally {
      try {
        if (statement != null) {
          statement.close();
        }
        if (connection != null) {
          connection.close();
        }
      } catch (SQLException exception) {
        System.out.println(exception);
      }
    }
    System.out.println(rowsAffected + " rows affected");
    return topic;
  }

  public Form updateForm(Form form)
  {
    String formId = form.getFormId();
    String formName = form.getFormName();
    String formDescription = form.getFormDescription();
    int rowsAffected = 0;

    Connection connection = null;
    PreparedStatement statement = null;
    try {
      // below two lines are used for connectivity.
      // Class.forName("com.mysql.cj.jdbc.Driver");
      connection =
              DriverManager.getConnection(
                      jdbcProperties.getDatabaseUrl() + "/" + jdbcProperties.getDatabaseName(),
                      jdbcProperties.getUserName(),
                      jdbcProperties.getPassword());

      // mydb is database
      // mydbuser is name of database
      // mydbuser is password of database

      String sql = "update form set form_name=?, form_description=? where form_id=?";
      statement = connection.prepareStatement(sql);
      statement.setString(1, formName);
      statement.setString(2, formDescription);
      statement.setString(3, formId);
      rowsAffected = statement.executeUpdate();
    } catch (Exception exception) {
      System.out.println(exception);
    } finally {
      try {
        if (statement != null) {
          statement.close();
        }
        if (connection != null) {
          connection.close();
        }
      } catch (SQLException exception) {
        System.out.println(exception);
      }
    }
    System.out.println(rowsAffected + " rows affected");
    return form;
  }

  public int deleteWriter(String writerId)
  {
    updatePoemForDelete("writer", writerId);
    Connection connection = null;
    PreparedStatement statement = null;
    int response = 0;
    try {
      // below two lines are used for connectivity.
      // Class.forName("com.mysql.cj.jdbc.Driver");
      connection =
              DriverManager.getConnection(
                      jdbcProperties.getDatabaseUrl() + "/" + jdbcProperties.getDatabaseName(),
                      jdbcProperties.getUserName(),
                      jdbcProperties.getPassword());

      // mydb is database
      // mydbuser is name of database
      // mydbuser is password of database

      statement = connection.prepareStatement("delete from writer where writer_id=?");
      statement.setString(1, writerId);
      response = statement.executeUpdate();

    } catch (Exception exception) {
      System.out.println(exception);
    } finally {
      try {
        if (statement != null) {
          statement.close();
        }
        if (connection != null) {
          connection.close();
        }
      } catch (SQLException exception) {
        System.out.println(exception);
      }
    }
    return response;
  }

  public int deletePoem(String poemId)
  {
    deleteTopicForPoem(poemId);
    deleteFormForPoem(poemId);
    Connection connection = null;
    PreparedStatement statement = null;
    int response = 0;
    try {
      // below two lines are used for connectivity.
      // Class.forName("com.mysql.cj.jdbc.Driver");
      connection =
              DriverManager.getConnection(
                      jdbcProperties.getDatabaseUrl() + "/" + jdbcProperties.getDatabaseName(),
                      jdbcProperties.getUserName(),
                      jdbcProperties.getPassword());

      // mydb is database
      // mydbuser is name of database
      // mydbuser is password of database

      statement = connection.prepareStatement("delete from poem where poem_id=?");
      statement.setString(1, poemId);
      response = statement.executeUpdate();

    } catch (Exception exception) {
      System.out.println(exception);
    } finally {
      try {
        if (statement != null) {
          statement.close();
        }
        if (connection != null) {
          connection.close();
        }
      } catch (SQLException exception) {
        System.out.println(exception);
      }
    }
    return response;
  }

  public int deleteForm(String formId)
  {
    updatePoemForDelete("form", formId);
    Connection connection = null;
    PreparedStatement statement = null;
    int response = 0;
    try {
      // below two lines are used for connectivity.
      // Class.forName("com.mysql.cj.jdbc.Driver");
      connection =
              DriverManager.getConnection(
                      jdbcProperties.getDatabaseUrl() + "/" + jdbcProperties.getDatabaseName(),
                      jdbcProperties.getUserName(),
                      jdbcProperties.getPassword());

      // mydb is database
      // mydbuser is name of database
      // mydbuser is password of database

      statement = connection.prepareStatement("delete from form where form_id=?");
      statement.setString(1, formId);
      response = statement.executeUpdate();

    } catch (Exception exception) {
      System.out.println(exception);
    } finally {
      try {
        if (statement != null) {
          statement.close();
        }
        if (connection != null) {
          connection.close();
        }
      } catch (SQLException exception) {
        System.out.println(exception);
      }
    }
    return response;
  }

  public int deleteTopic(String topicId)
  {
    updatePoemForDelete("topic", topicId);
    Connection connection = null;
    PreparedStatement statement = null;
    int response = 0;
    try {
      // below two lines are used for connectivity.
      // Class.forName("com.mysql.cj.jdbc.Driver");
      connection =
              DriverManager.getConnection(
                      jdbcProperties.getDatabaseUrl() + "/" + jdbcProperties.getDatabaseName(),
                      jdbcProperties.getUserName(),
                      jdbcProperties.getPassword());

      // mydb is database
      // mydbuser is name of database
      // mydbuser is password of database

      statement = connection.prepareStatement("delete from topic where topic_id=?");
      statement.setString(1, topicId);
      response = statement.executeUpdate();

    } catch (Exception exception) {
      System.out.println(exception);
    } finally {
      try {
        if (statement != null) {
          statement.close();
        }
        if (connection != null) {
          connection.close();
        }
      } catch (SQLException exception) {
        System.out.println(exception);
      }
    }
    return response;
  }

  public int updatePoemForDelete(String entity, String value)
  {
    int rowsAffected = 0;
    String defaultValue = "";
    String sql = "";
    if (entity.equals("writer")) {
      defaultValue = "w";
      sql = "update poem set writer_id=? where writer_id=?";
    } else if (entity.equals("topic")) {
      defaultValue = "t";
      sql = "update poem_topic set topic_id=? where topic_id=?";
    } else if (entity.equals("form")) {
      defaultValue = "f";
      sql = "update poem_form set form_id=? where form_id=?";
    }

    Connection connection = null;
    PreparedStatement statement = null;
    try {
      // below two lines are used for connectivity.
      // Class.forName("com.mysql.cj.jdbc.Driver");
      connection =
              DriverManager.getConnection(
                      jdbcProperties.getDatabaseUrl() + "/" + jdbcProperties.getDatabaseName(),
                      jdbcProperties.getUserName(),
                      jdbcProperties.getPassword());

      // mydb is database
      // mydbuser is name of database
      // mydbuser is password of database

      statement = connection.prepareStatement(sql);
      statement.setString(1, defaultValue);
      statement.setString(2, value);
      rowsAffected = statement.executeUpdate();
    } catch (Exception exception) {
      System.out.println(exception);
    } finally {
      try {
        if (statement != null) {
          statement.close();
        }
        if (connection != null) {
          connection.close();
        }
      } catch (SQLException exception) {
        System.out.println(exception);
      }
    }
    System.out.println(rowsAffected + " rows affected");
    return rowsAffected;
  }

  public String addForm(Form form) {
    String formId = "";
    String formName = form.getFormName();
    String formDescription = form.getFormDescription();
    int rowsAffected = 0;

    Connection connection = null;
    PreparedStatement statement = null;
    ResultSet resultSet = null;
    try {
      // below two lines are used for connectivity.
      // Class.forName("com.mysql.cj.jdbc.Driver");
      connection =
              DriverManager.getConnection(
                      jdbcProperties.getDatabaseUrl() + "/" + jdbcProperties.getDatabaseName(),
                      jdbcProperties.getUserName(),
                      jdbcProperties.getPassword());

      // mydb is database
      // mydbuser is name of database
      // mydbuser is password of database

      String sql = "insert into form (form_name, form_description) values (?,?)";
      statement = connection.prepareStatement(sql, new String[] {"form_id"});
      statement.setString(1, formName);
      statement.setString(2, formDescription);
      rowsAffected = statement.executeUpdate();
      resultSet = statement.getGeneratedKeys();
      if (resultSet.next()) {
        formId = String.valueOf(resultSet.getLong(1));
      }
    } catch (Exception exception) {
      System.out.println(exception);
    } finally {
      try {
        if (resultSet != null) {
          resultSet.close();
        }
        if (statement != null) {
          statement.close();
        }
        if (connection != null) {
          connection.close();
        }
      } catch (SQLException exception) {
        System.out.println(exception);
      }
    }
    System.out.println(rowsAffected + " rows affected");
    return formId;
  }

  public Form getForm(String formId) {
    String formName = "";
    String formDescription = "";

    Form form = null;
    Connection connection = null;
    PreparedStatement statement = null;
    ResultSet resultSet = null;
    try {
      // below two lines are used for connectivity.
      // Class.forName("com.mysql.cj.jdbc.Driver");
      connection =
              DriverManager.getConnection(
                      jdbcProperties.getDatabaseUrl() + "/" + jdbcProperties.getDatabaseName(),
                      jdbcProperties.getUserName(),
                      jdbcProperties.getPassword());

      // mydb is database
      // mydbuser is name of database
      // mydbuser is password of database

      statement = connection.prepareStatement("select * from form where form_id=?");
      statement.setString(1, formId);
      resultSet = statement.executeQuery();
      while (resultSet.next()) {
        formId = resultSet.getString("form_id").trim();
        formName = resultSet.getString("form_name").trim();
        formDescription = resultSet.getString("form_description").trim();

        System.out.println(
                "formId : "
                        + formId
                        + " formName : "
                        + formName
                        + "formDescription"
                        + formDescription);
      }
      form = new Form(formId, formName, formDescription);
    } catch (Exception exception) {
      System.out.println(exception);
    } finally {
      try {
        if (resultSet != null) {
          resultSet.close();
        }
        if (statement != null) {
          statement.close();
        }
        if (connection != null) {
          connection.close();
        }
      } catch (SQLException exception) {
        System.out.println(exception);
      }
    }
    return form;
  }

  public List<Form> getForms() {
    String formId = "";
    String formName = "";
    String formDescription = "";
    List<Form> forms = new ArrayList<>();

    Form form = null;
    Connection connection = null;
    PreparedStatement statement = null;
    ResultSet resultSet = null;
    try {
      // below two lines are used for connectivity.
      // Class.forName("com.mysql.cj.jdbc.Driver");
      connection =
              DriverManager.getConnection(
                      jdbcProperties.getDatabaseUrl() + "/" + jdbcProperties.getDatabaseName(),
                      jdbcProperties.getUserName(),
                      jdbcProperties.getPassword());

      // mydb is database
      // mydbuser is name of database
      // mydbuser is password of database

      statement = connection.prepareStatement("select * from form");
      resultSet = statement.executeQuery();
      while (resultSet.next()) {
        formId = resultSet.getString("form_id").trim();
        formName = resultSet.getString("form_name").trim();
        formDescription = resultSet.getString("form_description").trim();
        form = new Form(formId, formName, formDescription);
        forms.add(form);

        System.out.println(
                "formId : "
                        + formId
                        + " formName : "
                        + formName
                        + "formDescription"
                        + formDescription);
      }
    } catch (Exception exception) {
      System.out.println(exception);
    } finally {
      try {
        if (resultSet != null) {
          resultSet.close();
        }
        if (statement != null) {
          statement.close();
        }
        if (connection != null) {
          connection.close();
        }
      } catch (SQLException exception) {
        System.out.println(exception);
      }
    }
    return forms;
  }

  public String addTopic(Topic topic) {
    String topicId = "";
    String topicName = topic.getTopicName();

    int rowsAffected = 0;

    Connection connection = null;
    PreparedStatement statement = null;
    ResultSet resultSet = null;
    try {
      // below two lines are used for connectivity.
      // Class.forName("com.mysql.cj.jdbc.Driver");
      connection =
              DriverManager.getConnection(
                      jdbcProperties.getDatabaseUrl() + "/" + jdbcProperties.getDatabaseName(),
                      jdbcProperties.getUserName(),
                      jdbcProperties.getPassword());

      // mydb is database
      // mydbuser is name of database
      // mydbuser is password of database

      String sql = "insert into topic (topic_name) values (?)";
      statement = connection.prepareStatement(sql, new String[] {"topic_id"});
      statement.setString(1, topicName);
      rowsAffected = statement.executeUpdate();
      resultSet = statement.getGeneratedKeys();
      if (resultSet.next()) {
        topicId = String.valueOf(resultSet.getLong(1));
      }
    } catch (Exception exception) {
      System.out.println(exception);
    } finally {
      try {
        if (resultSet != null) {
          resultSet.close();
        }
        if (statement != null) {
          statement.close();
        }
        if (connection != null) {
          connection.close();
        }
      } catch (SQLException exception) {
        System.out.println(exception);
      }
    }
    System.out.println(rowsAffected + " rows affected");
    return topicId;
  }

  public List<Topic> getTopics() {
    String topicId = "";
    String topicName = "";
    List<Topic> topics = new ArrayList<>();

    Topic topic = null;
    Connection connection = null;
    PreparedStatement statement = null;
    ResultSet resultSet = null;
    try {
      // below two lines are used for connectivity.
      // Class.forName("com.mysql.cj.jdbc.Driver");
      connection =
              DriverManager.getConnection(
                      jdbcProperties.getDatabaseUrl() + "/" + jdbcProperties.getDatabaseName(),
                      jdbcProperties.getUserName(),
                      jdbcProperties.getPassword());

      // mydb is database
      // mydbuser is name of database
      // mydbuser is password of database

      statement = connection.prepareStatement("select * from topic");
      resultSet = statement.executeQuery();
      while (resultSet.next()) {
        topicId = resultSet.getString("topic_id").trim();
        topicName = resultSet.getString("topic_name").trim();
        topic = new Topic(topicId, topicName);
        topics.add(topic);

        System.out.println(
                "topicId : "
                        + topicId
                        + " topicName : "
                        + topicName);
      }
    } catch (Exception exception) {
      System.out.println(exception);
    } finally {
      try {
        if (resultSet != null) {
          resultSet.close();
        }
        if (statement != null) {
          statement.close();
        }
        if (connection != null) {
          connection.close();
        }
      } catch (SQLException exception) {
        System.out.println(exception);
      }
    }
    return topics;
  }

  public boolean validateId(String entity, String id) {

    boolean result = false;
    Connection connection = null;
    PreparedStatement statement = null;
    ResultSet resultSet = null;
    try {
      // below two lines are used for connectivity.
      // Class.forName("com.mysql.cj.jdbc.Driver");
      connection =
              DriverManager.getConnection(
                      jdbcProperties.getDatabaseUrl() + "/" + jdbcProperties.getDatabaseName(),
                      jdbcProperties.getUserName(),
                      jdbcProperties.getPassword());

      // mydb is database
      // mydbuser is name of database
      // mydbuser is password of database

      String sql = "select exists(select 1 from " + entity + " where " + entity + "_id='" + id + "')";
      statement = connection.prepareStatement(sql);
      System.out.println(sql);
      resultSet = statement.executeQuery();
      while (resultSet.next()) {
        result = resultSet.getBoolean("exists");
        System.out.println("printing result of validation: " + result);
      }
    } catch (Exception exception) {
      System.out.println(exception);
    } finally {
      try {
        if (resultSet != null) {
          resultSet.close();
        }
        if (statement != null) {
          statement.close();
        }
        if (connection != null) {
          connection.close();
        }
      } catch (SQLException exception) {
        System.out.println(exception);
      }
    }
    return result;
  }

  public Topic getTopic(String topicId) {
    String topicName = "";

    Topic topic = null;
    Connection connection = null;
    PreparedStatement statement = null;
    ResultSet resultSet = null;
    try {
      // below two lines are used for connectivity.
      // Class.forName("com.mysql.cj.jdbc.Driver");
      connection =
              DriverManager.getConnection(
                      jdbcProperties.getDatabaseUrl() + "/" + jdbcProperties.getDatabaseName(),
                      jdbcProperties.getUserName(),
                      jdbcProperties.getPassword());

      // mydb is database
      // mydbuser is name of database
      // mydbuser is password of database

      statement = connection.prepareStatement("select * from topic where topic_id=?");
      statement.setString(1, topicId);
      resultSet = statement.executeQuery();
      while (resultSet.next()) {
        topicId = resultSet.getString("topic_id").trim();
        topicName = resultSet.getString("topic_name").trim();

        System.out.println(
                "topicId : "
                        + topicId
                        + " topicName : "
                        + topicName);
      }
      topic = new Topic(topicId, topicName);
    } catch (Exception exception) {
      System.out.println(exception);
    } finally {
      try {
        if (resultSet != null) {
          resultSet.close();
        }
        if (statement != null) {
          statement.close();
        }
        if (connection != null) {
          connection.close();
        }
      } catch (SQLException exception) {
        System.out.println(exception);
      }
    }
    return topic;
  }

  public String addPoem(Poem poem) {
    String poemId = "";
    String poemName = poem.getPoemName();
    String poemContent = poem.getPoemContent();
    String writerId = poem.getWriterId();
    List<String> formIds = poem.getFormIds();
    List<String> topicIds = poem.getTopicIds();
    int rowsAffected = 0;

    Connection connection = null;
    PreparedStatement statement = null;
    ResultSet resultSet = null;
    try {
      // below two lines are used for connectivity.
      // Class.forName("com.mysql.cj.jdbc.Driver");
      connection =
              DriverManager.getConnection(
                      jdbcProperties.getDatabaseUrl() + "/" + jdbcProperties.getDatabaseName(),
                      jdbcProperties.getUserName(),
                      jdbcProperties.getPassword());

      // mydb is database
      // mydbuser is name of database
      // mydbuser is password of database

      String sql = "insert into poem (poem_name, poem_content, writer_id) values (?,?,?)";
      statement = connection.prepareStatement(sql, new String[] {"poem_id"});
      statement.setString(1, poemName);
      statement.setString(2, poemContent);
      statement.setString(3, writerId);
      rowsAffected = statement.executeUpdate();
      resultSet = statement.getGeneratedKeys();
      if (resultSet.next()) {
        poemId = String.valueOf(resultSet.getLong(1));
      }

      System.out.println(poemId);
      addPoemForm(connection, poemId, formIds);
      addPoemTopic(connection, poemId, topicIds);
    } catch (Exception exception) {
      System.out.println(exception);
    } finally {
      try {
        if (resultSet != null) {
          resultSet.close();
        }
        if (statement != null) {
          statement.close();
        }
        if (connection != null) {
          connection.close();
        }
      } catch (SQLException exception) {
        System.out.println(exception);
      }
    }
    System.out.println(rowsAffected + " rows affected");
    return poemId;
  }

  public Poem getPoem(String poemId) {
    String poemName = "";
    String poemContent = "";
    String writerId = "";
    List<String> formIds = null;
    List<String> topicIds = null;
    Poem poem = null;
    Connection connection = null;
    PreparedStatement statement = null;
    ResultSet resultSet = null;
    try {
      // below two lines are used for connectivity.
      // Class.forName("com.mysql.cj.jdbc.Driver");
      connection =
              DriverManager.getConnection(
                      jdbcProperties.getDatabaseUrl() + "/" + jdbcProperties.getDatabaseName(),
                      jdbcProperties.getUserName(),
                      jdbcProperties.getPassword());

      // mydb is database
      // mydbuser is name of database
      // mydbuser is password of database

      statement = connection.prepareStatement("select * from poem where poem_id=?");
      statement.setString(1, poemId);
      resultSet = statement.executeQuery();
      while (resultSet.next()) {
        poemId = resultSet.getString("poem_id").trim();
        poemName = resultSet.getString("poem_name").trim();
        poemContent = resultSet.getString("poem_content").trim();
        writerId = resultSet.getString("writer_id");

        System.out.println("printing poem info");
        System.out.println(
                "poemId : "
                        + poemId
                        + " poemName : "
                        + poemName
                        + "poemContent"
                        + poemContent
                        + "writerId"
                        + writerId);
      }
      formIds = getPoemForm(connection, poemId);
      System.out.println(formIds.toString());
      topicIds = getPoemTopic(connection, poemId);
      System.out.println(topicIds.toString());
      poem = new Poem(poemId, poemName, poemContent, writerId, formIds, topicIds);
    } catch (Exception exception) {
      System.out.println(exception);
    } finally {
      try {
        if (resultSet != null) {
          resultSet.close();
        }
        if (statement != null) {
          statement.close();
        }
        if (connection != null) {
          connection.close();
        }
      } catch (SQLException exception) {
        System.out.println(exception);
      }
    }
    return poem;
  }

  public List<Poem> getPoems() {
    String poemId = "";
    String poemName = "";
    String poemContent = "";
    String writerId = "";
    List<String> formIds = null;
    List<String> topicIds = null;
    List<Poem> poems = new ArrayList<>();
    Poem poem = null;
    Connection connection = null;
    PreparedStatement statement = null;
    ResultSet resultSet = null;
    try {
      // below two lines are used for connectivity.
      // Class.forName("com.mysql.cj.jdbc.Driver");
      connection =
              DriverManager.getConnection(
                      jdbcProperties.getDatabaseUrl() + "/" + jdbcProperties.getDatabaseName(),
                      jdbcProperties.getUserName(),
                      jdbcProperties.getPassword());

      // mydb is database
      // mydbuser is name of database
      // mydbuser is password of database

      statement = connection.prepareStatement("select * from poem");
      resultSet = statement.executeQuery();
      while (resultSet.next()) {
        poemId = resultSet.getString("poem_id").trim();
        poemName = resultSet.getString("poem_name").trim();
        poemContent = resultSet.getString("poem_content").trim();
        writerId = resultSet.getString("writer_id");

        System.out.println("printing poem info");
        System.out.println(
                "poemId : "
                        + poemId
                        + " poemName : "
                        + poemName
                        + "poemContent"
                        + poemContent
                        + "writerId"
                        + writerId);

        formIds = getPoemForm(connection, poemId);
        System.out.println(formIds.toString());
        topicIds = getPoemTopic(connection, poemId);
        System.out.println(topicIds.toString());
        poem = new Poem(poemId, poemName, poemContent, writerId, formIds, topicIds);
        poems.add(poem);
      }

    } catch (Exception exception) {
      System.out.println(exception);
    } finally {
      try {
        if (resultSet != null) {
          resultSet.close();
        }
        if (statement != null) {
          statement.close();
        }
        if (connection != null) {
          connection.close();
        }
      } catch (SQLException exception) {
        System.out.println(exception);
      }
    }
    return poems;
  }

  public List<Poem> getPoemsForWriters(List<String> writerIds) {
    String poemId = "";
    String poemName = "";
    String poemContent = "";
    String writerId = "";
    List<String> formIds = null;
    List<String> topicIds = null;
    List<Poem> poems = new ArrayList<>();
    Poem poem = null;
    Connection connection = null;
    PreparedStatement statement = null;
    ResultSet resultSet = null;
    try {
      // below two lines are used for connectivity.
      // Class.forName("com.mysql.cj.jdbc.Driver");
      connection =
              DriverManager.getConnection(
                      jdbcProperties.getDatabaseUrl() + "/" + jdbcProperties.getDatabaseName(),
                      jdbcProperties.getUserName(),
                      jdbcProperties.getPassword());

      // mydb is database
      // mydbuser is name of database
      // mydbuser is password of database

      String sql = "select * from poem where writer_id in (";
      for(int i=0; i< writerIds.size(); i++)
      {
        if (i == writerIds.size()-1)
        {
          sql = sql + "'" + writerIds.get(i) + "')";
        }
        else
        {
          sql = sql + "'" + writerIds.get(i) + "',";
        }
      }
      System.out.println("printing sql for get_poems_for_writers");
      System.out.println(sql);
      statement = connection.prepareStatement(sql);
      resultSet = statement.executeQuery();
      while (resultSet.next()) {
        poemId = resultSet.getString("poem_id").trim();
        poemName = resultSet.getString("poem_name").trim();
        poemContent = resultSet.getString("poem_content").trim();
        writerId = resultSet.getString("writer_id");

        System.out.println("printing poem info");
        System.out.println(
                "poemId : "
                        + poemId
                        + " poemName : "
                        + poemName
                        + "poemContent"
                        + poemContent
                        + "writerId"
                        + writerId);

        formIds = getPoemForm(connection, poemId);
        System.out.println(formIds.toString());
        topicIds = getPoemTopic(connection, poemId);
        System.out.println(topicIds.toString());
        poem = new Poem(poemId, poemName, poemContent, writerId, formIds, topicIds);
        poems.add(poem);
      }

    } catch (Exception exception) {
      System.out.println(exception);
    } finally {
      try {
        if (resultSet != null) {
          resultSet.close();
        }
        if (statement != null) {
          statement.close();
        }
        if (connection != null) {
          connection.close();
        }
      } catch (SQLException exception) {
        System.out.println(exception);
      }
    }
    return poems;
  }

  private void addPoemForm(Connection connection, String poemId, List<String> formIds) {
    String sql = "insert into poem_form (poem_id, form_id) values (?,?)";
    PreparedStatement statement = null;
    try{
      statement = connection.prepareStatement(sql);
      for(int i = 0; i < formIds.size(); i++){
        statement.setString(1, poemId);
        statement.setString(2, formIds.get(i));
        statement.addBatch();
      }
      statement.executeBatch();
    } catch (SQLException exception){
      System.out.println(exception);
    } finally {
      try{
        if (statement != null) {
          statement.close();
        }
      } catch (SQLException exception) {
        System.out.println(exception);
      }
    }
  }

  private void addPoemTopic(Connection connection, String poemId, List<String> topicIds) {
    String sql = "insert into poem_topic (poem_id, topic_id) values (?,?)";
    PreparedStatement statement = null;
    try{
      statement = connection.prepareStatement(sql);
      for(int i = 0; i < topicIds.size(); i++){
        statement.setString(1, poemId);
        statement.setString(2, topicIds.get(i));
        statement.addBatch();
      }
      statement.executeBatch();
    } catch (SQLException exception){
      System.out.println(exception);
    } finally {
      try{
        if (statement != null) {
          statement.close();
        }
      } catch (SQLException exception) {
        System.out.println(exception);
      }
    }
  }

  private List<String> getPoemForm(Connection connection, String poemId) {
    List<String> formIds = new ArrayList<>();
    String sql = "select form_id from poem_form where poem_id='" + poemId + "'";
    PreparedStatement statement = null;
    ResultSet resultSet = null;
    try{
      statement = connection.prepareStatement(sql);
      resultSet = statement.executeQuery();
      while(resultSet.next()){
        System.out.println("printing form");
        System.out.println();
        formIds.add(resultSet.getString("form_id"));
      }
    }catch (SQLException exception){
      System.out.println(exception);
    }finally {
      try{
        if (resultSet != null) {
          resultSet.close();
        }
        if (statement != null) {
          statement.close();
        }
      } catch (SQLException exception){
        System.out.println(exception);
      }
    }
    return formIds;
  }

  private List<String> getPoemTopic(Connection connection, String poemId){
    List<String> topicIds = new ArrayList<>();
    String sql = "select topic_id from poem_topic where poem_id='" + poemId + "'";
    PreparedStatement statement = null;
    ResultSet resultSet = null;
    try{
      statement = connection.prepareStatement(sql);
      resultSet = statement.executeQuery();
      while(resultSet.next()){
        topicIds.add(resultSet.getString("topic_id"));
      }
    }catch (SQLException exception){
      System.out.println(exception);
    }finally {
      try{
        if (resultSet != null) {
          resultSet.close();
        }
        if (statement != null) {
          statement.close();
        }
      } catch (SQLException exception){
        System.out.println(exception);
      }
    }
    return topicIds;
  }


//  //validate user with password
//  public boolean validateId(String creatorId, String eventId) {
//    int count = 0;
//    System.out.println("validating creatorId: " + creatorId);
//    System.out.println("validating eventId: " + eventId);
//    Connection connection = null;
//    PreparedStatement statement = null;
//    ResultSet resultSet = null;
//    try {
//      // below two lines are used for connectivity.
//      // Class.forName("com.mysql.cj.jdbc.Driver");
//      connection =
//          DriverManager.getConnection(
//              jdbcProperties.getDatabaseUrl() + "/" + jdbcProperties.getDatabaseName(),
//              jdbcProperties.getUserName(),
//              jdbcProperties.getPassword());
//
//      // mydb is database
//      // mydbuser is name of database
//      // mydbuser is password of database
//
//      if (creatorId != null && eventId == null) {
//        String validateCreatorQuery =
//            "select count(*) from creator where creator_id=\'" + creatorId + "\'";
//        System.out.println(validateCreatorQuery);
//        statement = connection.prepareStatement(validateCreatorQuery);
//        resultSet = statement.executeQuery();
//        while (resultSet.next()) {
//          count = resultSet.getInt(1);
//        }
//        System.out.println("Count: " + count);
//      } else if (creatorId != null && eventId != null) {
//        String validateCreatorWithEventQuery =
//            "select count(*) from event where creator_id=\'"
//                + creatorId
//                + "\' and event_id=\'"
//                + eventId
//                + "\'";
//        System.out.println(validateCreatorWithEventQuery);
//        statement = connection.prepareStatement(validateCreatorWithEventQuery);
//        resultSet = statement.executeQuery();
//        while (resultSet.next()) {
//          count = resultSet.getInt(1);
//        }
//        System.out.println("Count: " + count);
//      }
//    } catch (SQLException exception) {
//      System.out.println(exception.getStackTrace());
//    } finally {
//      try {
//        if (resultSet != null) {
//          resultSet.close();
//        }
//        if (statement != null) {
//          statement.close();
//        }
//        if (connection != null) {
//          connection.close();
//        }
//      } catch (SQLException exception) {
//        System.out.println(exception);
//      }
//    }
//    if (count > 0) {
//      return true;
//    } else {
//      return false;
//    }
//  }

}
