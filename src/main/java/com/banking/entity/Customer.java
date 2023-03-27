package com.banking.entity;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class Customer implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long custId;

    String custName;

    @Temporal(TemporalType.DATE)
    Date dob;

    String email;

    @OneToMany(cascade = CascadeType.ALL, fetch =  FetchType.EAGER)
    @JoinColumn(name = "cust_id", referencedColumnName = "custId")
    List<Account> accounts;
}
