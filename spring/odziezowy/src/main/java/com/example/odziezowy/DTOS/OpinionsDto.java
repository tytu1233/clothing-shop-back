package com.example.odziezowy.DTOS;

import com.example.odziezowy.Model.Opinions;
import lombok.Data;

import java.io.Serializable;

/**
 * A DTO for the {@link Opinions} entity
 */
@Data
public class OpinionsDto implements Serializable {
    private final Long idOpinion;
    private final int rating;
    private final String comment;
}