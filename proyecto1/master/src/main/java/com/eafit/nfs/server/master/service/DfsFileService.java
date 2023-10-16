package com.eafit.nfs.server.master.service;

import com.eafit.nfs.server.master.model.DfsFile;
import com.eafit.nfs.server.master.repository.DfsFileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class DfsFileService {
    private DfsFileRepository dfsFileRepository;

    @Autowired
    public void setDfsFileRepository(DfsFileRepository dfsFileRepository) {
        this.dfsFileRepository = dfsFileRepository;
    }

    public DfsFile save(DfsFile dfsFile) {
        return this.dfsFileRepository.save(dfsFile);
    }

    public List<byte[]> splitFileIntoBlocks(byte[] fileData) {
        List<byte[]> blocks = new ArrayList<>();
        int blockSize = 512*1024; // 512 KB por bloque
        int fileLength = fileData.length;
        int blockCount = (int) Math.ceil((double) fileLength / blockSize);

        for (int i = 0; i < blockCount; i++) {
            int start = i * blockSize;
            int length = Math.min(blockSize, fileLength - start);

            byte[] block = Arrays.copyOfRange(fileData, start, start + length);
            blocks.add(block);
        }

        return blocks;
    }
}
