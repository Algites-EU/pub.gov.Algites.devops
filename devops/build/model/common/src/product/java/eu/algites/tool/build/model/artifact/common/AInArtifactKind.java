package eu.algites.tool.build.model.artifact.common;

/**
 * <p>
 * Title: {@link AInArtifactKind}
 * </p>
 * <p>
 * Description: The Kinds of the artifacts in Algites Development paradigm, described in <a
 * href="https://github.com/Algites-EU/pub.gov.Algites.specs/blob/main/Algites-Development-Structure-Specification.md">Algites Development
 * Structure Specification</a>
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
public enum AInArtifactKind {

	/**
	 * General product artifact non-BOM intended to be productively used somewhere, external dependency (jar/…)
	 */
	UNCONTROLLED_CORE("(unknown core)", false, false, false),

	/**
	 * General product artifact non-BOM intended to be productively used somewhere, external POM used for import only / may represent BOM
	 */
	UNCONTROLLED_BOM("(unknown BOM)", false, false, true),

	/**
	 * General product artifact intended to be productively used somewhere
	 */
	PRODUCT_CORE("PROD", false, true, false),

	/**
	 * Product Test artifact containing the tests intended to be used to inherit the tests from it or use the tests for the testing of other
	 * artifacts
	 */
	TEST_CORE("TST", false, true, false),

	/**
	 * Aggregator of multiple artifacts
	 */
	AGGREGATOR("AGG", false, true, true),

	/**
	 * Policy artifact defining the policy of the building and dependencies used by the product and test artifacts to execute the
	 * functionality
	 */
	POLICY("POL", false, true, true),

	/**
	 * Product background BOM artifact, generated automatically from the artifact of the kind {@link #POLICY} It contains the BOM of all
	 * materials, defined in the policy, so everything used in the policy and policy parent policies is imported into this BOM to assure the
	 * absolute consistency of anybody, who is developing something "for the product defined by the policy" to be compatible with the givne
	 * policy.
	 */
	POLICY_BACKGROUND_BOM("PBBOM", true, true, true),

	/**
	 * Product interface BOM artifact, containing manually settled list of the given product artifacts, depending on the given policy
	 * artifact, without any pre-selection of the usage variant of the given product artifacts, open for construction of own variant (e.g.
	 * Spring framework BOM)
	 */
	PRODUCT_INTERFACE_BOM("PIBOM", false, true, true),

	/**
	 * Product varian BOM artifact, containing manually settled list of the product artifacts, composing together some specific product or
	 * product line, contianing already the compatible frameworks used by the products etc. (e.g. Spring boot BOM)
	 */
	PRODUCT_VARIANT_BOM("PVBOM", false, true, true),
	;

	private final String code;
	private final boolean automaticallyGenerated;
	private final boolean controlled;
	private final boolean mavenPomArtifactTypeRequired;

	AInArtifactKind(String aCode, boolean aAutomaticallyGenerated, final boolean aControlled, final boolean aMavenPomArtifactTypeRequired) {
		code = aCode;
		automaticallyGenerated = aAutomaticallyGenerated;
		controlled = aControlled;
		mavenPomArtifactTypeRequired = aMavenPomArtifactTypeRequired;
	}

	/**
	 * Finds the artifact kind by its short code.
	 *
	 * @param aCode the short aCode (e.g. "PROD", "TST")
	 * @return matching {@link AInArtifactKind} or {@code null} if not found
	 */
	public static AInArtifactKind findByCode(String aCode) {
		if (aCode == null) {
			return null;
		}
		for (AInArtifactKind kind : values()) {
			if (kind.code.equals(aCode)) {
				return kind;
			}
		}
		return null;
	}

	/**
	 * @return the code
	 */
	public String getCode() {
		return code;
	}

	/**
	 * Automatically generated artifacts, like {@link #POLICY_BACKGROUND_BOM} whcih directly depends on the {@link #POLICY} artifact. The
	 * Automatically generated artifacts may not be included in the algites-artifact.properties file.
	 *
	 * @return the automaticallyGenerated
	 */
	public boolean isAutomaticallyGenerated() {
		return automaticallyGenerated;
	}

	/**
	 * Indicates, if the artifact is controlled by the framework (true) or uncontrolled (external - false)
	 *
	 * @return the controlled
	 */
	public boolean isControlled() {
		return controlled;
	}

	/**
	 * @return the mavenPomArtifactTypeRequired
	 */
	public boolean isMavenPomArtifactTypeRequired() {
		return mavenPomArtifactTypeRequired;
	}
}
