package eu.algites.tool.build.model.artifact.impl;

import eu.algites.tool.build.model.artifact.intf.AIiAbstractArtifact;
import eu.algites.tool.build.model.artifact.intf.AIiArtifactProjection;

import java.util.Objects;

/**
 * Projection that also points to the projected artifact (useful for exclusions).
 *
 * @author linhart1
 */
public class AIcArtifactProjection<A extends AIiAbstractArtifact>
		implements AIiArtifactProjection<A> {

	private A linkedArtifact;
	private String outputClassifier;
	private String outputTypeId;

	public AIcArtifactProjection() {
	}

	public AIcArtifactProjection(A linkedArtifact, String outputClassifier, String outputTypeId) {
		this.linkedArtifact = linkedArtifact;
		this.outputClassifier = outputClassifier;
		this.outputTypeId = outputTypeId;
	}

	@Override
	public A getLinkedArtifact() {
		return linkedArtifact;
	}

	public void setLinkedArtifact(A linkedArtifact) {
		this.linkedArtifact = linkedArtifact;
	}

	@Override
	public String getOutputClassifier() {
		return outputClassifier;
	}

	public void setOutputClassifier(String outputClassifier) {
		this.outputClassifier = outputClassifier;
	}

	@Override
	public String getOutputTypeId() {
		return outputTypeId;
	}

	public void setOutputTypeId(String outputTypeId) {
		this.outputTypeId = outputTypeId;
	}

	@Override
	public int hashCode() {
		int result = Objects.hashCode(linkedArtifact);
		result = 31 * result + Objects.hashCode(outputClassifier);
		result = 31 * result + Objects.hashCode(outputTypeId);
		return result;
	}

	@Override
	public boolean equals(final Object aO) {
		if (!(aO instanceof AIcArtifactProjection<?> locthat))
			return false;

		return Objects.equals(linkedArtifact, locthat.linkedArtifact) && Objects.equals(
				outputClassifier,
				locthat.outputClassifier) && Objects.equals(outputTypeId, locthat.outputTypeId);
	}

	@Override
	public String toString() {
		return "AIcArtifactProjection{" +
				"linkedArtifact=" + linkedArtifact +
				", outputClassifier='" + outputClassifier + '\'' +
				", outputTypeId='" + outputTypeId + '\'' +
				'}';
	}
}
