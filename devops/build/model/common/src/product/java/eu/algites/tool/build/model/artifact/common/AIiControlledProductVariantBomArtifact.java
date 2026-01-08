package eu.algites.tool.build.model.artifact.common;

import static eu.algites.tool.build.model.artifact.common.AInArtifactKind.PRODUCT_VARIANT_BOM;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * Title: {@link AIiControlledProductVariantBomArtifact}
 * </p>
 * <p>
 * Description: Basic interface for the Algites PV-BOM Artifacts
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
public interface AIiControlledProductVariantBomArtifact
		extends AIiControlledArtifact, AIiProductVariantDependenciesManager, AIiParentContainer<AIiControlledPolicyArtifact> {

	/**
	 * Gets the referenced product background BOM artifact, if assigned
	 *
	 * @return the referenced product background BOM artifact, if assigned
	 */
	AIiControlledProductInterfaceBomArtifact getProductInterfaceBom();

	@SuppressWarnings("unchecked")
	default List<AIiArtifactDependency<? extends AIiAbstractArtifact>> getManagedDependencies() {
		List<AIiArtifactDependency<? extends AIiAbstractArtifact>> locResult = null;
		if (getProductInterfaceBom() != null)
			locResult = getProductInterfaceBom().getManagedDependencies();
		if (locResult == null)
			return (List) getManagedProductVariantDependencies();
		if (getManagedProductVariantDependencies() == null)
			return locResult;

		locResult = new ArrayList<>(locResult);
		locResult.addAll(getManagedProductVariantDependencies());
		return locResult;
	}

	/**
	 * Gets the kind of the artifact {@link AInArtifactKind#PRODUCT_VARIANT_BOM}
	 *
	 * @return the kind of the artifact
	 */
	@Override
	default AInArtifactKind getArtifactKind() {
		return PRODUCT_VARIANT_BOM;
	}
}
