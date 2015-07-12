package net.menthor.editor.popupmenu;

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

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Locale;

import javax.swing.ImageIcon;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.KeyStroke;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;

import net.menthor.editor.AppFrame;
import net.menthor.editor.Main;
import net.menthor.editor.dialog.DiagramListDialog;
import net.menthor.editor.explorer.ProjectBrowser;
import net.menthor.editor.explorer.ProjectTree;
import net.menthor.editor.model.ElementType;
import net.menthor.editor.model.OCLDocument;
import net.menthor.editor.model.RelationType;
import net.menthor.editor.ui.DiagramEditorWrapper;

import org.tinyuml.ui.diagram.DiagramEditor;
import org.tinyuml.umldraw.StructureDiagram;

import RefOntoUML.Association;
import RefOntoUML.Element;
import RefOntoUML.EnumerationLiteral;
import RefOntoUML.Generalization;
import RefOntoUML.Property;
import RefOntoUML.Type;
import RefOntoUML.util.RefOntoUMLElement;

/**
 * @author John Guerson
 */
public class TreePopupMenu extends JPopupMenu {
 
	private static final long serialVersionUID = 1L;
	public Object element;
	public AppFrame frame;
	public ProjectTree tree;
	public DefaultMutableTreeNode selectedNode;
	
	public void createRefreshItem()
	{    	
		JMenuItem refreshItem = new JMenuItem("Refresh");
		add(refreshItem);
		refreshItem.addActionListener(new ActionListener() {				
			@Override
			public void actionPerformed(ActionEvent e) {    				
				frame.getBrowserManager().getProjectBrowser().refreshTree();
			}
		});    	    	
	}
	
	public void createCompleteItem()
	{
		JMenuItem autoCompleteItem = new JMenuItem("Complete selection");
    	add(autoCompleteItem);	    	
    	autoCompleteItem.addActionListener(new ActionListener() {				
			@Override
			public void actionPerformed(ActionEvent e) {
				
				frame.getDiagramManager().getEditorDispatcher().autoComplete();
			}
		});
	}
	
	public void createAddDiagramItem()
	{
		JMenuItem addDiagramItem = new JMenuItem("Add Diagram");
		add(addDiagramItem);
		addDiagramItem.addActionListener(new ActionListener() {				
			@Override
			public void actionPerformed(ActionEvent e) {
				frame.getDiagramManager().newDiagram();    				
			}
		});
	}
	
	public void createAddOCLDocumentItem()
	{
		JMenuItem addOCLDocumentItem = new JMenuItem("Add Document");
		add(addOCLDocumentItem);
		addOCLDocumentItem.addActionListener(new ActionListener() {				
			@Override
			public void actionPerformed(ActionEvent e) {
				frame.getDiagramManager().newOCLDocument(true);    				
			}
		});
	}
	
	public void createRenameItem()
	{
		JMenuItem setNameItem = new JMenuItem("Rename");
		add(setNameItem);    		
		setNameItem.addActionListener(new ActionListener() {				
			@Override
			public void actionPerformed(ActionEvent e) {
				if (TreePopupMenu.this.element instanceof RefOntoUMLElement)
				{    					
					RefOntoUML.Element element = (RefOntoUML.Element)((RefOntoUMLElement)TreePopupMenu.this.element).getElement();    					
					ProjectBrowser.frame.getDiagramManager().renameElement(element);					
				}
				else if (TreePopupMenu.this.element instanceof StructureDiagram)
				{
					ProjectBrowser.frame.getDiagramManager().renameDiagram((StructureDiagram)element);					
				}
				else if (TreePopupMenu.this.element instanceof OCLDocument)
				{
					ProjectBrowser.frame.getDiagramManager().renameOCLDocument((OCLDocument)element);					
				}
			}
		});
	}
	
	public void createMoveToDiagramItem()
	{
		final RefOntoUMLElement ontoElement = ((RefOntoUMLElement)selectedNode.getUserObject());  
		JMenuItem moveToDiagramItem = new JMenuItem("Move to Diagram");
		add(moveToDiagramItem);    			    			
		moveToDiagramItem.addActionListener(new ActionListener() {				
			@Override
			public void actionPerformed(ActionEvent e) {
				DiagramEditor d = frame.getDiagramManager().getCurrentDiagramEditor();
				frame.getDiagramManager().moveToDiagram((RefOntoUML.Element)ontoElement.getElement(), d);        			
			};
		});    
	}
	
	public void createClassChangeItem()
	{
		final RefOntoUMLElement ontoElement = ((RefOntoUMLElement)selectedNode.getUserObject());  
		final Type type = (Type)ontoElement.getElement();
		ClassStereotypeChangeMenu changeMenu = new ClassStereotypeChangeMenu(frame.getDiagramManager());
		changeMenu.setElement(type);
		add(changeMenu);	
	}
	
	public void createRelationChangeItem()
	{
		final RefOntoUMLElement ontoElement = ((RefOntoUMLElement)selectedNode.getUserObject()); 
		final Association type = (Association)ontoElement.getElement();    			
		RelationStereotypeChangeMenu changeMenu = new RelationStereotypeChangeMenu(frame.getDiagramManager());
		changeMenu.setElement(type);
		add(changeMenu);
	}
	
	public void createAddElementItem()
	{
		RefOntoUMLElement ontoElement = ((RefOntoUMLElement)selectedNode.getUserObject());		
		final RefOntoUML.Package eContainer = (RefOntoUML.Package)ontoElement.getElement();
		ElementCreationMenu addElementMenu = new ElementCreationMenu(frame.getDiagramManager(),eContainer);
		add(addElementMenu);
	}
	
	public void createAddRelationItem()
	{
		RefOntoUMLElement ontoElement = ((RefOntoUMLElement)selectedNode.getUserObject());
		final RefOntoUML.Package eContainer = (RefOntoUML.Package)ontoElement.getElement();
		RelationCreationMenu addRelationMenu = new RelationCreationMenu(frame.getDiagramManager(),eContainer);
		add(addRelationMenu);
	}
	
	public void createAddContainedItem()
	{
		RefOntoUMLElement ontoElement = ((RefOntoUMLElement)selectedNode.getUserObject());
		final RefOntoUML.Type eContainer = (RefOntoUML.Type)ontoElement.getElement();
		JMenu addItem = new JMenu("Add");
		JMenuItem addGenItem = new JMenuItem("Generalization");
		JMenuItem addCommentItem = new JMenuItem("Comment");
		JMenuItem addConstraintItem = new JMenuItem("Constraintx");
		addItem.add(addGenItem);    			
		addItem.add(addCommentItem);
		addItem.add(addConstraintItem);
		add(addItem);
		addGenItem.addActionListener(new ActionListener() {				
	        	@Override
	        	public void actionPerformed(ActionEvent e) {
	        		frame.getDiagramManager().addRelation(RelationType.GENERALIZATION,eContainer);
	        	}
	        });
		addCommentItem.addActionListener(new ActionListener() {				
	        	@Override
	        	public void actionPerformed(ActionEvent e) {
	        		frame.getDiagramManager().addElement(ElementType.COMMENT,eContainer);
	        	}
	        });
		addConstraintItem.addActionListener(new ActionListener() {				
	        	@Override
	        	public void actionPerformed(ActionEvent e) {
	        		frame.getDiagramManager().addElement(ElementType.CONSTRAINT,eContainer);
	        	}
	        });
		addGenItem.setIcon(new ImageIcon(DiagramEditorWrapper.class.getResource("/resources/icons/x16/tree/generalization.png")));
		addCommentItem.setIcon(new ImageIcon(DiagramEditorWrapper.class.getResource("/resources/icons/x16/tree/comment.png")));
		addConstraintItem.setIcon(new ImageIcon(DiagramEditorWrapper.class.getResource("/resources/icons/x16/tree/constraintx.png")));
	}
	
	public void createInvertItem()
	{
		RefOntoUMLElement ontoElement = ((RefOntoUMLElement)selectedNode.getUserObject());
		final Association association = (Association)ontoElement.getElement();
		
		JMenu invertMenu = new JMenu("Invert");
		add(invertMenu);
			
		JMenuItem invertEndPointsItem = new JMenuItem("End Points");
		invertMenu.add(invertEndPointsItem);    			
		invertEndPointsItem.addActionListener(new ActionListener() {				
        	@Override
        	public void actionPerformed(ActionEvent e) {
        		frame.getDiagramManager().invertEndPoints(association);
        	}
        });
		
		JMenuItem invertEndNamesItem = new JMenuItem("End Names");
		invertMenu.add(invertEndNamesItem);    			
		invertEndNamesItem.addActionListener(new ActionListener() {				
        	@Override
        	public void actionPerformed(ActionEvent e) {
        		frame.getDiagramManager().invertEndNames(association);
        	}
        });
		
		JMenuItem invertEndMultiplicitiesItem = new JMenuItem("End Multiplicities");
		invertMenu.add(invertEndMultiplicitiesItem);    			
		invertEndMultiplicitiesItem.addActionListener(new ActionListener() {				
        	@Override
        	public void actionPerformed(ActionEvent e) {
        		frame.getDiagramManager().invertEndMultiplicities(association);
        	}
        });
		
		JMenuItem invertEndTypesItem = new JMenuItem("End Types");
		invertMenu.add(invertEndTypesItem);    			
		invertEndTypesItem.addActionListener(new ActionListener() {				
        	@Override
        	public void actionPerformed(ActionEvent e) {
        		frame.getDiagramManager().invertEndTypes(association);
        	}
        });		
	}
	
	/** Returns true iff running on Mac OS X. **/
	public static boolean onMac() {
      return System.getProperty("mrj.version")!=null || System.getProperty("os.name").toLowerCase(Locale.US).startsWith("mac ");                                     
	}
	
	public void createDeleteItem()
	{
		JMenuItem deleteItem = new JMenuItem("Delete");
		if(onMac()){
			deleteItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_DELETE, ActionEvent.META_MASK));			
		}else{
			deleteItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_DELETE, ActionEvent.CTRL_MASK));				
		}
		add(deleteItem);
		deleteItem.setIcon(new ImageIcon(TreePopupMenu.class.getResource("/resources/icons/x16/cross.png")));
		deleteItem.addActionListener(new ActionListener() {				
			@Override
			public void actionPerformed(ActionEvent e) {			
				if(!tree.isFocusable())return;
				if (TreePopupMenu.this.element instanceof RefOntoUMLElement)
				{
					RefOntoUMLElement ontoElem = (RefOntoUMLElement) ((DefaultMutableTreeNode)tree.getSelectionPath().getLastPathComponent()).getUserObject();
					RefOntoUML.Element elemForDeletion = (RefOntoUML.Element)ontoElem.getElement();
					frame.getDiagramManager().deleteFromMenthor(elemForDeletion,true);    					    					
    				tree.setSelectionPath(new TreePath(tree.getModelRootNode().getPath()));    					    					
				}
				else if (TreePopupMenu.this.element instanceof StructureDiagram)
				{
					frame.getDiagramManager().deleteDiagram((StructureDiagram)TreePopupMenu.this.element);    					
				}
				else if (TreePopupMenu.this.element instanceof OCLDocument)
				{
					frame.getDiagramManager().deleteOCLDocument((OCLDocument)TreePopupMenu.this.element);    					
				}
			}
		});
	}
	
	public void createFinInDiagramItem()
	{
		final RefOntoUMLElement ontoElement = ((RefOntoUMLElement)selectedNode.getUserObject());
		JMenuItem findDiagramItem = new JMenuItem("Find in Diagrams");
		add(findDiagramItem);		
		findDiagramItem.addActionListener(new ActionListener() {				
			@Override
			public void actionPerformed(ActionEvent e) {							
				ArrayList<DiagramEditor> diagrams = ProjectBrowser.frame.getDiagramManager().getDiagramEditors((Element)ontoElement.getElement());
				DiagramListDialog.open(ProjectBrowser.frame, diagrams,(Element)ontoElement.getElement());
			}
		});
	}
	
    public TreePopupMenu(final AppFrame frame, final ProjectTree tree, Object element)
    {       
    	this.element = element; 
    	this.frame = frame;
    	this.tree = tree;
    	
    	DefaultMutableTreeNode node = ((DefaultMutableTreeNode)tree.getSelectionPath().getLastPathComponent());
    	this.selectedNode = node;
    	
    	// Root Node: Application Project
    	if (tree.getRootNode().equals(selectedNode)) {
    		createRefreshItem();  
    		return;
    	} 
    	
    	// Model Node: Model
    	if (tree.getModelRootNode().equals(selectedNode)) {
    		createAddElementItem();
			createAddRelationItem();
    		//createCompleteItem();
    		return;
    	} 
    	
    	// Diagram Node: Diagrams
    	if(tree.getDiagramRootNode().equals(selectedNode)) {
    		createAddDiagramItem();
    		return;
    	} 

    	if(tree.getConstraintRootNode().equals(selectedNode)){
    		createAddOCLDocumentItem();
    		return;
    	}
    	
    	// Diagrams...
    	if ((TreePopupMenu.this.element instanceof StructureDiagram)) {
    		createRenameItem();
    	}
    	
    	// OCL Documents...
    	if (TreePopupMenu.this.element instanceof OCLDocument) {
    		createRenameItem(); 
    		createDeleteItem();
    	}
    	
    	// Model Elements...
    	
    	if ((!(TreePopupMenu.this.element instanceof StructureDiagram)) && (!(TreePopupMenu.this.element instanceof OCLDocument)) && 
    		!((RefOntoUML.Element)((RefOntoUMLElement)TreePopupMenu.this.element).getElement() instanceof Generalization)) {
    		createRenameItem();
    	}
    	
    	if(selectedNode.getUserObject() instanceof StructureDiagram)
    	{
    		createDeleteItem();
    	}
    	
		if (selectedNode.getUserObject() instanceof RefOntoUMLElement)
		{
    		final RefOntoUMLElement ontoElement = ((RefOntoUMLElement)selectedNode.getUserObject());    		
    		if(ontoElement.getElement() instanceof RefOntoUML.Type || ontoElement.getElement() instanceof RefOntoUML.Generalization)
    		{
    			createMoveToDiagramItem();
    			createFinInDiagramItem();
    		}		   		
    		if(ontoElement.getElement() instanceof RefOntoUML.Class)
    		{    			
    			createAddContainedItem();
    			createClassChangeItem();    			
    		}
    		if((ontoElement.getElement() instanceof RefOntoUML.DataType) && !(ontoElement.getElement() instanceof RefOntoUML.PrimitiveType) && !(ontoElement.getElement() instanceof RefOntoUML.Enumeration))
    		{
    			createAddContainedItem();
    			createClassChangeItem();    			 	
    		}
    		if(ontoElement.getElement() instanceof RefOntoUML.Enumeration)
    		{    			
    			createAddContainedItem();
    		}
    		if(ontoElement.getElement() instanceof RefOntoUML.PrimitiveType)
    		{    			
    			    			 	
    		}
    		if(ontoElement.getElement() instanceof RefOntoUML.Association)
    		{ 
    			createRelationChangeItem();
    			createInvertItem();
    		}
    		if(ontoElement.getElement() instanceof RefOntoUML.Package)
    		{
    			createAddElementItem();
    			createAddRelationItem();
    		}
    		if (!(ontoElement.getElement() instanceof Property) && !(ontoElement.getElement() instanceof EnumerationLiteral))
    		{
    			createDeleteItem();
    		} 
		}    	
    }
}
