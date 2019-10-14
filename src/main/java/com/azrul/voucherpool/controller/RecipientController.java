package com.azrul.voucherpool.controller;

import com.azrul.voucherpool.exception.ResourceNotFoundException;
import com.azrul.voucherpool.repository.RecipientRepository;
import com.azrul.voucherpool.model.Recipient;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RecipientController {
    
    @Autowired
    private RecipientRepository recipientRepository;
    
    /**
     *
     * @param pageable to list all recipients
     * @return All recipient
     */
    @GetMapping("/recipient")
    public Page<Recipient> getRecipient(Pageable pageable) {
        return recipientRepository.findAll(pageable);
    }

    /**
     *
     * @param recipient create new recipient
     * @return Recipient, created recipient object
     */
    @PostMapping("/recipient")
    public Recipient createRecipient(@Valid @RequestBody Recipient recipient) {
        return recipientRepository.save(recipient);
    }

    /**
     *
     * @param recipientId updated recipient id
     * @param recipientRequest updated recipient request
     * @return Recipient, updated recipient object
     */
    @PutMapping("/recipient/{recipientId}")
    public Recipient updateRecipient(@PathVariable Long recipientId,
                                   @Valid @RequestBody Recipient recipientRequest) {
        return recipientRepository.findById(recipientId)
                .map(recipient -> {
                    recipient.setName(recipientRequest.getName());
                    recipient.setEmail(recipientRequest.getEmail());
                    return recipientRepository.save(recipient);
                }).orElseThrow(() -> new ResourceNotFoundException("Recipient not found with id " + recipientId));
    }

    /**
     *
     * @param recipientId deleted recipient
     * @return Delete status
     */
    @DeleteMapping("/recipient/{recipientId}")
    public ResponseEntity<?> deleteRecipient(@PathVariable Long recipientId) {
        return recipientRepository.findById(recipientId)
                .map(recipient -> {
                    recipientRepository.delete(recipient);
                    return ResponseEntity.ok().build();
                }).orElseThrow(() -> new ResourceNotFoundException("Recipient not found with id " + recipientId));
    }
}
