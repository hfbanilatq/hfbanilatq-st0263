package com.eafit.nfs.server.master.controller;

import com.eafit.nfs.server.master.model.DfsServer;
import com.eafit.nfs.server.master.service.DfsServerService;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("server")
public class DfsServerController {
    private DfsServerService dfsServerService;

    @Autowired
    public void setDfsServerService(DfsServerService dfsServerService) {
        this.dfsServerService = dfsServerService;
    }

    @PostMapping("/regiter")
    public ResponseEntity<String> register(DfsServer dfsServer) {
        try {
            this.dfsServerService.save(dfsServer);
            return ResponseEntity.status(HttpStatus.OK).body("Se ha registrado con exito");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Se ha generado un error inesperado al registrar");
        }
    }


}
