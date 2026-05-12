package com.order;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface OutboxRepo extends JpaRepository<OutboxEvent, Long> {

    List<OutboxEvent> findByProcessedFalse();
}
