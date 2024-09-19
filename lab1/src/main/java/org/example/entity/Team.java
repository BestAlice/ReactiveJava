package org.example.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class Team {
    private String name;

    private List<String> members;

    private int wins;

    private int loses;
}