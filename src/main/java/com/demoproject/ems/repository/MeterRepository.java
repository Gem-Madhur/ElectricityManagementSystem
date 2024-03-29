package com.demoproject.ems.repository;

import com.demoproject.ems.entity.Meter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface MeterRepository extends JpaRepository<Meter, Long> {
}
