package org.example.cs_home_assignment.model;

import java.util.Map;

public record Probability(Integer column, Integer row, Map<String, Integer> symbols) {
}
