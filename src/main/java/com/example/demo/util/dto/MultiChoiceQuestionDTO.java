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
public class MultiChoiceQuestionDTO {
    private String question;
    private String key;
    private float score;
    private List<String> items;
    private List<String> correctAnswers;
    private List<String> courrentAnswers;

    public static Optional<MultiChoiceQuestionDTO> fromString(String value){
        try {
            MultiChoiceQuestionDTO question = new Gson().fromJson(value, MultiChoiceQuestionDTO.class);
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
