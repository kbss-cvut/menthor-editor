package net.menthor.editor.v2.managers;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.edit.command.AddCommand;

import RefOntoUML.AggregationKind;
import RefOntoUML.Association;
import RefOntoUML.Classifier;
import RefOntoUML.Collective;
import RefOntoUML.Comment;
import RefOntoUML.Constraintx;
import RefOntoUML.DecimalMeasurementRegion;
import RefOntoUML.Element;
import RefOntoUML.Enumeration;
import RefOntoUML.EnumerationLiteral;
import RefOntoUML.IntegerMeasurementRegion;
import RefOntoUML.MeasurementDimension;
import RefOntoUML.Meronymic;
import RefOntoUML.Property;
import RefOntoUML.impl.DecimalIntervalDimensionImpl;
import RefOntoUML.impl.DecimalMeasurementRegionImpl;
import RefOntoUML.impl.DecimalOrdinalDimensionImpl;
import RefOntoUML.impl.DecimalRationalDimensionImpl;
import RefOntoUML.impl.IntegerIntervalDimensionImpl;
import RefOntoUML.impl.IntegerMeasurementRegionImpl;
import RefOntoUML.impl.IntegerOrdinalDimensionImpl;
import RefOntoUML.impl.IntegerRationalDimensionImpl;
import RefOntoUML.parser.OntoUMLParser;
import RefOntoUML.util.RefOntoUMLFactoryUtil;
import net.menthor.editor.ui.Models;
import net.menthor.editor.ui.UmlProject;
import net.menthor.editor.v2.util.RefOntoUMLEditingDomain;

public class TransferManager extends BaseManager {
	
	private static TransferManager instance = new TransferManager();
	public static TransferManager get() { return instance; }
		
	public void transferLiterals(RefOntoUML.Element element, List<EnumerationLiteral> enumLiterals){
		//to be deleted
		ArrayList<EnumerationLiteral> literals = new ArrayList<EnumerationLiteral>();		
		if(element instanceof Enumeration){
			literals.addAll(((Enumeration)element).getOwnedLiteral());
			for(EnumerationLiteral p: literals){
				if(!enumLiterals.contains(p)) {					
					((Enumeration)element).getOwnedLiteral().remove(p);
					UpdateManager.get().updateFromDeletion(p);
				}
			}
		}
		//to be added
		for(Object literal : enumLiterals){			
			EnumerationLiteral l = (EnumerationLiteral)literal;
			if(!l.getName().isEmpty()){				
				if(element instanceof Enumeration){
					((Enumeration)element).getOwnedLiteral().add(l);					
					UpdateManager.get().updateFromAddition(l);
				}				
			}
		}
	}
	
	public void transferAssociation(RefOntoUML.Association element, String name, boolean isAbstract, boolean isDerived, boolean isEssential, boolean isInseparable,
		boolean isImmutablePart, boolean isImmutableWhole, boolean isShareable, String newStereotype){
		element.setName(name);
		element.setIsAbstract(isAbstract);
		((Association)element).setIsDerived(isDerived);				
		if(element instanceof Meronymic){
			((Meronymic)element).setIsEssential(isEssential);
			((Meronymic)element).setIsInseparable(isInseparable);
			((Meronymic)element).setIsImmutablePart(isImmutablePart);
			((Meronymic)element).setIsImmutableWhole(isImmutableWhole);
			((Meronymic)element).setIsShareable(isShareable);
		}			
		UpdateManager.get().updateFromChange(element,false);		
		if(OntoUMLParser.getStereotype(element).compareTo(newStereotype)!=0){
			diagramManager.getCommandListener().handleCommand("CHANGE_TO_"+newStereotype.toUpperCase(), element);			
		}
	}
	
	public void transferNewDataTypes(List<RefOntoUML.Type> newDataTypes){
		for(Element dt: newDataTypes) {
			UmlProject project = ProjectManager.get().getProject();				
			AddCommand cmd = new AddCommand(RefOntoUMLEditingDomain.getInstance().createDomain(), project.getModel().getPackagedElement(), dt);
			RefOntoUMLEditingDomain.getInstance().createDomain().getCommandStack().execute(cmd);				
			UpdateManager.get().updateFromAddition(dt);		
		}
	}
	
	public void transferGeneralization(RefOntoUML.Generalization element, RefOntoUML.Type general, RefOntoUML.Type specific){
		boolean redesign = false;					
		if (general!=null && !general.equals(element.getGeneral())) redesign = true;
		element.setGeneral((Classifier)general);					
		if (specific!=null && !specific.equals(element.getSpecific())) redesign = true;
		element.setSpecific((Classifier)specific);		
		UpdateManager.get().updateFromChange(element, redesign);
	}
	
	public void transferDimension(RefOntoUML.Element structure, String unitOfMeasure, RefOntoUML.MeasurementDomain domain, String upperBound, String lowerBound){
		if (structure instanceof MeasurementDimension) {
			((MeasurementDimension)structure).setUnitOfMeasure(unitOfMeasure);
			((MeasurementDimension)structure).setDomain(domain);
			transferUpperRegion(structure, upperBound);		
			transferLowerRegion(structure, lowerBound);
		}		
		UpdateManager.get().updateFromChange(structure,false);
	}
	
	public void transferUpperRegion(RefOntoUML.Element structure, String upperBound){
		if(structure instanceof IntegerOrdinalDimensionImpl || structure instanceof IntegerRationalDimensionImpl || structure instanceof IntegerIntervalDimensionImpl){
			IntegerMeasurementRegion upper = new IntegerMeasurementRegionImpl();
			try{ 
				int value = Integer.parseInt(upperBound); 
				upper.setValue(value);
				((MeasurementDimension)structure).setUpperBound(upper);				
			} catch(NumberFormatException e){ 
				System.err.println(e.getLocalizedMessage());
			}							
		}
		if(structure instanceof DecimalOrdinalDimensionImpl || structure instanceof DecimalRationalDimensionImpl || structure instanceof DecimalIntervalDimensionImpl){
			DecimalMeasurementRegion upper = new DecimalMeasurementRegionImpl();
			try{ 
				double value = Double.parseDouble(upperBound); 
				upper.setValue(new BigDecimal(value));
				((MeasurementDimension)structure).setUpperBound(upper);				
			} catch(NumberFormatException e){ 
				System.err.println(e.getLocalizedMessage());
			}							
		}			
	}
	
	public void transferLowerRegion(RefOntoUML.Element structure, String lowerBound){
		if(structure instanceof IntegerOrdinalDimensionImpl || structure instanceof IntegerRationalDimensionImpl || structure instanceof IntegerIntervalDimensionImpl){
			IntegerMeasurementRegion lower = new IntegerMeasurementRegionImpl();
			try{ 
				int value = Integer.parseInt(lowerBound); 
				lower.setValue(value);
				((MeasurementDimension)structure).setLowerBound(lower);
			} catch(NumberFormatException e){ 
				System.err.println(e.getLocalizedMessage());
			}							
		}
		if(structure instanceof DecimalOrdinalDimensionImpl || structure instanceof DecimalRationalDimensionImpl || structure instanceof DecimalIntervalDimensionImpl){
			DecimalMeasurementRegion lower = new DecimalMeasurementRegionImpl();
			try{ 
				double value = Double.parseDouble(lowerBound); 
				lower.setValue(new BigDecimal(value));
				((MeasurementDimension)structure).setLowerBound(lower);
			} catch(NumberFormatException e){ 
				System.err.println(e.getLocalizedMessage());
			}							
		}			
	}	

	public void transferAttributes(RefOntoUML.Element element, List<Property> attributes){
		//attributes to be deleted (owner: datatypes)
		ArrayList<Property> currentAttrs = new ArrayList<Property>();		
		if(element instanceof RefOntoUML.DataType){
			currentAttrs.addAll(((RefOntoUML.DataType)element).getOwnedAttribute());
			for(Property p: currentAttrs){
				if(!attributes.contains(p)) {					
					((RefOntoUML.DataType)element).getOwnedAttribute().remove(p);
					UpdateManager.get().updateFromDeletion(p);
				}
			}
		}
		//attributes to be deleted (owner: classes)
		currentAttrs.clear();		
		if(element instanceof RefOntoUML.Class){
			currentAttrs.addAll(((RefOntoUML.Class)element).getOwnedAttribute());
			for(Property p: currentAttrs){
				if(!attributes.contains(p)) {					
					((RefOntoUML.Class)element).getOwnedAttribute().remove(p);
					UpdateManager.get().updateFromDeletion(p);
				}
			}
		}
		//attributes to be added
		for (Property property : attributes){			
			if(!property.getName().isEmpty() || !property.getType().getName().isEmpty()){								
				if(element instanceof RefOntoUML.DataType) ((RefOntoUML.DataType)element).getOwnedAttribute().add(property);					
				if(element instanceof RefOntoUML.Class) ((RefOntoUML.Class)element).getOwnedAttribute().add(property);
				UpdateManager.get().updateFromAddition(property);
			}
		}
	}
	
	public void transferConstraints(RefOntoUML.Element element, List<Constraintx> constraints){
		// added
		ArrayList<Constraintx> toBeAdded = new ArrayList<Constraintx>();
		for(Constraintx c: constraints){			
			if (!Models.getRefparser().getConstraints(element).contains(c)){				
				toBeAdded.add(c);
			}
		}
		for(Constraintx cmt: toBeAdded) { AdditionManager.get().addConstraintx(cmt, (RefOntoUML.Element)element); }			
		//deleted
		ArrayList<Constraintx> toBeDeleted = new ArrayList<Constraintx>();
		for(Constraintx c: Models.getRefparser().getConstraints(element)){
			if (!constraints.contains(c)){
				toBeDeleted.add(c);
			}
		}
		for(Constraintx cmt: toBeDeleted) { DeletionManager.get().deleteElement(cmt,false); }	
	}
	
	public void transferComments(RefOntoUML.Element element, List<Comment> comments){
		// added
		ArrayList<Comment> toBeAdded = new ArrayList<Comment>();
		for(Comment c: comments){
			if (!element.getOwnedComment().contains(c)){				
				toBeAdded.add(c);
			}
		}
		for(Comment cmt: toBeAdded) { AdditionManager.get().addComment(cmt, element); }			
		//deleted
		ArrayList<Comment> toBeDeleted = new ArrayList<Comment>();
		for(Comment c: element.getOwnedComment()){
			if (!comments.contains(c)){
				toBeDeleted.add(c);
			}
		}
		for(Comment cmt: toBeDeleted) { DeletionManager.get().deleteElement(cmt,false); }
	}
	
	public void transferGeneralizationSet(RefOntoUML.GeneralizationSet genSet, String name, boolean isDisjoint, boolean isComplete){
		boolean redesign = false;		
		genSet.setIsCovering(isComplete);
		genSet.setIsDisjoint(isDisjoint);
		genSet.setName(name);		
		UpdateManager.get().updateFromChange(genSet, redesign);
	}
	
	public void transferClass(RefOntoUML.Classifier element, String name, boolean isExtensional, boolean isAbstract, String newStereotype){
		element.setName(name);
		if (element instanceof Collective) ((Collective) element).setIsExtensional(isExtensional);
		element.setIsAbstract(isAbstract);		
		UpdateManager.get().updateFromChange(element,false);		
		if(OntoUMLParser.getStereotype(element).compareTo(newStereotype)!=0)	{
			ChangeManager.get().changeClassStereotype(element, newStereotype);
		}
	}
	
	/** Edit/Update Property Data */
	public void transferProperty(RefOntoUML.Property property, String name, boolean isDerived, boolean isOrdered,
		boolean isReadOnly, boolean isUnique, String aggregationKind, String multiplicity, RefOntoUML.Type type){
		boolean redesign = false;		
		try{			
			property.setName(name);
			property.setIsDerived(isDerived);
			if(isDerived) property.setName(name.replace("/",""));			
			property.setIsOrdered(isOrdered);
			property.setIsReadOnly(isReadOnly);
			property.setIsUnique(isUnique);
			if(aggregationKind.compareToIgnoreCase("shared")==0) property.setAggregation(AggregationKind.SHARED);
			else if (aggregationKind.compareToIgnoreCase("composite")==0) property.setAggregation(AggregationKind.COMPOSITE);
			else property.setAggregation(AggregationKind.NONE);			
			RefOntoUMLFactoryUtil.setMultiplicityFromString(property, multiplicity);						
			if (type!=null && !type.equals(property.getType())) redesign = true;
			property.setType(type);
		}catch(Exception e){
			System.out.println("Transfering data to property - "+ e.getLocalizedMessage());
		}
		UpdateManager.get().updateFromChange((RefOntoUML.Element)property.eContainer(), redesign);
		UpdateManager.get().updateFromChange(property, redesign);
	}
	
}