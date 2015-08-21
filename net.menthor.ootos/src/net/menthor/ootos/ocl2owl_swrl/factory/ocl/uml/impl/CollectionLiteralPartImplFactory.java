package net.menthor.ootos.ocl2owl_swrl.factory.ocl.uml.impl;

import net.menthor.common.settings.owl.OwlOptions;
import net.menthor.ootos.ocl2owl_swrl.factory.uml2.uml.internal.impl.TypedElementImplFactory;
import net.menthor.ootos.util.MappingProperties;

import org.eclipse.uml2.uml.internal.impl.NamedElementImpl;

/**
 * @author Freddy Brasileiro Silva {freddybrasileiro@gmail.com}
 */
public class CollectionLiteralPartImplFactory extends TypedElementImplFactory {

	public CollectionLiteralPartImplFactory(MappingProperties mappingProperties, OwlOptions owlOptions, NamedElementImpl m_NamedElementImpl){
		super(mappingProperties, owlOptions, m_NamedElementImpl);	
	}

}