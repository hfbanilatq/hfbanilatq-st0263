package com.eafit.nfs.server.master.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@EqualsAndHashCode
@Entity
public class DfsFile {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    Long id;
    String name;
    long size;
    @OneToMany(mappedBy = "dfsFile")
    List<DfsBlock> blocks;
}
