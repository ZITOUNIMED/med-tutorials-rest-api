package com.example.demo.util.dto;

import java.util.Optional;

import com.google.gson.Gson;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class TextQuestionDTO {
    private String question;
    private String key;
    private float score;
    private String questionComplement;
    private String correctAnswer;
    private String courrentAnswer;

    public static Optional<TextQuestionDTO> fromString(String value){
        try {
            TextQuestionDTO question = new Gson().fromJson(value, TextQuestionDTO.class);
            return Optional.of(question);
        } catch (Exception e){
            return Optional.empty();
        }
    }

    @Override
    public String toString(){
        return new Gson().toJson(this);
    }
}
