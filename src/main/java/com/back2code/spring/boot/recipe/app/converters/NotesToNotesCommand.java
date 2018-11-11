package com.back2code.spring.boot.recipe.app.converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import com.back2code.spring.boot.recipe.app.commands.NotesCommand;
import com.back2code.spring.boot.recipe.app.domain.Notes;

import lombok.Synchronized;

@Component
public class NotesToNotesCommand implements Converter<Notes, NotesCommand> {

	@Synchronized
	@Nullable
	@Override
	public NotesCommand convert(Notes source) {
		if (source == null) {
			return null;
		}

		final NotesCommand notesCmd = new NotesCommand();
		notesCmd.setId(source.getId());
		notesCmd.setRecipeNotes(source.getRecipeNotes());
		return notesCmd;
	}

}
