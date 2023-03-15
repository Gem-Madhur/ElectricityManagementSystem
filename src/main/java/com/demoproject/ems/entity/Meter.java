package com.demoproject.ems.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "meter")

public class Meter {

    /**
     * meter_id is the primary key for Meter_tbl
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "meter_id")
    private Long meterId;

    @Column(name = "meter_load")
    private Float meterLoad;

    @Column(name = "min_bill_amount")
    private Float minBillAmount;
}
