package org.opentosca.toscaengine.service.impl.resolver;

import org.opentosca.model.tosca.Definitions;

/**
 * The ServiceTemplateResolver resolves references inside of TOSCA Types
 * according to the TOSCA specification wd14. Each found element and the
 * document in which the element is nested is stored by the
 * org.opentosca.core.model.toscareferencemapping.ToscaReferenceMapper.
 * 
 * Preconditions for resolving a Types: Definitions has to be valid in all kind
 * of meanings.
 * 
 * Copyright 2012 Christian Endres
 * 
 * @author Christian Endres - endrescn@studi.informatik.uni-stuttgart.de
 * 
 */
public class TypesResolver extends GenericResolver {
	
	/**
	 * Instantiate an object of the Resolver to resolve references inside of
	 * Types. This constructor sets the ReferenceMapper which search for
	 * references.
	 * 
	 * @param referenceMapper
	 */
	public TypesResolver(ReferenceMapper referenceMapper) {
		super(referenceMapper);
	}
	
	/**
	 * Resolves a Types and stores the mapping into the ToscaReferenceMapper.
	 * 
	 * @param definitions The Definitions object.
	 * @return true if an error occurred, false if not
	 */
	public boolean resolve(Definitions def) {
		// nothing to do here
		return false;
	}
	
}
