package eu.algites.tool.build.model.artifact.impl;

import eu.algites.tool.build.model.artifact.common.AIiUncontrolledCoreArtifact;
import eu.algites.tool.build.model.artifact.common.AInArtifactKind;

/**
 * Mutable POJO implementation of {@link AIiUncontrolledCoreArtifact}.
 * <p>
 * This is primarily intended as a backing class for YAML/JSON loading. The artifact kind is fixed to
 * {@link AInArtifactKind#UNCONTROLLED_CORE}.
 * </p>
 * @author linhart1
 */
public class AIcUncontrolledCoreArtifact extends AIcUncontrolledArtifact implements AIiUncontrolledCoreArtifact {

	public AIcUncontrolledCoreArtifact() {
		super();
		super.setArtifactKind(AInArtifactKind.UNCONTROLLED_CORE);
	}

	public AIcUncontrolledCoreArtifact(String groupId, String artifactIdBase, String artifactVersion) {
		super(groupId, artifactIdBase, AInArtifactKind.UNCONTROLLED_CORE, artifactVersion);
	}

	@Override
	public AInArtifactKind getArtifactKind() {
		return AInArtifactKind.UNCONTROLLED_CORE;
	}

	/**
	 * The kind is fixed for this type; reject any other kind.
	 */
	@Override
	public void setArtifactKind(AInArtifactKind artifactKind) {
		if (artifactKind != null && artifactKind != AInArtifactKind.UNCONTROLLED_CORE) {
			throw new IllegalArgumentException(
					"Invalid artifactKind for AUncontrolledCoreArtifactImpl: " + artifactKind
							+ " (expected " + AInArtifactKind.UNCONTROLLED_CORE + ")"
			);
		}
		super.setArtifactKind(AInArtifactKind.UNCONTROLLED_CORE);
	}

	@Override
	public String toString() {
		return "AIcUncontrolledCoreArtifact" + "{ " + super.toString() + "\n} ";
	}
}
