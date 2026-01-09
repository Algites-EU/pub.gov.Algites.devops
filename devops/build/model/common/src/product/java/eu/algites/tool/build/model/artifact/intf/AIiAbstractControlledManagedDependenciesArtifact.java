package eu.algites.tool.build.model.artifact.intf;

import java.util.List;

/**
 * <p>
 * Title: {@link AIiAbstractControlledManagedDependenciesArtifact}
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
public interface AIiAbstractControlledManagedDependenciesArtifact extends AIiAbstractControlledArtifact {

	/**
	 * Gets the managed dependencies of possible various types. If the manager contains reference to other managers, then also such
	 * dependencies are returned here.
	 *
	 * @return all managed dependencies of the manager
	 */
	List<AIiArtifactDependency<? extends AIiAbstractArtifact>> getManagedDependencies();

}
