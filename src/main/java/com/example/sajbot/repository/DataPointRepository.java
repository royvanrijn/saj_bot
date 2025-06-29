package com.example.sajbot.repository;

import com.example.sajbot.model.DataPoint;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DataPointRepository extends JpaRepository<DataPoint, Long> {
}
