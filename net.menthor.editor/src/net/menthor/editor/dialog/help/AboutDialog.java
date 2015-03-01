/**
 * Copyright(C) 2011-2014 by John Guerson, Tiago Prince, Antognoni Albuquerque
 *
 * This file is part of OLED (OntoUML Lightweight BaseEditor).
 * OLED is based on TinyUML and so is distributed under the same
 * license terms.
 *
 * OLED is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 *
 * OLED is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with OLED; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA
 */
package net.menthor.editor.dialog.help;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.SwingConstants;

import net.menthor.editor.AppFrame;
import net.menthor.editor.Main;
import net.menthor.editor.ui.JHyperLinkLabel;

/**
 * @author John Guerson
 */
public class AboutDialog extends JDialog {

	private static final long serialVersionUID = -251319551154959770L;
	private JPanel CenterPanel;
	private JLabel label;
	private JLabel lblVersion;
	
	/**
	 * Launch the Dialog.
	 */
	public static void open(AppFrame frame) 
	{
		try {
			
			AboutDialog dialog = new AboutDialog(frame);
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
			dialog.setLocationRelativeTo(frame);
						
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Create the dialog.
	 */
	public AboutDialog(final AppFrame frame) 
	{
		super(frame);
		
//		Image icon = new BufferedImage(1, 1,BufferedImage.TYPE_INT_ARGB_PRE);
//		setIconImage(icon);
		setTitle("About Menthor Editor");
		setBounds(100, 100, 491, 178);
		
		getContentPane().setLayout(new BorderLayout());
		
		CenterPanel = new JPanel();
		CenterPanel.setBackground(Color.WHITE);
		CenterPanel.setPreferredSize(new Dimension(150, 190));
		getContentPane().add(CenterPanel, BorderLayout.CENTER);
		
		lblVersion = new JLabel("Version: "+Main.MENTHOR_VERSION);
		lblVersion.setForeground(Color.BLACK);		
		lblVersion.setHorizontalAlignment(SwingConstants.LEFT);
		
		JLabel lblDate = new JLabel("Compilation Date: "+Main.MENTHOR_COMPILATION_DATE);				
		lblDate.setForeground(Color.BLACK);		
		lblDate.setHorizontalAlignment(SwingConstants.LEFT);
		
		label = new JLabel("");
		label.setBackground(Color.WHITE);
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setIcon(new ImageIcon(AboutDialog.class.getResource("/resources/icons/nemo100.png")));
		
		JLabel lblOntoumlLightweightEditor = new JLabel("Menthor Editor");
		lblOntoumlLightweightEditor.setForeground(Color.BLACK);
		lblOntoumlLightweightEditor.setHorizontalAlignment(SwingConstants.LEFT);
		
		JSeparator separator_1 = new JSeparator();
		
		JHyperLinkLabel lblNewLabel = new JHyperLinkLabel("");
		lblNewLabel.setText("menthor.net");
		lblNewLabel.addMouseListener(new MouseAdapter() {			
			 @Override
			    public void mouseClicked(MouseEvent e) {				 
					frame.getDiagramManager().openLinkWithBrowser("https://menthor.net");				 
			 }
		});
		
		GroupLayout gl_CenterPanel = new GroupLayout(CenterPanel);
		gl_CenterPanel.setHorizontalGroup(
			gl_CenterPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_CenterPanel.createSequentialGroup()
					.addContainerGap()
					.addComponent(label)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_CenterPanel.createParallelGroup(Alignment.LEADING, false)
						.addComponent(separator_1, GroupLayout.PREFERRED_SIZE, 311, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblOntoumlLightweightEditor, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 327, Short.MAX_VALUE)
						.addComponent(lblDate, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 327, Short.MAX_VALUE)
						.addComponent(lblVersion, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 327, Short.MAX_VALUE)
						.addComponent(lblNewLabel, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
					.addContainerGap(32, Short.MAX_VALUE))
		);
		gl_CenterPanel.setVerticalGroup(
			gl_CenterPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_CenterPanel.createSequentialGroup()
					.addGap(23)
					.addGroup(gl_CenterPanel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_CenterPanel.createSequentialGroup()
							.addComponent(lblOntoumlLightweightEditor)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(separator_1, GroupLayout.PREFERRED_SIZE, 8, GroupLayout.PREFERRED_SIZE)
							.addGap(4)
							.addComponent(lblVersion)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(lblDate)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(lblNewLabel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addComponent(label))
					.addContainerGap(136, Short.MAX_VALUE))
		);
		CenterPanel.setLayout(gl_CenterPanel);
	}
}
