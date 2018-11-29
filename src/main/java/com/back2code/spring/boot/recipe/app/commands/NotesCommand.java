package com.back2code.spring.boot.recipe.app.commands;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@NoArgsConstructor
@ToString
public class NotesCommand {
	private Long id;
	private String recipeNotes;
}
