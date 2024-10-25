package com.kyalo.mpesaintegration.repository;

import com.kyalo.mpesaintegration.entity.MpesaPayment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MpesaPaymentRepository extends JpaRepository<MpesaPayment, String> {
}
