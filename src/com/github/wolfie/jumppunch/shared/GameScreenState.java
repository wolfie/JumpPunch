package com.github.wolfie.jumppunch.shared;

import com.vaadin.shared.AbstractComponentState;
import com.vaadin.shared.annotations.DelegateToWidget;

public class GameScreenState extends AbstractComponentState {
	private static final long serialVersionUID = -2414016249952915289L;
	@DelegateToWidget
	public Player player;
}
