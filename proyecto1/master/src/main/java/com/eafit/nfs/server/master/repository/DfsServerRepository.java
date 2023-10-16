package com.eafit.nfs.server.master.repository;

import com.eafit.nfs.server.master.model.DfsServer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DfsServerRepository extends JpaRepository<DfsServer, Long> {
}
