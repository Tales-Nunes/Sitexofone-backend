package com.talesnunes.Sitexofone_backend.entities;

import com.talesnunes.Sitexofone_backend.enums.PortfolioType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Table(name = "tb_portfolio_item")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PortfolioItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PortfolioType type;

    @Column(nullable = false)
    private String url;

    @Column(columnDefinition = "TEXT")
    private String description;

    private String tags;

    @Column(nullable = false)
    private Date createdAt = new Date();
}
