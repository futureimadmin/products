package com.futureim.generative.im.supervisor.repo;

import com.futureim.generative.im.model.Agent;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AgentRepository extends JpaRepository<Agent, Long> {
    List<Agent> findByCustomerCustomerId(Long customerId);
}
