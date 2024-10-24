package com.kyalo.mpesaintegration.repository;

import com.kyalo.mpesaintegration.entity.MpesaExpress;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface MpesaExpressRepository extends JpaRepository<MpesaExpress, UUID> {
}
