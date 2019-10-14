package com.azrul.voucherpool.repository;

import com.azrul.voucherpool.model.VoucherCode;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface VoucherRepository extends JpaRepository<VoucherCode, Long> {

    @Query(value = "SELECT * FROM voucher_code WHERE recipient = :recipient",
            nativeQuery = true)
    List<VoucherCode> findVoucherByEmail(@Param("recipient") String recipient);
    
    @Query(value = "SELECT * FROM voucher_code WHERE code = :code",
            nativeQuery = true)
    VoucherCode findVoucherByCode(@Param("code") String code);

}
