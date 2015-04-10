package org.tinyuml.ui.diagram.commands;

/**
 * Copyright 2007 Wei-ju Wu
 *
 * This file is part of TinyUML.
 *
 * TinyUML is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 *
 * TinyUML is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with TinyUML; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA
 */

import javax.swing.undo.AbstractUndoableEdit;

import net.menthor.editor.model.UmlProject;

/**
 * Base class for all diagram commands
 * 
 * @author Antognoni Albuquerque
 */
public abstract class BaseDiagramCommand extends AbstractUndoableEdit implements Command {
	private static final long serialVersionUID = 733613330226013575L;
	protected DiagramNotification notification;
	protected boolean redo = false;
	protected UmlProject project;
	
	public DiagramNotification getNotification() {
		return notification;
	}
	
	public UmlProject getProject() {
		return project;
	}
	
}