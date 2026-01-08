package eu.algites.tool.build.model.yaml.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class AIcYamlExclusionDefDTO {

	/**
	 * ID of the excluded artifact
	 */
	@JsonProperty(value = "target", required = true)
	private String targetId;

	@JsonProperty("classifier")
	private String outputClassifier;

	@JsonProperty("type")
	private String outputTypeId;

	public String getTargetId() {
		return targetId;
	}

	public void setTargetId(String targetId) {
		this.targetId = targetId;
	}

	public String getOutputClassifier() {
		return outputClassifier;
	}

	public void setOutputClassifier(String outputClassifier) {
		this.outputClassifier = outputClassifier;
	}

	public String getOutputTypeId() {
		return outputTypeId;
	}

	public void setOutputTypeId(String outputTypeId) {
		this.outputTypeId = outputTypeId;
	}
}
