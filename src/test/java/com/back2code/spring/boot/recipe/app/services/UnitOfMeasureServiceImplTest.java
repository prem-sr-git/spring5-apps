package com.back2code.spring.boot.recipe.app.services;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.back2code.spring.boot.recipe.app.commands.UnitOfMeasureCommand;
import com.back2code.spring.boot.recipe.app.converters.UnitOfMeasureCommandToUnitOfMeasure;
import com.back2code.spring.boot.recipe.app.converters.UnitOfMeasureToUnitOfMeasureCommand;
import com.back2code.spring.boot.recipe.app.domain.UnitOfMeasure;
import com.back2code.spring.boot.recipe.app.repositories.UnitOfMeasureRepository;

import static org.junit.jupiter.api.Assertions.assertEquals;

class UnitOfMeasureServiceImplTest {
	
	UnitOfMeasureServiceImpl uomService;
	
	
	UnitOfMeasureToUnitOfMeasureCommand uomCommandConverter = new UnitOfMeasureToUnitOfMeasureCommand() ;
	
	
	UnitOfMeasureCommandToUnitOfMeasure uomConverter;

	@Mock
	UnitOfMeasureRepository uomRepository;

	@BeforeEach
	void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		uomService = new UnitOfMeasureServiceImpl(uomRepository, uomCommandConverter, uomConverter);
	}

	@Test
	void testGetUOMCommandsTest() {
		//given
		HashSet<UnitOfMeasure> uomDataSet = new HashSet<>();

		UnitOfMeasure uom1 = new UnitOfMeasure();
		uom1.setId(1L);
		uomDataSet.add(uom1);
		
		UnitOfMeasure uom2 = new UnitOfMeasure();
		uom2.setId(2L);
		uomDataSet.add(uom2);
		
		UnitOfMeasure uom3 = new UnitOfMeasure();
		uom3.setId(3L);
		uomDataSet.add(uom3);
		
		when(uomRepository.findAll()).thenReturn(uomDataSet);
		
		//when
		Set<UnitOfMeasureCommand> uomCommands = uomService.getAllUOMCommands();
		
		//then
		assertEquals(uomDataSet.size(), uomCommands.size());
		verify(uomRepository, times(1)).findAll();
		verify(uomRepository, never()).findById(anyLong());
		
	}

}
