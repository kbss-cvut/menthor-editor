package org.tinyuml.umldraw;

import RefOntoUML.Element;
import RefOntoUML.EnumerationLiteral;
import RefOntoUML.Generalization;
import RefOntoUML.GeneralizationSet;
import RefOntoUML.NamedElement;
import RefOntoUML.Property;
import RefOntoUML.impl.PropertyImpl;
import RefOntoUML.util.RefOntoUMLFactoryUtil;

/**
 * @author Wei-ju Wu, John Guerson
 */
public class UmlLabelFormatter {

	public static String getLabelTextFor(Element namedElement)
	{
		if(namedElement instanceof PropertyImpl)
		{
			Property property = (Property) namedElement;
			String type = new String();
			if(property.getType()!=null) type = property.getType().getName();
			return property.getName() + " : " + type+"["+RefOntoUMLFactoryUtil.getMultiplicityAsString(property)+"]";			
		}
		if(namedElement instanceof Generalization)
		{
			String name = new String();
			int i=0;
			for(GeneralizationSet genSet: ((Generalization)namedElement).getGeneralizationSet()){
				if(i==((Generalization)namedElement).getGeneralizationSet().size()-1) name += genSet.getName();
				i++;
			}
			return name;
		}
		if(namedElement instanceof EnumerationLiteral){
			EnumerationLiteral literal = (EnumerationLiteral) namedElement;			
			return literal.getName();
		}
		/*else if(namedElement instanceof GeneralizationSetImpl)
		{
			GeneralizationSet genSet = (GeneralizationSet) namedElement; 
			
			String params = " {";
			if(genSet.isIsDisjoint())
				params += "disjoint";
			
			if(genSet.isIsCovering() && genSet.isIsDisjoint())
				params += ", ";
			
			if(genSet.isIsCovering())
				params += "covering";
			
			params += "}";
			
			return genSet.getName() + (genSet.isIsCovering() || genSet.isIsDisjoint() ? params : "" );
		}*/
		if(((NamedElement)namedElement)!=null){
			if (((NamedElement)namedElement).getName()==null) return "<null>";
			else return ((NamedElement)namedElement).getName();
		}else{
			return null;
		}
	}

}
