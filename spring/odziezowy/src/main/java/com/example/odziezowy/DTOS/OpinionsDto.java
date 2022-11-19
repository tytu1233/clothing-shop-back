package com.example.odziezowy.DTOS;

import com.example.odziezowy.Model.Opinions;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * A DTO for the {@link Opinions} entity
 */
@Data
public class OpinionsDto implements Serializable {
    private final Long idOpinion;
    @NotBlank(message = "Ocena jest wymagana")
    private final int rating;
    @NotBlank(message = "Komentarz jest wymagany")
    private final String comment;
}