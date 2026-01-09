package eu.algites.tool.build.model.version.intf;

/**
 * <p>
 * Title: {@link AIiVersionContext}
 * </p>
 * <p>
 * Description: TODO: Add description
 * </p>
 * <p>
 * Copyright: Copyright (c) 2026 Artur Linhart, Algites
 * </p>
 * <p>
 * Company: Algites
 * </p>
 *
 * @author linhart1
 * @date 08.01.26 0:06
 */
public interface AIiVersionContext {

	/**
	 * Gets the basic release line of the version context. Can be null if no release context is specified
	 *
	 * @return the basic release line of the version context
	 */
	AIiVersionReleaseLine getReleaseLine();

	/**
	 * Gets the revision of the version context.
	 *
	 * @return the revision of the version context
	 */
	AIiVersionReleaseLineRevision getRevision();

	/**
	 * Gets the qualifier of the version context. Must be always specified.
	 *
	 * @return the qualifier of the version context
	 */
	AIiVersionQualifier getQualifier();
}
