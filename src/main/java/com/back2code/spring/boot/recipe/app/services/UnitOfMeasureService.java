package com.back2code.spring.boot.recipe.app.services;

import java.util.Set;

import com.back2code.spring.boot.recipe.app.commands.UnitOfMeasureCommand;

public interface UnitOfMeasureService {

	Set<UnitOfMeasureCommand> getAllUOMCommands();
}
