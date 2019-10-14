package com.azrul.voucherpool.controller;

import com.azrul.voucherpool.exception.ResourceNotFoundException;
import com.azrul.voucherpool.repository.VoucherRepository;
import com.azrul.voucherpool.model.VoucherCode;
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
public class VoucherController {
    
    @Autowired
    private VoucherRepository voucherRepository;
    
    /**
     *
     * @param pageable List all voucher
     * @return All voucher
     */
    @GetMapping("/voucherCode")
    public Page<VoucherCode> getVoucher(Pageable pageable) {
        return voucherRepository.findAll(pageable);
    }
    
    /**
     *
     * @param voucherCode new voucher to be created
     * @return VoucherCode, new created voucher code
     */
    @PostMapping("/voucherCode")
    public VoucherCode createVoucher(@Valid @RequestBody VoucherCode voucherCode) {
        return voucherRepository.save(voucherCode);
    }

    /**
     *
     * @param voucherCodeId Voucher code to be updated
     * @param voucherCodeRequest Voucher code request to be updated
     * @return VoucherCode, updated voucher code
     */
    @PutMapping("/voucherCode/{voucherCodeId}")
    public VoucherCode updateVoucher(@PathVariable Long voucherCodeId,
                                   @Valid @RequestBody VoucherCode voucherCodeRequest) {
        return voucherRepository.findById(voucherCodeId)
                .map(voucherCode -> {
                    voucherCode.setCode(voucherCodeRequest.getCode());
                    voucherCode.setRecipient(voucherCodeRequest.getRecipient());
                    voucherCode.setSpecialOffer(voucherCodeRequest.getSpecialOffer());
                    voucherCode.setRedeemDate(voucherCodeRequest.getRedeemDate());
                    voucherCode.setExpirationDate(voucherCodeRequest.getExpirationDate());
                    return voucherRepository.save(voucherCode);
                }).orElseThrow(() -> new ResourceNotFoundException("Voucher not found with id " + voucherCodeId));
    }

    /**
     *
     * @param voucherCodeId Deleted voucher code id
     * @return Delete status
     */
    @DeleteMapping("/voucherCode/{voucherCodeId}")
    public ResponseEntity<?> deleteVoucher(@PathVariable Long voucherCodeId) {
        return voucherRepository.findById(voucherCodeId)
                .map(voucherCode -> {
                    voucherRepository.delete(voucherCode);
                    return ResponseEntity.ok().build();
                }).orElseThrow(() -> new ResourceNotFoundException("Voucher not found with id " + voucherCodeId));
    }      
}
