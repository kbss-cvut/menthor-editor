package net.menthor.editor.v2.menu;

import javax.swing.ButtonGroup;

import org.tinyuml.umldraw.AssociationElement;

import RefOntoUML.util.RefOntoUMLFactoryUtil;
import net.menthor.editor.v2.commands.CommandListener;
import net.menthor.editor.v2.commands.CommandType;

public class SourceMultiplicityMenu extends MultiplicityMenu {

	private static final long serialVersionUID = 3797953970276009760L;
		
	public SourceMultiplicityMenu(CommandListener listener, String text){
		super(listener, text);
		build();
	}
	
	public SourceMultiplicityMenu(CommandListener listener){
		super(listener, "Multiplicity on Source");		
		build();
  	}
	
	public void build(){
		optional = createRadioMenuItem("0..1", CommandType.OPTIONAL_ON_SOURCE);
		singular = createRadioMenuItem("1", CommandType.SINGULAR_ON_SOURCE);
		any = createRadioMenuItem("0..*", CommandType.ANY_ON_SOURCE);
		some = createRadioMenuItem("1..*", CommandType.SOME_ON_SOURCE);
		two = createRadioMenuItem("2", CommandType.TWO_ON_SOURCE);
		twoAtLeast = createRadioMenuItem("2..*", CommandType.TWO_AT_LEAST_ON_SOURCE);
		other = createRadioMenuItem("Other", CommandType.OTHER_ON_SOURCE);
		group = new ButtonGroup();
		group.add(optional);
		group.add(singular);
		group.add(any);
		group.add(some);
		group.add(two);
		group.add(twoAtLeast);
		group.add(other);
		sort();
	}	
	
	@Override
	public void setContext(Object context){		
		this.context = context;	
		if(context instanceof AssociationElement){
			RefOntoUML.Association rel = (RefOntoUML.Association)((AssociationElement)context).getRelationship();
			String mult = RefOntoUMLFactoryUtil.getMultiplicityAsString(rel.getMemberEnd().get(0));
			if(mult.equals("0..1")) optional.setSelected(true);
			else if(mult.equals("1")) singular.setSelected(true);
			else if(mult.equals("1..*")) some.setSelected(true);
			else if(mult.equals("0..*") || mult.equals("*")) any.setSelected(true);
			else if(mult.equals("2")) two.setSelected(true);
			else if(mult.equals("2..*")) twoAtLeast.setSelected(true);
			else other.setSelected(true);		
		}
	}
}