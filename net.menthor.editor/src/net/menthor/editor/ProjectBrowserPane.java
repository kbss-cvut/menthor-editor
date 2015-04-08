package net.menthor.editor;

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
import java.awt.Color;

import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import net.menthor.editor.explorer.ProjectBrowser;

/**
 * @author John Guerson
 */
public class ProjectBrowserPane extends JPanel {

	private static final long serialVersionUID = 1752050268631906319L;
	@SuppressWarnings("unused")
	private AppFrame frame;
	private ProjectBrowser browser;
	
	public ProjectBrowserPane(AppFrame frame)
	{
		super();
		setBackground(Color.WHITE);
		
		this.frame = frame;	
		
		setFocusable(false);
		setLayout(new BorderLayout(3,3));
		
		browser = new ProjectBrowser(frame,null,null);
		browser.setBorder(new EmptyBorder(0, 0, 0, 0));
			
		//TitlePane panel = new TitlePane("Project Browser","/resources/icons/x16/door_in.png");
				
		add(browser,BorderLayout.CENTER);
		//add(panel,BorderLayout.NORTH);
		//addTab("Project Browser", browser); 
		//setIconAt(indexOfComponent(browser),new ImageIcon(BrowserManager.class.getResource("/resources/icons/x16/drawer.png")));
	}
	
	public ProjectBrowser getProjectBrowser() { return browser; }
	
}
