package com.azrul.voucherpool.controller;

import com.azrul.voucherpool.exception.ResourceNotFoundException;
import com.azrul.voucherpool.model.Recipient;
import com.azrul.voucherpool.model.SpecialOffer;
import com.azrul.voucherpool.model.VoucherCode;
import com.azrul.voucherpool.repository.RecipientRepository;
import com.azrul.voucherpool.repository.SpecialOfferRepository;
import com.azrul.voucherpool.repository.VoucherRepository;
import com.azrul.voucherpool.util.RandomGenerator;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class VoucherPoolController {

    @Autowired
    private VoucherRepository voucherRepository;
    @Autowired
    private SpecialOfferRepository specialOfferRepository;
    @Autowired
    private RecipientRepository recipientRepository;

    /**
     *
     * @param expirationDate Voucher expiration date
     * @param specialOffer Special offer name
     * @return Status of Offer creation
     * @throws ParseException
     */
    @PostMapping("/createSpecialOffer/{expirationDate}")
    public String createVoucherEachRecipient(@PathVariable String expirationDate, @Valid @RequestBody SpecialOffer specialOffer) throws ParseException {
        int MAX_ATTEMPT = 5;
        boolean specialOfferSaved = false;

        for (Recipient recipient : recipientRepository.findAll()) {
            VoucherCode voucherCode = new VoucherCode();
            voucherCode.setRecipient(recipient.getEmail());
            voucherCode.setSpecialOffer(specialOffer);
            voucherCode.setExpirationDate(new SimpleDateFormat("yyyy-MM-dd").parse(expirationDate));

            int attempt = 0;
            while (true) {
                try {
                    attempt++;
                    if (!specialOfferSaved) {
                        specialOfferRepository.save(specialOffer);
                        specialOfferSaved = true;
                    }

                    voucherCode.setCode(RandomGenerator.randomAlphaNumeric());
                    voucherRepository.save(voucherCode);
                    break;
                } catch (Exception e) {
                    if (attempt >= MAX_ATTEMPT) {
                        return "Failed to generate special offer";
                    }
                }
            }
        }

        return "Successfully generate voucher for all recipient!";
    }

    /**
     *
     * @param voucherCode Voucher code to validate
     * @param email Recipient email to validate
     * @return Status of voucher validation
     */
    @GetMapping("/validateVoucher/{voucherCode}/{email}")
    public String validateVoucher(@PathVariable String voucherCode, @PathVariable String email) {

        VoucherCode vc = voucherRepository.findVoucherByCode(voucherCode);
        if (vc != null) {
            if (vc.getRecipient().equals(email)) {
                vc.setRedeemDate(new Date(System.currentTimeMillis()));
                voucherRepository.save(vc);
                return "Voucher is valid with " + vc.getSpecialOffer().getOfferPercentage() + " discount.";
            }
        }
        throw new ResourceNotFoundException("Voucher is not valid");
    }

    /**
     *
     * @param recipient Find all valid voucher for specified recipient's email
     * @return Map of valid offer and voucher code
     */
    @GetMapping("/validateRecipient/{recipient}")
    public Map<String, VoucherCode> getVoucherByEmail(@PathVariable String recipient) {
        Map<String, VoucherCode> map = new HashMap<>();
        voucherRepository.findVoucherByEmail(recipient).stream().filter((vc) -> (vc.getRedeemDate() == null)).forEachOrdered((vc) -> {
            map.put(vc.getSpecialOffer().getOfferName(), vc);
        });
        return map;
    }
}
