package org.project.portfolio.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "account")
public class AccountEntity extends AuditingEntity {
  
  @Id
  private Integer id;
  
  @Column(length = 50, nullable = false)
  private String email;
  
  @Column(length = 50, nullable = false)
  private String password;
  
  @Column(length = 50, nullable = false)
  private String mobileNumber;
  
  @Column(length = 20, nullable = false)
  private String name;
}
