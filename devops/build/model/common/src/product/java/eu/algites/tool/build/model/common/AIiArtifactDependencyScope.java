package eu.algites.tool.build.model.common;

/**
 * <p>
 * Title: {@link AIiArtifactDependencyScope}
 * </p>
 * <p>
 * Description: Defines the scope of the dependency
 * </p>
 * <p>
 * Copyright: Copyright (c) 2026 Artur Linhart, Algites
 * </p>
 * <p>
 * Company: Algites
 * </p>
 *
 * @author linhart1
 * @date 08.01.26 1:05
 */
public interface AIiArtifactDependencyScope {

	/**
	 * Gets the scope level
	 *
	 * @return the scope level
	 */
	AInArtifactDependencyScopeLevel getLevel();

	/**
	 * Gets the dependency scope behavior
	 *
	 * @return the dependency scope behavior
	 */
	AIiDependencyScopeBehavior getBehavior();

}
