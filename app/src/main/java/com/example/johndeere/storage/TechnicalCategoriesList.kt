package com.example.johndeere.storage

import com.example.johndeere.Categories
import com.example.johndeere.R
import com.example.johndeere.Words

/*
Lista de categorías de lenguaje técnico, donde cata categoría tiene sus respectivas palabras.
 */

var TechnicalCategoriesList = mutableListOf(
    Categories("Categoría1", mutableListOf(
        Words("Palabra1", null),
        Words("Palabra2", null),
        Words("Palabra3", null),
        Words("Palabra4", null),
        Words("Palabra5", null)
    )
    ),
    Categories("Categoría2",mutableListOf(
        Words("Palabra1", null),
        Words("Palabra2", null),
        Words("Palabra3", null),
        Words("Palabra4", null),
        Words("Palabra5", null)
    )
    )
)