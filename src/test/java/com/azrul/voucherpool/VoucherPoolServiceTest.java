package com.azrul.voucherpool;

import com.azrul.voucherpool.model.SpecialOffer;
import com.azrul.voucherpool.model.VoucherCode;
import com.azrul.voucherpool.repository.VoucherRepository;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)

@DataJpaTest
public class VoucherPoolServiceTest {
    
    @Autowired
    private TestEntityManager entityManager;
    
    @Autowired
    private VoucherRepository voucherRepository;
    
    @Test
    public void contextLoads() {
    }
    
    @Test
    public void whenFindByVoucherByCode_returnVoucher() throws ParseException {

        // given
        VoucherCode voucher = new VoucherCode();
        voucher.setCode("12345678");
        voucher.setRecipient("test@mail.com");
        SpecialOffer specialOffer = new SpecialOffer();
        specialOffer.setOfferName("testOffer");
        entityManager.persist(specialOffer);
        entityManager.flush();
        voucher.setSpecialOffer(specialOffer);
        voucher.setExpirationDate(new SimpleDateFormat("yyyy-MM-dd").parse("2019-12-15"));
        entityManager.persist(voucher);
        entityManager.flush();

        // when
        VoucherCode found = voucherRepository.findVoucherByCode(voucher.getCode());

        // then
        assertThat(found.getCode())
                .isEqualTo(voucher.getCode());
    }    
}
