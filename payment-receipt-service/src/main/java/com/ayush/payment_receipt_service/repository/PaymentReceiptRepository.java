package com.ayush.payment_receipt_service.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ayush.payment_receipt_service.model.PaymentReceipt;

public interface PaymentReceiptRepository extends JpaRepository<PaymentReceipt, Long> {
  List<PaymentReceipt> findByDealerId(Long dealerId);

  Optional<PaymentReceipt> findByCropId(Long cropId);
}
