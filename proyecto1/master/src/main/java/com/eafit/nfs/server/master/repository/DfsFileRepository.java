package com.eafit.nfs.server.master.repository;

import com.eafit.nfs.server.master.model.DfsFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DfsFileRepository extends JpaRepository<DfsFile,Long > {
}
