package com.example.odziezowy.Model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "opinions")
public class Opinions {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long idOpinion;

    @Column
    private Double rating;

    @Column
    private String comment;
    @ManyToOne
    @JsonManagedReference
    @JoinColumn(name = "userId")
    private Users usersOpinion;

    @ManyToOne
    @JoinColumn(name = "productsId")
    @JsonManagedReference
    private Products productsId;

}
