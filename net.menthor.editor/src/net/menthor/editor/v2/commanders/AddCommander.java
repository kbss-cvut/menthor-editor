
package net.menthor.editor.v2.commanders;

import java.util.List;

import javax.swing.tree.DefaultMutableTreeNode;

import org.eclipse.emf.ecore.EObject;
import org.tinyuml.draw.DiagramElement;
import org.tinyuml.ui.diagram.OntoumlEditor;
import org.tinyuml.ui.diagram.commands.AddConnectionCommand;
import org.tinyuml.ui.diagram.commands.AddGeneralizationSetCommand;
import org.tinyuml.ui.diagram.commands.AddNodeCommand;
import org.tinyuml.umldraw.StructureDiagram;

import RefOntoUML.Constraintx;
import RefOntoUML.Generalization;
import RefOntoUML.GeneralizationSet;
import RefOntoUML.Package;
import RefOntoUML.StringExpression;
import RefOntoUML.parser.OntoUMLParser;

import net.menthor.editor.ui.UmlProject;
import net.menthor.editor.v2.OclDocument;
import net.menthor.editor.v2.managers.FactoryManager;
import net.menthor.editor.v2.managers.ProjectManager;
import net.menthor.editor.v2.types.ClassType;
import net.menthor.editor.v2.types.DataType;
import net.menthor.editor.v2.types.RelationshipType;
import net.menthor.editor.v2.ui.manager.BrowserUIManager;
import net.menthor.editor.v2.ui.manager.MessageUIManager;
import net.menthor.editor.v2.ui.manager.SplitPaneUIManager;
import net.menthor.editor.v2.ui.manager.TabUIManager;
import net.menthor.editor.v2.util.Util;

public class AddCommander {

	// -------- Lazy Initialization
	
	private static class AdditionLoader {
        private static final AddCommander INSTANCE = new AddCommander();
    }	
	public static AddCommander get() { 
		return AdditionLoader.INSTANCE; 
	}	
    private AddCommander() {
        if (AdditionLoader.INSTANCE != null) throw new IllegalStateException("AdditionCommander already instantiated");
    }		
    
    // ----------------------------
	
	private boolean confirmGenSetAddition(){
		return MessageUIManager.get().confirm("Add Generalization Set",
			"There is already a generalization set in the selected generalizations.\nAre you sure you want to continue?"
		);
	}
	
	/** Add relationship to the model. */
	public RefOntoUML.Relationship addRelationship(RelationshipType stereotype, EObject eContainer)	{
		RefOntoUML.Relationship relationship = FactoryManager.get().createRelationship(stereotype);		
		if (stereotype==RelationshipType.GENERALIZATION) { //generalizations are owned by a type
			AddConnectionCommand cmd = new AddConnectionCommand(null,null,relationship,(RefOntoUML.Classifier)eContainer,null,null);
			cmd.run();
		}else{
			AddConnectionCommand cmd = new AddConnectionCommand(null,null,relationship,null,null,eContainer);
			cmd.run();
		}
		return relationship;
	}
	
	/** Add comment to the model. */
	public RefOntoUML.Comment addComment(RefOntoUML.Element eContainer){
		RefOntoUML.Comment comment = FactoryManager.get().createComment();
		AddNodeCommand cmd = new AddNodeCommand(null,null,comment,0,0,eContainer);		
		cmd.run();
		return comment;
	}
	
	/** Add comment to the model */
	public void addComment(RefOntoUML.Comment c, RefOntoUML.Element eContainer){
		AddNodeCommand cmd = new AddNodeCommand(null,null,c,0,0,eContainer);		
		cmd.run();
	}
	
	/** Add package to the model.  */
	public void addPackage(DefaultMutableTreeNode node){		
		if(!(node.getUserObject() instanceof RefOntoUML.Package)) return;		
		RefOntoUML.Package container = (RefOntoUML.Package) (node.getUserObject()) ;
		RefOntoUML.Element newPackage = FactoryManager.get().createPackage();
		AddNodeCommand cmd = new AddNodeCommand(null,null,newPackage,0,0,container);		
		cmd.run();
	}
	
	/** Add constraint to the model */
	public void addConstraintx(Constraintx cmt, RefOntoUML.Element eContainer){
		AddNodeCommand cmd = new AddNodeCommand(null,null,cmt,0,0,eContainer);		
		cmd.run();
	}
	
	/** Add constraint to the model */
	public void addConstraintx(RefOntoUML.Element eContainer){
		addConstraintx("",eContainer);
	}
	
	/** Add constraint to the model*/
	public void addConstraintx(String text, RefOntoUML.Element eContainer){
		RefOntoUML.Constraintx element = FactoryManager.get().createConstraintx();
		((StringExpression)element.getSpecification()).setSymbol(text);
		AddNodeCommand cmd = new AddNodeCommand(null,null,element,0,0,eContainer);		
		cmd.run();				
	}
	
	/** Add generalization set to the model  */
	public RefOntoUML.Element addGeneralizationSet(RefOntoUML.Element eContainer){
		RefOntoUML.Element element = FactoryManager.get().createGeneralizationSet();		
		AddNodeCommand cmd = new AddNodeCommand(null,null,element,0,0,eContainer);		
		cmd.run();		
		return element;
	}
	
	/** Add class to the model */
	public RefOntoUML.Element addClass(ClassType stereotype, RefOntoUML.Element eContainer){	
		RefOntoUML.Element element = FactoryManager.get().createClass(stereotype);		
		AddNodeCommand cmd = new AddNodeCommand(null,null,element,0,0,eContainer);		
		cmd.run();		
		return element;
	}
	
	/** Add datatype to the model */
	public RefOntoUML.Element addDataType(DataType stereotype, RefOntoUML.Element eContainer){
		RefOntoUML.Element element = FactoryManager.get().createDataType(stereotype);		
		AddNodeCommand cmd = new AddNodeCommand(null,null,element,0,0,eContainer);		
		cmd.run();		
		return element;
	}
	
	/** Add generalization set to generalization diagram elements */
	public GeneralizationSet addGeneralizationSet(OntoumlEditor d, List<DiagramElement> diagramElements){		
		UmlProject project = ProjectManager.get().getProject();
		List<Generalization> gens = d.getGeneralizations(diagramElements);
		boolean haveGenSet = OntoUMLParser.haveGeneralizationSet(gens);		
		if(gens.size()<1) return null;		
		if(haveGenSet){
			if(!confirmGenSetAddition()) return null;
		}		
		EObject eContainer = null;
		if(gens.size()>1) eContainer = gens.get(0).getSpecific().eContainer();	
		else eContainer = project.getModel();
		RefOntoUML.GeneralizationSet newgenset = (GeneralizationSet)FactoryManager.get().createGeneralizationSet();
		((RefOntoUML.Package)eContainer).getPackagedElement().add(newgenset);
		((GeneralizationSet)newgenset).setIsCovering(true);
		((GeneralizationSet)newgenset).setIsDisjoint(true);
		((GeneralizationSet)newgenset).setName("gs");
		new AddGeneralizationSetCommand(d, d.getDiagram(), newgenset, gens, project.getModel()).run();
		return (GeneralizationSet)newgenset;
	}
	
	/** New ocl document */
	public void newOclDocument(){
		newOclDocument(null, false);		
	}
	
	/** New ocl document */
	public void newOclDocument(String oclcontent, boolean createTab){
		addOclDocument(null, oclcontent, createTab);		
	}
	
	/** Add ocl document to a container */
	public void addOclDocument(Object treeNode){
		addOclDocument(treeNode, "", false);
	}
	
	/** Add ocl document to a container */
	public void addOclDocument(Object treeNode, String oclContent, boolean createTab){				
		if(treeNode==null || !(treeNode instanceof DefaultMutableTreeNode) || !(((DefaultMutableTreeNode)treeNode).getUserObject() instanceof Package)){
			treeNode = BrowserUIManager.get().root();
		}
		OclDocument oclDoc = new OclDocument();
		Package pack = (Package) ((DefaultMutableTreeNode) treeNode).getUserObject();
		oclDoc.setContainer(pack);		
		if(oclContent!=null) oclDoc.setContentAsString(oclContent);
		oclDoc.setName("OclDocument"+ProjectManager.get().getProject().getOclDocList().size());		
		ProjectManager.get().getProject().getOclDocList().add(oclDoc);		
		BrowserUIManager.get().add((DefaultMutableTreeNode)treeNode, oclDoc);		
		if(createTab) TabUIManager.get().addOclEditor(oclDoc);
	}
	
	public void newDiagram(){
		addDiagram(null);
	}

	public void addDiagram(Object treeNode){
		if(treeNode==null || !(treeNode instanceof DefaultMutableTreeNode) || !(((DefaultMutableTreeNode)treeNode).getUserObject() instanceof Package)){
			treeNode = BrowserUIManager.get().root();
		}
		StructureDiagram diagram = new StructureDiagram(ProjectManager.get().getProject());		
		Package epackage = (Package) ((DefaultMutableTreeNode) treeNode).getUserObject();
		diagram.setContainer(epackage);		
		setDefaultDiagramSize(diagram);
		diagram.setLabelText("Diagram"+ProjectManager.get().getProject().getDiagrams().size());
		ProjectManager.get().getProject().addDiagram(diagram);
		ProjectManager.get().getProject().saveDiagramNeeded(diagram,false);
		TabUIManager.get().addDiagramEditor(diagram);				
		if(treeNode!=null) BrowserUIManager.get().add((DefaultMutableTreeNode)treeNode,diagram);
	}
	
	public void setDefaultDiagramSize(StructureDiagram diagram){
		double waste = 0;
		if(SplitPaneUIManager.get().isShowProjectBrowser()) waste+=240;
		if(SplitPaneUIManager.get().isShowPalette()) waste+=240;
		diagram.setSize((Util.getScreenWorkingWidth()-waste+100)*3, (Util.getScreenWorkingHeight()-100)*3);
	}
}