package eu.algites.tool.build.model.artifact.impl;

import eu.algites.tool.build.model.artifact.common.AIiUncontrolledBomArtifact;
import eu.algites.tool.build.model.artifact.common.AInArtifactKind;

/**
 * Mutable POJO implementation of {@link AIiUncontrolledBomArtifact}.
 * <p>
 * This is primarily intended as a backing class for YAML/JSON loading. The artifact kind is fixed to
 * {@link AInArtifactKind#UNCONTROLLED_BOM}.
 * </p>
 * @author linhart1
 */
public class AIcUncontrolledBomArtifact extends AIcUncontrolledArtifact implements AIiUncontrolledBomArtifact {

	public AIcUncontrolledBomArtifact() {
		super();
		super.setArtifactKind(AInArtifactKind.UNCONTROLLED_BOM);
	}

	public AIcUncontrolledBomArtifact(String groupId, String artifactIdBase, String artifactVersion) {
		super(groupId, artifactIdBase, AInArtifactKind.UNCONTROLLED_BOM, artifactVersion);
	}

	@Override
	public AInArtifactKind getArtifactKind() {
		return AInArtifactKind.UNCONTROLLED_BOM;
	}

	/**
	 * The kind is fixed for this type; reject any other kind.
	 */
	@Override
	public void setArtifactKind(AInArtifactKind artifactKind) {
		if (artifactKind != null && artifactKind != AInArtifactKind.UNCONTROLLED_BOM) {
			throw new IllegalArgumentException(
					"Invalid artifactKind for AUncontrolledBomArtifactImpl: " + artifactKind
							+ " (expected " + AInArtifactKind.UNCONTROLLED_BOM + ")"
			);
		}
		super.setArtifactKind(AInArtifactKind.UNCONTROLLED_BOM);
	}

	@Override
	public String toString() {
		return "AIcUncontrolledBomArtifact" + "{ " + super.toString() + "\n} ";
	}
}
