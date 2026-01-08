package eu.algites.tool.build.model.artifact.common;

/**
 * <p>
 * Title: {@link AIiDependencyScopeBehavior}
 * </p>
 * <p>
 * Description: Behavior of the dependency scope
 * </p>
 * <p>
 * Copyright: Copyright (c) 2026 Artur Linhart, Algites
 * </p>
 * <p>
 * Company: Algites
 * </p>
 *
 * @author linhart1
 * @date 08.01.26 3:12
 */
public interface AIiDependencyScopeBehavior {

	/**
	 * Means the dependency scope declared in the current artifact is locked and cannot be changed during the next inheritance
	 *
	 * @return true if the scope is locked in the transitions and cannot be redefined and false if can be redefined
	 */
	boolean isLocked();

	/**
	 * Means the dependency scope declared in the current artifact is locked and cannot be changed during the next inheritance
	 *
	 * @return true if the scope is locked in the transitions and cannot be redefined and false if can be redefined
	 */
	boolean isTransitive();

}
