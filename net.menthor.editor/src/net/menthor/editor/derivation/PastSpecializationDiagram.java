package net.menthor.editor.derivation;

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
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultComboBoxModel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.EmptyBorder;

import org.tinyuml.umldraw.ClassElement;

public class PastSpecializationDiagram extends JDialog {

	static public final JPanel contentPanel = new JPanel();
	public static JLabel lal = new JLabel("Supertype");
	static private JTextField txt_super;
	static private JTextField txt_past;
	static JComboBox cmb_stereotype = new JComboBox();
	static JLabel lblNewLabel = new JLabel("Past Specialization");
	static ClassElement ce;
	/**
	 * Create the dialog.
	 */
	
	
	
	public PastSpecializationDiagram(ClassElement ce) {
		this.ce= ce;
		setIconImage(Toolkit.getDefaultToolkit().getImage(PastSpecializationDiagram.class.getResource("/resources/icons/x16/sitemap.png")));
		setBounds(100, 100, 362, 214);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);

		txt_super = new JTextField();
		txt_super.setColumns(10);
		txt_past = new JTextField();
		txt_past.setColumns(10);


		GroupLayout gl_contentPanel = new GroupLayout(contentPanel);
		gl_contentPanel.setHorizontalGroup(
			gl_contentPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPanel.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.LEADING, false)
						.addComponent(lal)
						.addComponent(lblNewLabel)
						.addComponent(txt_super, GroupLayout.DEFAULT_SIZE, 183, Short.MAX_VALUE)
						.addComponent(txt_past, GroupLayout.PREFERRED_SIZE, 210, GroupLayout.PREFERRED_SIZE))
					.addGap(18)
					.addComponent(cmb_stereotype, GroupLayout.PREFERRED_SIZE, 77, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(21, Short.MAX_VALUE))
		);
		cmb_stereotype.setModel(new DefaultComboBoxModel(new String[] {"Kind", "SubKind", "Quantity", "Collective", "Role", "Phase"}));
		gl_contentPanel.setVerticalGroup(
			gl_contentPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPanel.createSequentialGroup()
					.addContainerGap()
					.addComponent(lal)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(txt_super, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(cmb_stereotype, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(13)
					.addComponent(lblNewLabel)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(txt_past, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(18, Short.MAX_VALUE))
		);
		contentPanel.setLayout(gl_contentPanel);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						DerivedTypesOperations.createPastSpecializationDerivation();
						dispose();
					}
				});
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
	}
	public static ClassElement getCe() {
		return ce;
	}
	public static String getTxt_super() {
		return txt_super.getText();
	}
	public static void setTxt_super(JTextField txt_super) {
		PastSpecializationDiagram.txt_super = txt_super;
	}
	public static String getTxt_past() {
		return txt_past.getText();
	}
	public static void setTxt_past(JTextField txt_past) {
		PastSpecializationDiagram.txt_past = txt_past;
	}
	public static String getCmb_stereotype() {
		return cmb_stereotype.getSelectedItem().toString();
	}
	public static void setCmb_stereotype(JComboBox cmb_stereotype) {
		PastSpecializationDiagram.cmb_stereotype = cmb_stereotype;
	}

	

	
	
	
}
