package com.back2code.spring.boot.recipe.app.converters;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.back2code.spring.boot.recipe.app.commands.UnitOfMeasureCommand;
import com.back2code.spring.boot.recipe.app.domain.UnitOfMeasure;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;


class UnitOfMeasureCommandToUnitOfMeasureTest {

	private static final String DESCRIPTION = "description";
	private static final Long LONG_VAL = new Long(1l);
	UnitOfMeasureCommandToUnitOfMeasure converter;
	
	@BeforeEach
	void setUp() throws Exception {
		converter = new UnitOfMeasureCommandToUnitOfMeasure();
	}
	
	@Test
	public void testNullParameter() {
		assertNull(converter.convert(null));
	}
	
	@Test
	public void testEmptyObject(){
		assertNotNull(converter.convert(new UnitOfMeasureCommand()));
	}
	
	@Test
	public void testConvert() {
		//given
		UnitOfMeasureCommand command = new UnitOfMeasureCommand();
		command.setId(LONG_VAL);
		command.setDescription(DESCRIPTION);
	
//		when
		
		UnitOfMeasure uom = converter.convert(command);
		
//		then
		assertNotNull(uom);
		assertEquals(LONG_VAL, uom.getId());
		assertEquals(DESCRIPTION, uom.getDescription());
		
	}

}
