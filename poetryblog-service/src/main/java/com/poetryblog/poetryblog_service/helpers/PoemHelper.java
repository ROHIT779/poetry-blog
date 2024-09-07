package com.poetryblog.poetryblog_service.helpers;

import com.poetryblog.poetryblog_service.jdbc.JDBCManager;
import com.poetryblog.poetryblog_service.model.Form;
import com.poetryblog.poetryblog_service.model.Message;
import com.poetryblog.poetryblog_service.model.Poem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class PoemHelper {
  private JDBCManager jdbcManager;

  @Autowired
  public PoemHelper(JDBCManager jdbcManager) {
    this.jdbcManager = jdbcManager;
  }

  public Poem createPoem(Poem poem) {
    String writerId = poem.getWriterId();
    List<String> formIds = poem.getFormIds();
    List<String> topicIds = poem.getTopicIds();
    if (jdbcManager.validateId("writer", writerId)) {
      int validFormIds = 0;
      int validTopicIds = 0;
      for (String formId : formIds) {
        if (jdbcManager.validateId("form", formId)) {
          validFormIds += 1;
        }
      }
      for (String topicId : topicIds) {
        if (jdbcManager.validateId("topic", topicId)) {
          validTopicIds += 1;
        }
      }
      if ((formIds.size() == validFormIds) && (topicIds.size() == validTopicIds)) {
        poem.setPoemId(jdbcManager.addPoem(poem));
        return poem;
      }
    }
    return null;
  }

  public Poem getPoem(String poemId) {
    if (jdbcManager.validateId("poem", poemId)) {
      return jdbcManager.getPoem(poemId);
    } else {
      return null;
    }
  }

  public List<Poem> getPoemsFiltered(List<String> writerIds, List<String> formIds, List<String> topicIds)
  {
    List<Poem> poemsFiltered = new ArrayList<>();
    if (writerIds.isEmpty() && formIds.isEmpty() && topicIds.isEmpty()){
      poemsFiltered = jdbcManager.getPoems();
      return poemsFiltered;
    } else {
      int validWriterIds = 0;
      int validFormIds = 0;
      int validTopicIds = 0;
      for (String writerId : writerIds) {
        if (jdbcManager.validateId("writer", writerId)) {
          validWriterIds += 1;
        }
      }
      for (String formId : formIds) {
        if (jdbcManager.validateId("form", formId)) {
          validFormIds += 1;
        }
      }
      for (String topicId : topicIds) {
        if (jdbcManager.validateId("topic", topicId)) {
          validTopicIds += 1;
        }
      }
      if ((writerIds.size() == validWriterIds) && (formIds.size() == validFormIds) && (topicIds.size() == validTopicIds)) {
        poemsFiltered = jdbcManager.getPoems();
        if (!writerIds.isEmpty())
        {
          poemsFiltered = poemsFiltered.stream().filter(poem->writerIds.contains(poem.getWriterId())).toList();
        }
        if (!formIds.isEmpty())
        {
          List<Poem> poemsFilteredByForms = new ArrayList<>();
          for (Poem poem : poemsFiltered)
          {
            List<String> poemFormIds = poem.getFormIds();
            for (String formId : formIds)
            {
              if (poemFormIds.contains(formId))
              {
                poemsFilteredByForms.add(poem);
                break;
              }
            }
          }
          poemsFiltered = poemsFilteredByForms;
        }

        if (!topicIds.isEmpty())
        {
          List<Poem> poemsFilteredByTopics = new ArrayList<>();
          for (Poem poem : poemsFiltered)
          {
            List<String> poemTopicIds = poem.getTopicIds();
            for (String topicId : topicIds)
            {
              if (poemTopicIds.contains(topicId))
              {
                poemsFilteredByTopics.add(poem);
                break;
              }
            }
          }
          poemsFiltered = poemsFilteredByTopics;
        }
        return poemsFiltered;
      } else {
        return null;
      }
    }
  }

  public Poem updatePoem(String poemId, Poem poem)
  {
    String writerId = poem.getWriterId();
    if (jdbcManager.validateId("writer", writerId)) {
      List<String> formIds = poem.getFormIds();
      List<String> topicIds = poem.getTopicIds();
      int validatedFormIds = 0;
      int validatedTopicIds = 0;
      if (jdbcManager.validateId("poem", poemId)) {
        for (String formId : formIds) {
          if (jdbcManager.validateId("form", formId)) {
            validatedFormIds += 1;
          }
        }
        for (String topicId : topicIds) {
          if (jdbcManager.validateId("topic", topicId)) {
            validatedTopicIds += 1;
          }
        }
        if ((formIds.size() == validatedFormIds) && (topicIds.size() == validatedTopicIds)) {
          poem.setPoemId(poemId);
          return jdbcManager.updatePoem(poem);
        }
      }
    }
    return null;
  }

  public Message deletePoem(String poemId)
  {
    if (jdbcManager.validateId("poem", poemId)) {
      int count = jdbcManager.deletePoem(poemId);
      System.out.println("Delete count: "+ count);
      String message = "poemId " + poemId + " is deleted from Database";
      String notes = "Call " + "GET /service/poems/" + poemId + " to verify";
      return new Message(message, notes);
    } else {
      return null;
    }
  }

  public Message deletePoems()
  {
    int count = jdbcManager.deleteAll();
    System.out.println("Delete count: "+ count);
    String message = count + " rows are deleted from Database";
    String notes = "Call " + "GET /service/poems/ or /service/forms/ or /service/topics to verify";
    return new Message(message, notes);
  }

}
