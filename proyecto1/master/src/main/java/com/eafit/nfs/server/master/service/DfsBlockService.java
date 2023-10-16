package com.eafit.nfs.server.master.service;

import com.eafit.nfs.server.master.model.DfsBlock;
import com.eafit.nfs.server.master.model.DfsFile;
import com.eafit.nfs.server.master.model.DfsServer;
import com.eafit.nfs.server.master.repository.DfsBlockRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DfsBlockService {
    private DfsBlockRepository dfsBlockRepository;

    @Autowired
    public void setDfsBlockRepository(DfsBlockRepository dfsBlockRepository) {
        this.dfsBlockRepository = dfsBlockRepository;
    }

    public DfsBlock save(DfsBlock dfsBlock) {
        return this.dfsBlockRepository.save(dfsBlock);
    }
}
