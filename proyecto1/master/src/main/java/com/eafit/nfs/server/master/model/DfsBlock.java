package com.eafit.nfs.server.master.model;

import jakarta.persistence.*;
import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@EqualsAndHashCode
@Entity
public class DfsBlock {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    Long id;
    String serverLocation;
    long size;
    @ManyToOne
    @JoinColumn(name = "dfsServer_Id")
    DfsFile dfsFile;
    @ManyToOne
    @JoinColumn(name = "dfsServer_Id")
    DfsServer dfsServer;
}
