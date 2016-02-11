package net.menthor.editor.v2.ui.menu;

import javax.swing.JPopupMenu;

import org.eclipse.emf.ecore.EObject;

import RefOntoUML.Relationship;

import net.menthor.editor.v2.commands.ICommandListener;
import net.menthor.editor.v2.commands.CommandType;
import net.menthor.editor.v2.types.ClassType;
import net.menthor.editor.v2.types.DataType;
import net.menthor.editor.v2.types.RelationshipType;
import net.menthor.editor.v2.ui.generic.GenericMenu;
import net.menthor.editor.v2.ui.icon.IconType;

public class ChangeStereotypeMenu extends GenericMenu<EObject> {

	private static final long serialVersionUID = 3797953970276009760L;
	
	public ChangeStereotypeMenu(ICommandListener listener, EObject element, JPopupMenu parent){
		this(listener, "Change Stereotype To", element,parent);
	}
	
	public ChangeStereotypeMenu(ICommandListener listener, String text, EObject element, JPopupMenu parent){
		super(listener, text, element);			
		if(element instanceof RefOntoUML.Class){
			createMenuItem(ClassType.KIND.getName(), IconType.MENTHOR_CLASS, CommandType.CHANGE_TO_KIND);
			createMenuItem(ClassType.SUBKIND.getName(), IconType.MENTHOR_CLASS, CommandType.CHANGE_TO_SUBKIND);
			createMenuItem(ClassType.COLLECTIVE.getName(), IconType.MENTHOR_CLASS, CommandType.CHANGE_TO_COLLECTIVE);
			createMenuItem(ClassType.QUANTITY.getName(), IconType.MENTHOR_CLASS, CommandType.CHANGE_TO_QUANTITY);
			createMenuItem(ClassType.PHASE.getName(), IconType.MENTHOR_CLASS, CommandType.CHANGE_TO_PHASE);
			createMenuItem(ClassType.ROLE.getName(), IconType.MENTHOR_CLASS, CommandType.CHANGE_TO_ROLE);
			createMenuItem(ClassType.CATEGORY.getName(), IconType.MENTHOR_CLASS, CommandType.CHANGE_TO_CATEGORY);
			createMenuItem(ClassType.ROLEMIXIN.getName(), IconType.MENTHOR_CLASS, CommandType.CHANGE_TO_ROLEMIXIN);
			createMenuItem(ClassType.MIXIN.getName(), IconType.MENTHOR_CLASS, CommandType.CHANGE_TO_MIXIN);
			createMenuItem(ClassType.RELATOR.getName(), IconType.MENTHOR_CLASS, CommandType.CHANGE_TO_RELATOR);
			createMenuItem(ClassType.MODE.getName(), IconType.MENTHOR_CLASS, CommandType.CHANGE_TO_MODE);
			createMenuItem(ClassType.PERCEIVABLE_QUALITY.getName(), IconType.MENTHOR_CLASS, CommandType.CHANGE_TO_PERCEIVABLE_QUALITY);
			createMenuItem(ClassType.NONPERCEIVABLE_QUALITY.getName(), IconType.MENTHOR_CLASS, CommandType.CHANGE_TO_NONPERCEIVABLE_QUALITY);
			createMenuItem(ClassType.NOMINAL_QUALITY.getName(), IconType.MENTHOR_CLASS, CommandType.CHANGE_TO_NOMINAL_QUALITY);
		}
		else if (element instanceof Relationship){
			createMenuItem(RelationshipType.GENERALIZATION.getName(), IconType.MENTHOR_GENERALIZATION, CommandType.CHANGE_TO_GENERALIZATION);
			createMenuItem(RelationshipType.CHARACTERIZATION.getName(), IconType.MENTHOR_ASSOCIATION, CommandType.CHANGE_TO_CHARACTERIZATION);
			createMenuItem(RelationshipType.MEDIATION.getName(), IconType.MENTHOR_ASSOCIATION, CommandType.CHANGE_TO_MEDIATION);
			createMenuItem(RelationshipType.FORMAL.getName(), IconType.MENTHOR_ASSOCIATION, CommandType.CHANGE_TO_FORMAL);
			createMenuItem(RelationshipType.MATERIAL.getName(), IconType.MENTHOR_ASSOCIATION, CommandType.CHANGE_TO_MATERIAL);
			createMenuItem(RelationshipType.DERIVATION.getName(), IconType.MENTHOR_DERIVATION, CommandType.CHANGE_TO_DERIVATION);
			createMenuItem(RelationshipType.COMPONENTOF.getName(), IconType.MENTHOR_COMPONENTOF, CommandType.CHANGE_TO_COMPONENTOF);
			createMenuItem(RelationshipType.MEMBEROF.getName(), IconType.MENTHOR_MEMBEROF, CommandType.CHANGE_TO_MEMBEROF);
			createMenuItem(RelationshipType.SUBCOLLECTIONOF.getName(), IconType.MENTHOR_SUBCOLLECTIONOF, CommandType.CHANGE_TO_SUBCOLLECTIONOF);
			createMenuItem(RelationshipType.SUBQUANTITYOF.getName(), IconType.MENTHOR_SUBQUANTITYOF, CommandType.CHANGE_TO_SUBQUANTITYOF);
			createMenuItem(RelationshipType.STRUCTURATION.getName(), IconType.MENTHOR_ASSOCIATION, CommandType.CHANGE_TO_STRUCTURATION);
			createMenuItem(RelationshipType.ASSOCIATION.getName(), IconType.MENTHOR_ASSOCIATION, CommandType.CHANGE_TO_ASSOCIATION);
		}
		else if (element instanceof RefOntoUML.DataType){
			createMenuItem(DataType.DATATYPE.getName(), IconType.MENTHOR_CLASS, CommandType.CHANGE_TO_DATATYPE);
			createMenuItem(DataType.ENUMERATION.getName(), IconType.MENTHOR_CLASS, CommandType.CHANGE_TO_ENUMERATION);
			createMenuItem(DataType.PRIMITIVETYPE.getName(), IconType.MENTHOR_CLASS, CommandType.CHANGE_TO_PRIMITIVETYPE);
			createMenuItem(DataType.DECIMALINTERVAL_DIMENSION.getName(), IconType.MENTHOR_CLASS, CommandType.CHANGE_TO_DECIMALINTERVAL_DIMENSION);
			createMenuItem(DataType.DECIMALORDINAL_DIMENSION.getName(), IconType.MENTHOR_CLASS, CommandType.CHANGE_TO_DECIMALORDINAL_DIMENSION);
			createMenuItem(DataType.DECIMALRATIONAL_DIMENSION.getName(), IconType.MENTHOR_CLASS, CommandType.CHANGE_TO_DECIMALRATIONAL_DIMENSION);
			createMenuItem(DataType.INTEGERINTERVAL_DIMENSION.getName(), IconType.MENTHOR_CLASS, CommandType.CHANGE_TO_INTEGERINTERVAL_DIMENSION);
			createMenuItem(DataType.INTEGERORDINAL_DIMENSION.getName(), IconType.MENTHOR_CLASS, CommandType.CHANGE_TO_INTEGERORDINAL_DIMENSION);
			createMenuItem(DataType.INTEGERRATIONAL_DIMENSION.getName(), IconType.MENTHOR_CLASS, CommandType.CHANGE_TO_INTEGERRATIONAL_DIMENSION);
			createMenuItem(DataType.STRINGNOMINAL_STRUCTURE.getName(), IconType.MENTHOR_CLASS, CommandType.CHANGE_TO_STRINGNOMINAL_STRUCTURE);
			createMenuItem(DataType.MEASUREMENT_DOMAIN.getName(), IconType.MENTHOR_CLASS, CommandType.CHANGE_TO_MEASUREMENT_DOMAIN);
		}
		sort();		
		parent.add(this);
	}
}