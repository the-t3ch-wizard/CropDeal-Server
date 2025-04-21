package com.ayush.crop_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ayush.crop_service.model.Crop;

import java.util.List;

@Repository
public interface CropRepository extends JpaRepository<Crop, Long> {
    List<Crop> findByFarmerId(Long farmerId);
    List<Crop> findByCropType(String cropType);
    void deleteByFarmerId(Long farmerId);
}
