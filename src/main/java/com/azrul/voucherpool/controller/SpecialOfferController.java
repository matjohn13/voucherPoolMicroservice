package com.azrul.voucherpool.controller;

import com.azrul.voucherpool.exception.ResourceNotFoundException;
import com.azrul.voucherpool.repository.SpecialOfferRepository;
import com.azrul.voucherpool.model.SpecialOffer;
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
public class SpecialOfferController {
    
    @Autowired
    private SpecialOfferRepository specialOfferRepository;
    
    /**
     *
     * @param pageable list all special offer
     * @return All Special offer
     */
    @GetMapping("/specialOffer")
    public Page<SpecialOffer> getSpecialOffer(Pageable pageable) {
        return specialOfferRepository.findAll(pageable);
    }

    /**
     *
     * @param specialOffer New special offer to be created
     * @return SpecialOffer, created offer object
     */
    @PostMapping("/specialOffer")
    public SpecialOffer createSpecialOffer(@Valid @RequestBody SpecialOffer specialOffer) {
        
        return specialOfferRepository.save(specialOffer);
    }

    /**
     *
     * @param specialOfferId special offer id to be created
     * @param specialOfferRequest special offer request to be updated
     * @return SpecialOffer, updated offer object
     */
    @PutMapping("/specialOffer/{specialOfferId}")
    public SpecialOffer updateSpecialOffer(@PathVariable Long specialOfferId,
                                   @Valid @RequestBody SpecialOffer specialOfferRequest) {
        return specialOfferRepository.findById(specialOfferId)
                .map(specialOffer -> {
                    specialOffer.setOfferName(specialOfferRequest.getOfferName());
                    specialOffer.setOfferPercentage(specialOfferRequest.getOfferPercentage());
                    return specialOfferRepository.save(specialOffer);
                }).orElseThrow(() -> new ResourceNotFoundException("SpecialOffer not found with id " + specialOfferId));
    }

    /**
     *
     * @param specialOfferId Special offer to be deleted
     * @return Delete status
     */
    @DeleteMapping("/specialOffer/{specialOfferId}")
    public ResponseEntity<?> deleteSpecialOffer(@PathVariable Long specialOfferId) {
        return specialOfferRepository.findById(specialOfferId)
                .map(specialOffer -> {
                    specialOfferRepository.delete(specialOffer);
                    return ResponseEntity.ok().build();
                }).orElseThrow(() -> new ResourceNotFoundException("SpecialOffer not found with id " + specialOfferId));
    }   
}
