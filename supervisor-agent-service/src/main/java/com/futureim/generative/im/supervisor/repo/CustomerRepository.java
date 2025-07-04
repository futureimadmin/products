package com.futureim.generative.im.supervisor.repo;

import com.futureim.generative.im.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {}
