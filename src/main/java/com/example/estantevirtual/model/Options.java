package com.example.estantevirtual.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Options {
    int page;
    int pageSize;
    String orderBy;
    String sort;
}
