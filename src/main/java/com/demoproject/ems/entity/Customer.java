package com.demoproject.ems.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.util.Date;


@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "customer_tbl")
public class Customer {

    /**
     * customer_id is the primary key for customer_tbl
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "customer_id")
    private Long cId;

    @Column(name = "customer_name")
    private String cName;

    @Column(name = "customer_address")
    private String cAddress;

    @JsonFormat(pattern = "dd-MM-yyyy")
    @CreationTimestamp
    @Column(name = "c_date")
    private Date connectionDate;

    @Column(name = "last_reading")
    private Long lastReading;

    @Column(name = "current_reading")
    private Long currReading;

    @Column(name = "bill_amount")
    private Double billAmount;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "meter_id")
    private Meter meter;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "supplier_id")
    private Supplier supplier;


}