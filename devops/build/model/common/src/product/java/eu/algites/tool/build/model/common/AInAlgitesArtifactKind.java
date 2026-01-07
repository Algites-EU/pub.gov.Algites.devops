package eu.algites.tool.build.model.common;

/**
 * <p>
 * Title: {@link AInAlgitesArtifactKind}
 * </p>
 * <p>
 * Description: The Kinds of the artifacts in Algites Development paradigm, described
 * in <a href="https://github.com/Algites-EU/pub.gov.Algites.specs/blob/main/Algites-Development-Structure-Specification.md">Algites Development Structure Specification</a>
 * </p>
 * <p>
 * Copyright: Copyright (c) 2026 Artur Linhart, Algites
 * </p>
 * <p>
 * Company: Algites
 * </p>
 *
 * @author linhart1
 * @date 07.01.26 11:17
 */
public enum AInAlgitesArtifactKind {

	/**
	 * General product artifact intended to be productively used somewhere
	 */
	PRODUCT("PROD"),

	/**
	 * Product Test artifact containing the tests intended to be used
	 * to inherit the tests from it or use the tests for the testing
	 * of other artifacts
	 */
	TEST("TST"),

	/**
	 * Aggregator of multiple artifacts
	 */
	AGGREGATOR("AGG"),

	/**
	 * Policy artifact defining the policy of the building and dependencies
	 * used by the product and test artifacts to execute the functionality
	 */
	POLICY("POL"),

	/**
	 * Product background BOM artifact, generated automatically
	 * from the artifact of the kind {@link #POLICY}
	 * It contains the BOM of all materials, defined in the policy, so everything
	 * used in the policy and policy parent policies is imported into this BOM
	 * to assure the absolute consistency of anybody, who is developing something
	 * "for the product defined by the policy" to be compatible with the givne policy.
	 */
	PRODUCT_BACKGROUND_BOM("PBBOM"),

	/**
	 * Plain product BOM artifact, containing manually
	 */
	PRODUCT_INTERFACE_BOM("PIBOM"),
	PRODUCT_VARIANT_BOM("PVBOM"),
	;

	private final String code;

	AInAlgitesArtifactKind(String aCode) {
		code = aCode;
	}

	/**
	 * @return the code
	 */
	public String getCode() {
		return code;
	}
}
