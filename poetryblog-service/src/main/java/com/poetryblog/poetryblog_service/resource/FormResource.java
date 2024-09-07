package com.poetryblog.poetryblog_service.resource;

import com.poetryblog.poetryblog_service.helpers.FormHelper;
import com.poetryblog.poetryblog_service.model.Form;
import com.poetryblog.poetryblog_service.model.Message;
import com.poetryblog.poetryblog_service.model.Topic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/service/forms")
public class FormResource {

    private final FormHelper helper;

    @Autowired
    public FormResource(FormHelper helper) {
        this.helper = helper;
    }

    @PostMapping("/")
    public ResponseEntity createForm(@RequestBody Form form) {
        form = helper.createForm(form);
        if (form != null) {
            return new ResponseEntity<>(form, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(form, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/{formId}")
    public ResponseEntity getForm(@PathVariable("formId") String formId) {
        Form form = helper.getForm(formId);
        if (form != null) {
            return new ResponseEntity(form, HttpStatus.OK);
        } else {
            return new ResponseEntity(form, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/")
    public ResponseEntity getForms() {
        List<Form> forms = helper.getForms();
        return new ResponseEntity(forms, HttpStatus.OK);
    }

    @PutMapping("/{formId}")
    public ResponseEntity updateForm(@PathVariable("formId") String formId, @RequestBody Form form) {
        form = helper.updateForm(formId, form);
        if (form != null){
            return new ResponseEntity<>(form, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(form, HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{formId}")
    public ResponseEntity deleteForm(@PathVariable("formId") String formId) {
        Message message = helper.deleteForm(formId);
        if (message != null){
            return new ResponseEntity<>(message, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }
}
