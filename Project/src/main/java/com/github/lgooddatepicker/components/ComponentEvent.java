/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.lgooddatepicker.components;

import javax.swing.JComponent;

/**
 *
 * @author mcwolfy
 */
public class ComponentEvent {
	public static final int PREVIOUS_YEAR = 1;
	public static final int PREVIOUS_MONTH = 2;
	public static final int NEXT_MONTH = 3;
	public static final int NEXT_YEAR = 4;
	
	private int what;
	private JComponent component;

	public ComponentEvent(int what, JComponent component) {
		this.what = what;
		this.component = component;
	}

	public int getWhat() {
		return what;
	}

	public JComponent getComponent() {
		return component;
	}
}
