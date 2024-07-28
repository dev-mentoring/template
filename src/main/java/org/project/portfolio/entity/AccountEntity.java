package org.project.portfolio.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "account")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AccountEntity extends AuditingEntity {
  
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
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
