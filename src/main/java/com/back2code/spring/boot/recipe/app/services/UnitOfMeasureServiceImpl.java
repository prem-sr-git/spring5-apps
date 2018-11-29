package com.back2code.spring.boot.recipe.app.services;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.stereotype.Service;

import com.back2code.spring.boot.recipe.app.commands.UnitOfMeasureCommand;
import com.back2code.spring.boot.recipe.app.converters.UnitOfMeasureCommandToUnitOfMeasure;
import com.back2code.spring.boot.recipe.app.converters.UnitOfMeasureToUnitOfMeasureCommand;
import com.back2code.spring.boot.recipe.app.repositories.UnitOfMeasureRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class UnitOfMeasureServiceImpl implements UnitOfMeasureService {

	final private UnitOfMeasureRepository uomRepository;
	final private UnitOfMeasureToUnitOfMeasureCommand uomCommandConverter;
	final private UnitOfMeasureCommandToUnitOfMeasure uomConverter;
	
	public UnitOfMeasureServiceImpl(UnitOfMeasureRepository uomRepository,
			UnitOfMeasureToUnitOfMeasureCommand uomCommandConverter, UnitOfMeasureCommandToUnitOfMeasure uomConverter) {
		this.uomRepository = uomRepository;
		this.uomCommandConverter = uomCommandConverter;
		this.uomConverter = uomConverter;
	}

	@Override
	public Set<UnitOfMeasureCommand> getAllUOMCommands() {
		
		log.debug("Getting UOM commands @ Service Layer.... >> getting All UOMCommands");
				
		Set<UnitOfMeasureCommand> uomCmds =
				StreamSupport.stream(uomRepository.findAll().spliterator(), false)
																.map(uomCommandConverter::convert)
																.collect(Collectors.toSet());
		log.debug("UOM commands retreived @ Service Layer.... >> UOMCommands[no.="+uomCmds.size()+"]");

		return uomCmds;
	}

}
