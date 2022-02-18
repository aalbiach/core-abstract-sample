package com.example.demo.repository;

import com.example.demo.entity.Stock;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShoeRepository extends JpaRepository<Stock, UUID> {

}
