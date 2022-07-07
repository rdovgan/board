package com.longboard.game.durak.display;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.util.Arrays;
import java.util.stream.Collectors;

class DurakGameDisplayTest {

	@Test
	public void testGameStart() {
		DurakGameDisplay gameDisplay = new DurakGameDisplay();

		int componentsCount = (int) Arrays.stream(gameDisplay.getComponents()).filter(component -> component instanceof Panel).collect(Collectors.toList())
				.parallelStream().flatMap(component -> Arrays.stream(((Panel) (component)).getComponents())).count();;
		Assertions.assertEquals(3, componentsCount);

		gameDisplay.beginGame();

		componentsCount = (int) Arrays.stream(gameDisplay.getComponents()).filter(component -> component instanceof Panel).collect(Collectors.toList())
				.parallelStream().flatMap(component -> Arrays.stream(((Panel) (component)).getComponents())).count();;
		Assertions.assertTrue(componentsCount > 10);
	}

}