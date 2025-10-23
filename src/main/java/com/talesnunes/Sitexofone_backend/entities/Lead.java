package com.talesnunes.Sitexofone_backend.entities;

import com.talesnunes.Sitexofone_backend.enums.EventType;
import com.talesnunes.Sitexofone_backend.enums.Status;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;

@Entity
@Table(name = "tb_lead")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Lead {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false, length = 11)
    private String telephone;

    @Column(nullable = false)
    private LocalDate dataEvent;

    @Column(nullable = false)
    private String local;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private EventType typeEvent;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String message;

    @Column(nullable = false)
    private Status status;

    @CreationTimestamp
    @Column(nullable = false)
    private LocalDate createAt;
}
