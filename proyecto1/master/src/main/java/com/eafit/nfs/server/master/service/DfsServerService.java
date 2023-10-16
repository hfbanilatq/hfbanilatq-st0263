package com.eafit.nfs.server.master.service;

import com.eafit.nfs.server.master.model.DfsServer;
import com.eafit.nfs.server.master.repository.DfsServerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DfsServerService {
    private DfsServerRepository dfsServerRepository;

    @Autowired
    public void setDfsServerRepository(DfsServerRepository dfsServerRepository) {
        this.dfsServerRepository = dfsServerRepository;
    }

    public DfsServer save(DfsServer dfsServer) {
        return this.dfsServerRepository.save(dfsServer);
    }

    public boolean setAsUnavailable(Long id) {
        try {
            if (!this.dfsServerRepository.existsById(id)) {
                return false;
            }

            DfsServer dfsServer = dfsServerRepository.getReferenceById(id);
            dfsServer.setActive(false);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

    }
}
