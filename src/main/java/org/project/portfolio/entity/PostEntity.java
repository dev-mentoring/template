package org.project.portfolio.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "post")
public class PostEntity extends AuditingEntity {
  
  @Id
  private Integer id;
  
  @ManyToOne
  private AccountEntity account;
  
  @Column(length = 200, nullable = false)
  private String title;
  
  @Column(length = 1000, nullable = false)
  private String content;
  
}
