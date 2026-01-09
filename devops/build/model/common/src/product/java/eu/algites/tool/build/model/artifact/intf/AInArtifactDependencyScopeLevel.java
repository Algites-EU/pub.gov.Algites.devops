package eu.algites.tool.build.model.artifact.intf;

import static eu.algites.tool.build.model.artifact.intf.AInArtifactKind.POLICY;
import static eu.algites.tool.build.model.artifact.intf.AInArtifactKind.POLICY_BACKGROUND_BOM;
import static eu.algites.tool.build.model.artifact.intf.AInArtifactKind.PRODUCT_CORE;
import static eu.algites.tool.build.model.artifact.intf.AInArtifactKind.PRODUCT_INTERFACE_BOM;
import static eu.algites.tool.build.model.artifact.intf.AInArtifactKind.PRODUCT_VARIANT_BOM;
import static eu.algites.tool.build.model.artifact.intf.AInArtifactKind.TEST_CORE;
import static eu.algites.tool.build.model.artifact.intf.AInArtifactKind.UNCONTROLLED_BOM;
import static eu.algites.tool.build.model.artifact.intf.AInArtifactKind.UNCONTROLLED_CORE;

import java.util.List;

/**
 * <p>
 * Title: {@link AInArtifactDependencyScopeLevel}
 * </p>
 * <p>
 * Description: Defines the possible scopes of the dependencies
 * </p>
 * <p>
 * Copyright: Copyright (c) 2026 Artur Linhart, Algites
 * </p>
 * <p>
 * Company: Algites
 * </p>
 *
 * @author linhart1
 * @date 07.01.26 16:02
 */
public enum AInArtifactDependencyScopeLevel {

	UNDEFINED(true, UNCONTROLLED_CORE, POLICY, PRODUCT_CORE, TEST_CORE),
	COMPILE(false, UNCONTROLLED_CORE, POLICY, PRODUCT_CORE, TEST_CORE),
	TEST(false, UNCONTROLLED_CORE, POLICY, PRODUCT_CORE, TEST_CORE),
	RUNTIME(false, UNCONTROLLED_CORE, POLICY, PRODUCT_CORE, TEST_CORE),
	PROVIDED(false, UNCONTROLLED_CORE, POLICY, PRODUCT_CORE, TEST_CORE),
	IMPORT(true, UNCONTROLLED_BOM, POLICY_BACKGROUND_BOM, PRODUCT_INTERFACE_BOM, PRODUCT_VARIANT_BOM
	),
	;

	private final List<AInArtifactKind> allowedForUsageWithArtifactKinds;
	private final boolean usedForManagedDependenciesOnly;

	AInArtifactDependencyScopeLevel(
			final boolean aUsedForManagedDependenciesOnly,
			final AInArtifactKind... aAllowedForUsageWithArtifactKinds) {
		allowedForUsageWithArtifactKinds = List.of(aAllowedForUsageWithArtifactKinds);
		usedForManagedDependenciesOnly = aUsedForManagedDependenciesOnly;
	}

	/**
	 * Gets the list of the artifact kinds, with whcih the given scope level can be used in the defined dependency.
	 *
	 * @return the allowedArtifactKinds
	 */
	public List<AInArtifactKind> getAllowedForUsageInDependencyWithArtifactKinds() {
		return allowedForUsageWithArtifactKinds;
	}

	/**
	 * Defines the scope level can be used only in the definition of the managed dependencies, not in direct dependencies.
	 *
	 * @return the managedOnly flag for the scope level.
	 */
	public boolean isUsedForManagedDependenciesOnly() {
		return usedForManagedDependenciesOnly;
	}
}
