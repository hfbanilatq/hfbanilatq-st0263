package com.eafit.nfs.server.master.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
public class DfsServer {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    Long id;
    String host;
    long weight;
    List<DfsBlock> dfsBlocks;
    boolean active;
}
