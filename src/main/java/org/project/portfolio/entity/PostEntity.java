package org.project.portfolio.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "post")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PostEntity extends AuditingEntity {
  
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;
  
  @ManyToOne
  private AccountEntity account;
  
  @Column(length = 200, nullable = false)
  private String title;
  
  @Column(length = 1000, nullable = false)
  private String content;
  
}
