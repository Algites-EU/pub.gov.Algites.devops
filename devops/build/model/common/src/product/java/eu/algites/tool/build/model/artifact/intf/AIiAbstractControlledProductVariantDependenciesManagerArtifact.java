package eu.algites.tool.build.model.artifact.intf;

import java.util.List;

/**
 * <p>
 * Title: {@link AIiAbstractControlledProductVariantDependenciesManagerArtifact}
 * </p>
 * <p>
 * Description: Container of the background managed dependencies
 * </p>
 * <p>
 * Copyright: Copyright (c) 2026 Artur Linhart, Algites
 * </p>
 * <p>
 * Company: Algites
 * </p>
 *
 * @author linhart1
 * @date 08.01.26 1:16
 */
public interface AIiAbstractControlledProductVariantDependenciesManagerArtifact extends AIiAbstractControlledManagedDependenciesArtifact {

	/**
	 * Gets the managed product variant uncotrolled dependencies
	 *
	 * @return the managed product variant uncotrolled dependencies
	 */
	List<AIiArtifactDependency<? extends AIiAbstractUncontrolledArtifact>> getManagedProductVariantDependencies();

}
