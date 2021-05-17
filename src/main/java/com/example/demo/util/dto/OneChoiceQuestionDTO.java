package com.example.demo.util.dto;

import java.util.List;
import java.util.Optional;

import com.google.gson.Gson;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class OneChoiceQuestionDTO {
    private String question;
    private String key;
    private float score;
    private List<String> items;
    private String correctAnswer;
    private String courrentAnswer;

    public static Optional<OneChoiceQuestionDTO> fromString(String value){
        try {
            OneChoiceQuestionDTO question = new Gson().fromJson(value, OneChoiceQuestionDTO.class);
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
