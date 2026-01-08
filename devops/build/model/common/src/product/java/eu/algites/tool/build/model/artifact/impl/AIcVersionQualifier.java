package eu.algites.tool.build.model.artifact.impl;

import eu.algites.tool.build.model.artifact.common.AIiVersionQualifier;
import eu.algites.tool.build.model.artifact.common.AInVersionQualifierKind;

import java.util.Objects;

public class AIcVersionQualifier implements AIiVersionQualifier {

	private AInVersionQualifierKind kind;
	private String label;

	public AIcVersionQualifier() {
	}

	public AIcVersionQualifier(AInVersionQualifierKind kind, String label) {
		this.kind = kind;
		this.label = label;
	}

	@Override
	public AInVersionQualifierKind getKind() {
		return kind;
	}

	public void setKind(AInVersionQualifierKind kind) {
		this.kind = kind;
	}

	@Override
	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	@Override
	public int hashCode() {
		int result = Objects.hashCode(kind);
		result = 31 * result + Objects.hashCode(label);
		return result;
	}

	@Override
	public boolean equals(final Object aO) {
		if (!(aO instanceof AIcVersionQualifier locthat))
			return false;

		return kind == locthat.kind && Objects.equals(label, locthat.label);
	}
}
