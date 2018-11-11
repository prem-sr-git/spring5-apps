package com.back2code.spring.boot.recipe.app.converters;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.back2code.spring.boot.recipe.app.commands.UnitOfMeasureCommand;
import com.back2code.spring.boot.recipe.app.domain.UnitOfMeasure;

import static org.junit.jupiter.api.Assertions.*;

class UnitOfMeasureToUnitOfMeasureCommandTest {

    public static final String DESCRIPTION = "description";
    public static final Long LONG_VALUE = new Long(1L);
    
	UnitOfMeasureToUnitOfMeasureCommand converter;
	
	@BeforeEach
	void setUp() throws Exception {
		converter = new UnitOfMeasureToUnitOfMeasureCommand();
	}

    @Test
    public void testNullObjectConvert() throws Exception {
        assertNull(converter.convert(null));
    }

    @Test
    public void testEmptyObj() throws Exception {
        assertNotNull(converter.convert(new UnitOfMeasure()));
    }
	
	@Test
	void testConvert() {
        //given
        UnitOfMeasure uom = new UnitOfMeasure();
        uom.setId(LONG_VALUE);
        uom.setDescription(DESCRIPTION);
        
        //when
        UnitOfMeasureCommand uomc = converter.convert(uom);

        //then
        assertEquals(LONG_VALUE, uomc.getId());
        assertEquals(DESCRIPTION, uomc.getDescription());		
	}

}
