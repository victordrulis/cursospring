package com.victor.cursospring.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.victor.cursospring.domain.Estado;

@Repository
public interface EstadoRepository extends JpaRepository<Estado, Integer> {}
