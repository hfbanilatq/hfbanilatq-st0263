package com.eafit.nfs.server.master.repository;

import com.eafit.nfs.server.master.model.DfsBlock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DfsBlockRepository extends JpaRepository<DfsBlock, Long> {
}
