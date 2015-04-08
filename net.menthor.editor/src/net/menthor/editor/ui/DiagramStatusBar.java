package net.menthor.editor.ui;

/**
 * ============================================================================================
 * Menthor Editor -- Copyright (c) 2015 
 *
 * This file is part of Menthor Editor. Menthor Editor is based on TinyUML and as so it is 
 * distributed under the same license terms.
 *
 * Menthor Editor is free software; you can redistribute it and/or modify it under the terms 
 * of the GNU General Public License as published by the Free Software Foundation; either 
 * version 2 of the License, or (at your option) any later version.
 *
 * Menthor Editor is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; 
 * without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  
 * See the GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along with Menthor Editor; 
 * if not, write to the Free Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, 
 * MA  02110-1301  USA
 * ============================================================================================
 */

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import org.tinyuml.ui.diagram.DiagramEditor;

import java.awt.Color;

/**
 * @author John Guerson
 */
public class DiagramStatusBar extends JPanel implements StatusListener{
	
	private static final long serialVersionUID = 2153837501881399529L;
	
	public JLabel statusLabel = new JLabel();
	public DiagramEditor editor;
	
	public void clearStatus()
	{
		statusLabel.setText("");
	}
		
	public DiagramStatusBar(DiagramEditor d)
	{
		super(new BorderLayout());		
		setBackground(Color.WHITE);
		this.editor = d;
		setBorder(new EmptyBorder(3, 3, 3, 3));		
		statusLabel.setBackground(Color.WHITE);
		add(statusLabel, BorderLayout.CENTER);
		setPreferredSize(new Dimension(450, 28));		
	}

	public void reportStatus(String status)
	{
		statusLabel.setText(status);
	}	
}