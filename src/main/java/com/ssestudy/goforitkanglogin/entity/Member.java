package com.ssestudy.goforitkanglogin.entity;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "member")
@Data
public class Member {
    @Id
    @GeneratedValue
    private Long id;
    private String email;
    private String password;
    private String memberName;

}
