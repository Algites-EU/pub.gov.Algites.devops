package eu.algites.tool.build.model.version.dto;

import eu.algites.tool.build.model.version.intf.AInVersionQualifierKind;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class AIcVersionContextDefDTO {

	@JsonProperty(value = "releaseLine", required = true)
	private String releaseLine;

	@JsonProperty(value = "revision", required = true)
	private int revision;

	@JsonProperty(value = "qualifierKind", required = true)
	private AInVersionQualifierKind qualifierKind;

	@JsonProperty(value = "qualifierLabel", required = true)
	private String qualifierLabel;

	public String getReleaseLine() {
		return releaseLine;
	}

	public void setReleaseLine(String releaseLine) {
		this.releaseLine = releaseLine;
	}

	public int getRevision() {
		return revision;
	}

	public void setRevision(int revision) {
		this.revision = revision;
	}

	public AInVersionQualifierKind getQualifierKind() {
		return qualifierKind;
	}

	public void setQualifierKind(AInVersionQualifierKind qualifierKind) {
		this.qualifierKind = qualifierKind;
	}

	public String getQualifierLabel() {
		return qualifierLabel;
	}

	public void setQualifierLabel(String qualifierLabel) {
		this.qualifierLabel = qualifierLabel;
	}
}
