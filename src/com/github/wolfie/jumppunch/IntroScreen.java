package com.github.wolfie.jumppunch;

import java.io.Serializable;

import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.VerticalLayout;

public class IntroScreen extends CustomComponent {
	private static final long serialVersionUID = -2542366715396717450L;

	public interface OptionsListener extends Serializable {
		void startGame();
	}

	private final VerticalLayout layout = new VerticalLayout();

	public IntroScreen(final OptionsListener listener) {
		setCompositionRoot(layout);
		layout.addComponent(new Button("Start", new Button.ClickListener() {
			private static final long serialVersionUID = -607964405994614202L;

			@Override
			public void buttonClick(final ClickEvent event) {
				listener.startGame();
			}
		}));
	}
}
