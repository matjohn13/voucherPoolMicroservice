package com.azrul.voucherpool;

import com.azrul.voucherpool.controller.RecipientController;
import com.azrul.voucherpool.controller.VoucherPoolController;
import com.azrul.voucherpool.model.Recipient;
import com.azrul.voucherpool.model.SpecialOffer;
import com.azrul.voucherpool.model.VoucherCode;
import com.azrul.voucherpool.repository.RecipientRepository;
import com.azrul.voucherpool.repository.SpecialOfferRepository;
import com.azrul.voucherpool.repository.VoucherRepository;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import javafx.application.Application;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.mockito.BDDMockito.given;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@RunWith(SpringRunner.class)
//@WebMvcTest(RecipientController.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class VoucherPoolControllerTest {
          
    @Autowired
    private MockMvc mvc;
    
    @MockBean
    private VoucherRepository voucherRepository;
    
   @MockBean
    private SpecialOfferRepository specialOfferRepository;
   
    @MockBean
    private RecipientRepository recipientRepository;
    
  
    @Test
    public void contextLoads() {
    }

    @Test
    public void recipientController_validateRecipient() throws Exception {

        Recipient r = new Recipient();
        r.setName("test");
        r.setEmail("test@mail.com");
        given(recipientRepository.save(r)).willReturn(r);

        RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/recipient").content("{\"name\": \"test\", \"email\": \"test@mail.com\"}").contentType(MediaType.APPLICATION_JSON);
        MvcResult result = mvc.perform(requestBuilder).andReturn();

        MockHttpServletResponse response = result.getResponse();
        int status = response.getStatus();
        Assert.assertEquals("response status is correct", 200, status);
    }
    
    @Test
    public void SpecialOfferController_validateOffer() throws Exception {

        SpecialOffer o = new SpecialOffer();
        o.setOfferName("offer");
        o.setOfferPercentage("50%");
        given(specialOfferRepository.save(o)).willReturn(o);

        RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/specialOffer").content("{\"offerName\": \"offer\", \"offerPercentage\": \"50%\"}").contentType(MediaType.APPLICATION_JSON);
        MvcResult result = mvc.perform(requestBuilder).andReturn();

        MockHttpServletResponse response = result.getResponse();
        int status = response.getStatus();
        Assert.assertEquals("response status is correct", 200, status);
    }
    
    @Test
    public void VoucherCodeController_validateVoucher() throws Exception {

        VoucherCode v = new VoucherCode();
        v.setCode("1234");
        v.setRecipient("test@mail.com");
        given(voucherRepository.save(v)).willReturn(v);

        RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/voucherCode").content("{\"code\": \"1234\", \"recipient\": \"test@mail.com\"}").contentType(MediaType.APPLICATION_JSON);
        MvcResult result = mvc.perform(requestBuilder).andReturn();

        MockHttpServletResponse response = result.getResponse();
        int status = response.getStatus();
        Assert.assertEquals("response status is correct", 200, status);
    }
    
}
