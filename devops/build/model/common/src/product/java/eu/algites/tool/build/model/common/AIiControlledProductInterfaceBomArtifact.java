package eu.algites.tool.build.model.common;

import static eu.algites.tool.build.model.common.AInArtifactKind.PRODUCT_INTERFACE_BOM;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * Title: {@link AIiControlledProductInterfaceBomArtifact}
 * </p>
 * <p>
 * Description: Basic interface for the Algites PI-BOM Artifacts
 * </p>
 * <p>
 * Copyright: Copyright (c) 2026 Artur Linhart, Algites
 * </p>
 * <p>
 * Company: Algites
 * </p>
 *
 * @author linhart1
 * @date 07.01.26 14:07
 */
public interface AIiControlledProductInterfaceBomArtifact extends AIiControlledArtifact,
		AIiProductInterfaceDependenciesManager, AIiParentContainer<AIiControlledPolicyArtifact> {

	/**
	 * Gets the referenced product background BOM artifact, if assigned
	 *
	 * @return the referenced product background BOM artifact, if assigned
	 */
	AIiControlledPolicyBackgroundBomArtifact getPolicyBackgroundBom();

	@SuppressWarnings("unchecked")
	default List<AIiArtifactDependency<? extends AIiAbstractArtifact>> getManagedDependencies() {
		List<AIiArtifactDependency<? extends AIiAbstractArtifact>> locResult = null;
		if (getPolicyBackgroundBom() != null)
			locResult = getPolicyBackgroundBom().getManagedDependencies();
		if (locResult == null)
			return (List) getManagedInterfaceDependencies();
		if (getManagedInterfaceDependencies() == null)
			return locResult;

		locResult = new ArrayList<>(locResult);
		locResult.addAll(getManagedInterfaceDependencies());
		return locResult;
	}

	/**
	 * Gets the kind of the artifact {@link AInArtifactKind#PRODUCT_INTERFACE_BOM}
	 *
	 * @return the kind of the artifact
	 */
	@Override
	default AInArtifactKind getArtifactKind() {
		return PRODUCT_INTERFACE_BOM;
	}
}
