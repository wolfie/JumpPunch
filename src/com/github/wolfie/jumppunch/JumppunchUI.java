package com.github.wolfie.jumppunch;

import com.github.wolfie.jumppunch.IntroScreen.OptionsListener;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Title;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.UI;

/**
 * Main UI class
 */
@Title("JumpPunch!!!")
@Theme("jumppunch")
public class JumppunchUI extends UI {
	private static final long serialVersionUID = 8799639110574902262L;

	private final OptionsListener optionsListener = new OptionsListener() {
		private static final long serialVersionUID = -3569859115908191243L;

		@Override
		public void startGame() {
			JumppunchUI.this.startGame();
		}
	};

	@Override
	protected void init(final VaadinRequest request) {
		setContent(new IntroScreen(optionsListener));
	}

	private void startGame() {
		setContent(new GameScreen());
	}
}